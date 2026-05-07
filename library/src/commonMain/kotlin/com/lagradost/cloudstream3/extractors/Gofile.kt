package com.rajk2007.zcast.extractors

import com.fasterxml.jackson.annotation.JsonProperty
import com.rajk2007.zcast.SubtitleFile
import com.rajk2007.zcast.app
import com.rajk2007.zcast.utils.ExtractorApi
import com.rajk2007.zcast.utils.ExtractorLink
import com.rajk2007.zcast.utils.ExtractorLinkType
import com.rajk2007.zcast.utils.Qualities
import com.rajk2007.zcast.utils.newExtractorLink

open class Gofile : ExtractorApi() {
    override val name = "Gofile"
    override val mainUrl = "https://gofile.io"
    override val requiresReferer = false
    private val mainApi = "https://api.gofile.io"

    override suspend fun getUrl(
        url: String,
        referer: String?,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        val id = Regex("/(?:\\?c=|d/)([\\da-zA-Z-]+)").find(url)?.groupValues?.get(1) ?: return

        val token = app.post(
            "$mainApi/accounts",
        ).parsedSafe<AccountResponse>()?.data?.token ?: return

        val globalRes = app.get("$mainUrl/dist/js/config.js").text
        val wt = Regex("""appdata\.wt\s*=\s*[\"']([^\"']+)[\"']""").find(globalRes)?.groupValues?.get(1) ?: return

        val headers = mapOf(
            "Authorization" to "Bearer $token",
            "X-Website-Token" to wt
        )

        val parsedResponse = app.get(
            "$mainApi/contents/$id?contentFilter=&page=1&pageSize=1000&sortField=name&sortDirection=1",
            headers = headers
        ).parsedSafe<GofileResponse>()

        val childrenMap = parsedResponse?.data?.children ?: return

        for ((_, file) in childrenMap) {
            if (file.link.isNullOrEmpty() || file.type != "file") continue
            val fileName = file.name ?: ""
            val size = file.size ?: 0L
            val formattedSize = formatBytes(size)

            callback.invoke(
                newExtractorLink(
                    "Gofile",
                    "[Gofile] $fileName [$formattedSize]",
                    file.link,
                    ExtractorLinkType.VIDEO
                ) {
                    this.quality = getQuality(fileName)
                    this.headers = mapOf("Cookie" to "accountToken=$token")
                }
            )
        }
    }

    private fun getQuality(str: String?): Int {
        return Regex("(\\d{3,4})[pP]").find(str ?: "")?.groupValues?.getOrNull(1)?.toIntOrNull()
            ?: Qualities.Unknown.value
    }

    private fun formatBytes(bytes: Long): String {
        return when {
            bytes < 1024L * 1024 * 1024 -> "%.2f MB".format(bytes.toDouble() / (1024 * 1024))
            else -> "%.2f GB".format(bytes.toDouble() / (1024 * 1024 * 1024))
        }
    }

    data class AccountResponse(
        @JsonProperty("data") val data: AccountData? = null
    )

    data class AccountData(
        @JsonProperty("token") val token: String? = null
    )

    data class GofileResponse(
        @JsonProperty("data") val data: GofileData? = null
    )

    data class GofileData(
        @JsonProperty("children") val children: Map<String, GofileFile>? = null
    )

    data class GofileFile(
        @JsonProperty("type") val type: String? = null,
        @JsonProperty("name") val name: String? = null,
        @JsonProperty("link") val link: String? = null,
        @JsonProperty("size") val size: Long? = 0L
    )
}
