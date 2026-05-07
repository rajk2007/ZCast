package com.rajk2007.zcast.extractors

import com.rajk2007.zcast.SubtitleFile
import com.rajk2007.zcast.app
import com.rajk2007.zcast.newSubtitleFile
import com.rajk2007.zcast.utils.ExtractorApi
import com.rajk2007.zcast.utils.ExtractorLink
import com.rajk2007.zcast.utils.ExtractorLinkType
import com.rajk2007.zcast.utils.Qualities
import com.rajk2007.zcast.utils.newExtractorLink

open class SecvideoOnline : ExtractorApi() {
    override val name: String = "Secvideo1"
    override val mainUrl: String = "https://secvideo1.online"
    override val requiresReferer: Boolean = false

    private val fileListRegex = Regex("""file:\s*"(.*)\"""")
    private val subtitleListRegex = Regex("""subtitle:\s*"(.*)\"""")
    private val labelSourceRegex = Regex("""\[(.*?)\](.*)""")

    override suspend fun getUrl(
        url: String,
        referer: String?,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        val doc = app.get(url, referer = referer).document
        for (script in doc.select("script")) {
            val files = fileListRegex.findAll(script.data())
                .mapNotNull { it.groupValues.getOrNull(1)?.split(",") }
                .flatten()
                .distinct()

            for (file in files) {
                val labelAndSourceMatch = labelSourceRegex.find(file) ?: continue
                callback.invoke(
                    newExtractorLink(
                        source = name,
                        name = name,
                        url = labelAndSourceMatch.groupValues[2]
                    ) {
                        quality = labelAndSourceMatch.groupValues[1].replace("p", "").toIntOrNull()
                            ?: Qualities.Unknown.value
                        this.type = ExtractorLinkType.VIDEO
                    }
                )
            }

            val subtitles = subtitleListRegex.findAll(script.data())
                .mapNotNull { it.groupValues.getOrNull(1)?.split(",") }
                .flatten()
                .distinct()

            for (subtitle in subtitles) {
                val languageAndSourceMatch = labelSourceRegex.find(subtitle) ?: continue
                subtitleCallback.invoke(
                    newSubtitleFile(
                        lang = languageAndSourceMatch.groupValues[1],
                        url = languageAndSourceMatch.groupValues[2]
                    )
                )
            }
        }
    }
}

class FsstOnline : SecvideoOnline() {
    override val name: String = "FsstOnline"
    override val mainUrl: String = "https://fsst.online"
}

class CsstOnline : SecvideoOnline() {
    override val name: String = "CsstOnline"
    override val mainUrl: String = "https://csst.online"
}

class DsstOnline : SecvideoOnline() {
    override val name: String = "DsstOnline"
    override val mainUrl: String = "https://dsst.online"
}