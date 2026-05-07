package com.rajk2007.zcast.extractors
import com.rajk2007.zcast.app
import com.rajk2007.zcast.utils.ExtractorApi
import com.rajk2007.zcast.utils.ExtractorLink
import com.rajk2007.zcast.utils.ExtractorLinkType
import com.rajk2007.zcast.utils.Qualities
import com.rajk2007.zcast.utils.getAndUnpack
import com.rajk2007.zcast.utils.newExtractorLink

class Vido : ExtractorApi() {
    override var name = "Vido"
    override var mainUrl = "https://vido.lol"
    private val srcRegex = Regex("""sources:\s*\["(.*?)"\]""")
    override val requiresReferer = true

    override suspend fun getUrl(url: String, referer: String?): List<ExtractorLink>? {
        val methode = app.get(url.replace("/e/", "/embed-")) // fix wiflix and mesfilms
        with(methode) {
            if (!methode.isSuccessful) return null
            //val quality = unpackedText.lowercase().substringAfter(" height=").substringBefore(" ").toIntOrNull()
            srcRegex.find(this.text)?.groupValues?.get(1)?.let { link ->
                return listOf(
                    newExtractorLink(
                        source = name,
                        name = name,
                        url = link,
                        type = ExtractorLinkType.M3U8
                    ) {
                        this.referer = url
                        this.quality = Qualities.Unknown.value
                    }
                )
            }
        }
        return null
    }
}