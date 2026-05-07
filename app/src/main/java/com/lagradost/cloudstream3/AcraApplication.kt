package com.rajk2007.zcast

/**
 * Deprecated alias for ZCastApp for backwards compatibility with plugins.
 * Use ZCastApp instead.
 */
@Deprecated(
    message = "AcraApplication is deprecated, use ZCastApp instead",
    replaceWith = ReplaceWith("com.rajk2007.zcast.ZCastApp"),
    level = DeprecationLevel.WARNING
)
class AcraApplication {
	companion object {

		@Deprecated(
		    message = "AcraApplication is deprecated, use ZCastApp instead",
		    replaceWith = ReplaceWith("com.rajk2007.zcast.ZCastApp.context"),
		    level = DeprecationLevel.WARNING
		)
		val context get() = ZCastApp.context

		@Deprecated(
		    message = "AcraApplication is deprecated, use ZCastApp instead",
		    replaceWith = ReplaceWith("com.rajk2007.zcast.ZCastApp.removeKeys(folder)"),
		    level = DeprecationLevel.WARNING
		)
		fun removeKeys(folder: String): Int? =
		    ZCastApp.removeKeys(folder)

		@Deprecated(
		    message = "AcraApplication is deprecated, use ZCastApp instead",
		    replaceWith = ReplaceWith("com.rajk2007.zcast.ZCastApp.setKey(path, value)"),
		    level = DeprecationLevel.WARNING
		)
		fun <T> setKey(path: String, value: T) =
			ZCastApp.setKey(path, value)

		@Deprecated(
		    message = "AcraApplication is deprecated, use ZCastApp instead",
		    replaceWith = ReplaceWith("com.rajk2007.zcast.ZCastApp.setKey(folder, path, value)"),
		    level = DeprecationLevel.WARNING
		)
		fun <T> setKey(folder: String, path: String, value: T) =
			ZCastApp.setKey(folder, path, value)

		@Deprecated(
		    message = "AcraApplication is deprecated, use ZCastApp instead",
		    replaceWith = ReplaceWith("com.rajk2007.zcast.ZCastApp.getKey(path, defVal)"),
		    level = DeprecationLevel.WARNING
		)
		inline fun <reified T : Any> getKey(path: String, defVal: T?): T? =
			ZCastApp.getKey(path, defVal)

		@Deprecated(
		    message = "AcraApplication is deprecated, use ZCastApp instead",
		    replaceWith = ReplaceWith("com.rajk2007.zcast.ZCastApp.getKey(path)"),
		    level = DeprecationLevel.WARNING
		)
		inline fun <reified T : Any> getKey(path: String): T? =
			ZCastApp.getKey(path)

		@Deprecated(
		    message = "AcraApplication is deprecated, use ZCastApp instead",
		    replaceWith = ReplaceWith("com.rajk2007.zcast.ZCastApp.getKey(folder, path)"),
		    level = DeprecationLevel.WARNING
		)
		inline fun <reified T : Any> getKey(folder: String, path: String): T? =
		    ZCastApp.getKey(folder, path)

		@Deprecated(
		    message = "AcraApplication is deprecated, use ZCastApp instead",
		    replaceWith = ReplaceWith("com.rajk2007.zcast.ZCastApp.getKey(folder, path, defVal)"),
		    level = DeprecationLevel.WARNING
		)
		inline fun <reified T : Any> getKey(folder: String, path: String, defVal: T?): T? =
			ZCastApp.getKey(folder, path, defVal)
	}
}
