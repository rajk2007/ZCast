package com.rajk2007.zcast.extractors

import com.fasterxml.jackson.annotation.JsonProperty
import com.rajk2007.zcast.app
import com.rajk2007.zcast.utils.AppUtils.tryParseJson
import com.rajk2007.zcast.utils.ExtractorApi
import com.rajk2007.zcast.utils.ExtractorLink
import com.rajk2007.zcast.utils.getQualityFromName
import com.rajk2007.zcast.utils.newExtractorLink

open class YourUpload: ExtractorApi() {
    override val name = "Yourupload"
    override val mainUrl = "https://www.yourupload.com"
    override val requiresReferer = false

    override suspend fun getUrl(url: String, referer: String?): List<ExtractorLink> {
        val sources = mutableListOf<ExtractorLink>()
        with(app.get(url).document) {
            val quality = Regex("\\d{3,4}p").find(this.select("title").text())?.groupValues?.get(0)
            this.select("script").map { script ->
                if (script.data().contains("var jwplayerOptions = {")) {
                    val data =
                        script.data().substringAfter("var jwplayerOptions = {").substringBefore(",\n")
                    val link = tryParseJson<ResponseSource>(
                        "{${
                            data.replace("file", "\"file\"").replace("'", "\"")
                        }}"
                    )
                    sources.add(
                        newExtractorLink(
                            source = name,
                            name = name,
                            url = link!!.file,
                        ) {
                            this.referer = url
                            this.quality = getQualityFromName(quality)
                        }
                    )
                }
            }
        }
        return sources
    }

    private data class ResponseSource(
        @JsonProperty("file") val file: String,
    )

}