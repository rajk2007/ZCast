package com.rajk2007.zcast.ui.player

import android.net.Uri
import com.rajk2007.zcast.TvType
import com.rajk2007.zcast.actions.temp.ZCastPackage
import com.rajk2007.zcast.amap
import com.rajk2007.zcast.utils.ExtractorLink
import com.rajk2007.zcast.utils.ExtractorLinkType
import com.rajk2007.zcast.utils.INFER_TYPE
import com.rajk2007.zcast.utils.Qualities
import com.rajk2007.zcast.utils.loadExtractor
import com.rajk2007.zcast.utils.newExtractorLink
import com.rajk2007.zcast.utils.unshortenLinkSafe

data class ExtractorUri(
    val uri: Uri,
    val name: String,

    val basePath: String? = null,
    val relativePath: String? = null,
    val displayName: String? = null,

    val id: Int? = null,
    val parentId: Int? = null,
    val episode: Int? = null,
    val season: Int? = null,
    val headerName: String? = null,
    val tvType: TvType? = null,
)

/**
 * Used to open the player more easily with the LinkGenerator
 **/
data class BasicLink(
    val url: String,
    val name: String? = null,
)

class LinkGenerator(
    private val links: List<BasicLink>,
    private val extract: Boolean = true,
    private val refererUrl: String? = null,
    id: Int?
) : NoVideoGenerator(id) {
    override suspend fun generateLinks(
        clearCache: Boolean,
        sourceTypes: Set<ExtractorLinkType>,
        callback: (Pair<ExtractorLink?, ExtractorUri?>) -> Unit,
        subtitleCallback: (SubtitleData) -> Unit,
        offset: Int,
        isCasting: Boolean
    ): Boolean {
        links.amap { link ->
            if (!extract || !loadExtractor(link.url, refererUrl, {
                    subtitleCallback(PlayerSubtitleHelper.getSubtitleData(it))
                }) {
                    callback(it to null)
                }) {

                // if don't extract or if no extractor found simply return the link
                callback(
                    newExtractorLink(
                        "",
                        link.name ?: link.url,
                        unshortenLinkSafe(link.url), // unshorten because it might be a raw link
                        type = INFER_TYPE,
                    ) {
                        this.referer = refererUrl ?: ""
                        this.quality = Qualities.Unknown.value
                    } to null
                )
            }
        }

        return true
    }
}

class MinimalLinkGenerator(
    private val links: List<ZCastPackage.MinimalVideoLink>,
    private val subs: List<ZCastPackage.MinimalSubtitleLink>,
    id: Int?
) : NoVideoGenerator(id) {
    override suspend fun generateLinks(
        clearCache: Boolean,
        sourceTypes: Set<ExtractorLinkType>,
        callback: (Pair<ExtractorLink?, ExtractorUri?>) -> Unit,
        subtitleCallback: (SubtitleData) -> Unit,
        offset: Int,
        isCasting: Boolean
    ): Boolean {
        for (link in links) {
            callback(link.toExtractorLink())
        }
        for (link in subs) {
            subtitleCallback(link.toSubtitleData())
        }

        return true
    }
}