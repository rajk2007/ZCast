package com.rajk2007.zcast.extractors

import com.rajk2007.zcast.SubtitleFile
import com.rajk2007.zcast.app
import com.rajk2007.zcast.extractors.helper.JwPlayerHelper
import com.rajk2007.zcast.utils.ExtractorApi
import com.rajk2007.zcast.utils.ExtractorLink
import com.rajk2007.zcast.utils.JsUnpacker

open class Supervideo : ExtractorApi() {
    override var name = "Supervideo"
    override var mainUrl = "https://supervideo.cc"
    override val requiresReferer = false

    override suspend fun getUrl(
        url: String,
        referer: String?,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        val response = app.get(url).text
        val jstounpack = Regex("eval((.|\\n)*?)</script>").find(response)?.groups?.get(1)?.value
        val unpacked = JsUnpacker(jstounpack).unpack()

        JwPlayerHelper.extractStreamLinks(unpacked.orEmpty(), name, mainUrl, callback, subtitleCallback)
    }
}