package com.rajk2007.zcast.extractors

import com.rajk2007.zcast.SubtitleFile
import com.rajk2007.zcast.app
import com.rajk2007.zcast.utils.ExtractorApi
import com.rajk2007.zcast.utils.ExtractorLink
import com.rajk2007.zcast.utils.INFER_TYPE
import com.rajk2007.zcast.utils.Qualities
import com.rajk2007.zcast.utils.newExtractorLink

open class Mediafire : ExtractorApi() {
    override val name = "Mediafire"
    override val mainUrl = "https://www.mediafire.com"
    override val requiresReferer = true

    override suspend fun getUrl(
        url: String,
        referer: String?,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        val res = app.get(url, referer = referer).document
        val title = res.select("div.dl-btn-label").text()
        val video = res.selectFirst("a#downloadButton")?.attr("href")

        callback.invoke(
            newExtractorLink(
                this.name,
                this.name,
                video ?: return
            ) {
                this.quality = getQuality(title)
            }
        )

    }

    private fun getQuality(str: String?): Int {
        return Regex("(\\d{3,4})[pP]").find(str ?: "")?.groupValues?.getOrNull(1)?.toIntOrNull()
            ?: Qualities.Unknown.value
    }

}