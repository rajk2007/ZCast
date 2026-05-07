package com.rajk2007.zcast.extractors

import com.rajk2007.zcast.SubtitleFile
import com.rajk2007.zcast.app
import com.rajk2007.zcast.utils.ExtractorApi
import com.rajk2007.zcast.utils.ExtractorLink
import com.rajk2007.zcast.utils.Qualities
import com.rajk2007.zcast.utils.newExtractorLink

class GoodstreamExtractor : ExtractorApi() {
    override var name = "Goodstream"
    override val mainUrl = "https://goodstream.uno"
    override val requiresReferer = false

    override suspend fun getUrl(
        url: String,
        referer: String?,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        app.get(url).document.select("script").map { script ->
            if (script.data().contains(Regex("file|player"))) {
                val urlRegex = Regex("file: \"(https:\\/\\/[a-z0-9.\\/-_?=&]+)\",")
                urlRegex.find(script.data())?.groupValues?.get(1).let { link ->
                    callback.invoke(
                        newExtractorLink(
                            name,
                            name,
                            link!!,
                        ) {
                            this.referer = mainUrl
                        }
                    )
                }
            }
        }
    }
}