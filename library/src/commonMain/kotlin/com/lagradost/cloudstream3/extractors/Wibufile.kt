package com.rajk2007.zcast.extractors

import com.rajk2007.zcast.SubtitleFile
import com.rajk2007.zcast.app
import com.rajk2007.zcast.utils.ExtractorApi
import com.rajk2007.zcast.utils.ExtractorLink
import com.rajk2007.zcast.utils.INFER_TYPE
import com.rajk2007.zcast.utils.Qualities
import com.rajk2007.zcast.utils.newExtractorLink

open class Wibufile : ExtractorApi() {
    override val name: String = "Wibufile"
    override val mainUrl: String = "https://wibufile.com"
    override val requiresReferer = false

    override suspend fun getUrl(
        url: String,
        referer: String?,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        val res = app.get(url).text
        val video = Regex("src: ['\"](.*?)['\"]").find(res)?.groupValues?.get(1)

        callback.invoke(
            newExtractorLink(
                name,
                name,
                video ?: return,
            ) {
                this.referer = "$mainUrl/"
                this.quality = Qualities.Unknown.value
            }
        )
    }
}