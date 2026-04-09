package com.lagradost.cloudstream3

/**
 * Deprecated alias for NovaCastApp for backwards compatibility with plugins.
 * Use NovaCastApp instead.
 */
@Deprecated(
    message = "AcraApplication is deprecated, use NovaCastApp instead",
    replaceWith = ReplaceWith("com.lagradost.cloudstream3.NovaCastApp"),
    level = DeprecationLevel.WARNING
)
class AcraApplication {
	companion object {

		@Deprecated(
		    message = "AcraApplication is deprecated, use NovaCastApp instead",
		    replaceWith = ReplaceWith("com.lagradost.cloudstream3.NovaCastApp.context"),
		    level = DeprecationLevel.WARNING
		)
		val context get() = NovaCastApp.context

		@Deprecated(
		    message = "AcraApplication is deprecated, use NovaCastApp instead",
		    replaceWith = ReplaceWith("com.lagradost.cloudstream3.NovaCastApp.removeKeys(folder)"),
		    level = DeprecationLevel.WARNING
		)
		fun removeKeys(folder: String): Int? =
		    NovaCastApp.removeKeys(folder)

		@Deprecated(
		    message = "AcraApplication is deprecated, use NovaCastApp instead",
		    replaceWith = ReplaceWith("com.lagradost.cloudstream3.NovaCastApp.setKey(path, value)"),
		    level = DeprecationLevel.WARNING
		)
		fun <T> setKey(path: String, value: T) =
			NovaCastApp.setKey(path, value)

		@Deprecated(
		    message = "AcraApplication is deprecated, use NovaCastApp instead",
		    replaceWith = ReplaceWith("com.lagradost.cloudstream3.NovaCastApp.setKey(folder, path, value)"),
		    level = DeprecationLevel.WARNING
		)
		fun <T> setKey(folder: String, path: String, value: T) =
			NovaCastApp.setKey(folder, path, value)

		@Deprecated(
		    message = "AcraApplication is deprecated, use NovaCastApp instead",
		    replaceWith = ReplaceWith("com.lagradost.cloudstream3.NovaCastApp.getKey(path, defVal)"),
		    level = DeprecationLevel.WARNING
		)
		inline fun <reified T : Any> getKey(path: String, defVal: T?): T? =
			NovaCastApp.getKey(path, defVal)

		@Deprecated(
		    message = "AcraApplication is deprecated, use NovaCastApp instead",
		    replaceWith = ReplaceWith("com.lagradost.cloudstream3.NovaCastApp.getKey(path)"),
		    level = DeprecationLevel.WARNING
		)
		inline fun <reified T : Any> getKey(path: String): T? =
			NovaCastApp.getKey(path)

		@Deprecated(
		    message = "AcraApplication is deprecated, use NovaCastApp instead",
		    replaceWith = ReplaceWith("com.lagradost.cloudstream3.NovaCastApp.getKey(folder, path)"),
		    level = DeprecationLevel.WARNING
		)
		inline fun <reified T : Any> getKey(folder: String, path: String): T? =
		    NovaCastApp.getKey(folder, path)

		@Deprecated(
		    message = "AcraApplication is deprecated, use NovaCastApp instead",
		    replaceWith = ReplaceWith("com.lagradost.cloudstream3.NovaCastApp.getKey(folder, path, defVal)"),
		    level = DeprecationLevel.WARNING
		)
		inline fun <reified T : Any> getKey(folder: String, path: String, defVal: T?): T? =
			NovaCastApp.getKey(folder, path, defVal)
	}
}
