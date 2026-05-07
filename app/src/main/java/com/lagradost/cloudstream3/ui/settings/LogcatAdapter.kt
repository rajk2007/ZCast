package com.rajk2007.zcast.ui.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import com.rajk2007.zcast.databinding.ItemLogcatBinding
import com.rajk2007.zcast.ui.BaseDiffCallback
import com.rajk2007.zcast.ui.NoStateAdapter
import com.rajk2007.zcast.ui.ViewHolderState

class LogcatAdapter() : NoStateAdapter<String>(
    diffCallback = BaseDiffCallback(
        itemSame = String::equals,
        contentSame = String::equals
    )
) {
    override fun onCreateContent(parent: ViewGroup): ViewHolderState<Any> {
        return ViewHolderState(
            ItemLogcatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindContent(holder: ViewHolderState<Any>, item: String, position: Int) {
        (holder.view as? ItemLogcatBinding)?.apply {
            logText.text = item
        }
    }
}