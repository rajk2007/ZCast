package com.rajk2007.zcast.extractors

import com.rajk2007.zcast.SubtitleFile
import com.rajk2007.zcast.app
import com.rajk2007.zcast.extractors.helper.JwPlayerHelper
import com.rajk2007.zcast.utils.ExtractorApi
import com.rajk2007.zcast.utils.ExtractorLink
import com.rajk2007.zcast.utils.getAndUnpack
import com.rajk2007.zcast.utils.getPacked

open class StreamoUpload : ExtractorApi() {
    override val name = "StreamoUpload"
    override val mainUrl = "https://streamoupload.xyz"
    override val requiresReferer = true

    override suspend fun getUrl(
        url: String,
        referer: String?,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        val response = app.get(url, referer = referer)
        response.document.select("script").map { script ->
            if (getPacked(script.data()) != null) {
                val data = getAndUnpack(script.data())
                JwPlayerHelper.extractStreamLinks(data, name, mainUrl, callback, subtitleCallback)
            }
        }
    }
}
