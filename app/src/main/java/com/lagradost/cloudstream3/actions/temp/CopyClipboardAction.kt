package com.rajk2007.zcast.actions.temp

import android.content.Context
import com.rajk2007.zcast.actions.VideoClickAction
import com.rajk2007.zcast.ui.result.LinkLoadingResult
import com.rajk2007.zcast.ui.result.ResultEpisode
import com.rajk2007.zcast.utils.txt
import com.rajk2007.zcast.utils.UIHelper.clipboardHelper

class CopyClipboardAction: VideoClickAction() {
    override val name = txt("Copy to clipboard")

    override val oneSource = true

    override fun shouldShow(context: Context?, video: ResultEpisode?) = true

    override suspend fun runAction(
        context: Context?,
        video: ResultEpisode,
        result: LinkLoadingResult,
        index: Int?
    ) {
        if (index == null) return
        val link = result.links.getOrNull(index) ?: return
        clipboardHelper(txt(link.name), link.url)
    }
}