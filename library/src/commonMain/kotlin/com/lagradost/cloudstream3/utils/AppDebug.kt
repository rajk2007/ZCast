package com.rajk2007.zcast.utils

import com.rajk2007.zcast.InternalAPI

@InternalAPI
object AppDebug {
    @Volatile
    var isDebug: Boolean = false
}
