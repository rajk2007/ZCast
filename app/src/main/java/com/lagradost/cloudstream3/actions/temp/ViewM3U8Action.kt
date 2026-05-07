package com.rajk2007.zcast.actions.temp

import android.content.Context
import android.content.Intent
import com.rajk2007.zcast.R
import com.rajk2007.zcast.actions.VideoClickAction
import com.rajk2007.zcast.actions.makeTempM3U8Intent
import com.rajk2007.zcast.ui.result.LinkLoadingResult
import com.rajk2007.zcast.ui.result.ResultEpisode
import com.rajk2007.zcast.utils.txt

class ViewM3U8Action: VideoClickAction() {
    override val name = txt(R.string.episode_action_play_in_format, "m3u8 player")

    override val isPlayer = true

    override fun shouldShow(context: Context?, video: ResultEpisode?) = true

    override suspend fun runAction(
        context: Context?,
        video: ResultEpisode,
        result: LinkLoadingResult,
        index: Int?
    ) {
        if (context == null) return
        val i = Intent(Intent.ACTION_VIEW)
        makeTempM3U8Intent(context, i, result)
        launch(i)
    }
}