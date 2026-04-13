package com.rajk007.novacast.ui.setup

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.navigation.fragment.findNavController
import com.lagradost.cloudstream3.BuildConfig
import com.lagradost.cloudstream3.R
import com.lagradost.cloudstream3.databinding.FragmentSetupGithubStarBinding
import com.lagradost.cloudstream3.mvvm.safe
import com.lagradost.cloudstream3.ui.BaseFragment
import com.lagradost.cloudstream3.utils.UIHelper.fixSystemBarsPadding

class GithubStarFragment : BaseFragment<FragmentSetupGithubStarBinding>(
    BaseFragment.BindingCreator.Inflate(FragmentSetupGithubStarBinding::inflate)
) {

    override fun fixLayout(view: View) {
        fixSystemBarsPadding(view)
    }

    override fun onBindingCreated(binding: FragmentSetupGithubStarBinding) {
        safe {
            val ctx = context ?: return@safe

            binding.apply {
                // Set app icon with fallback
                try {
                    val drawable = when {
                        BuildConfig.DEBUG -> R.drawable.cloud_2_gradient_debug
                        BuildConfig.FLAVOR == "prerelease" -> R.drawable.cloud_2_gradient_beta
                        else -> R.drawable.cloud_2_gradient
                    }
                    appIconImage.setImageDrawable(
                        androidx.core.content.ContextCompat.getDrawable(ctx, drawable)
                    )
                } catch (e: Exception) {
                    // If custom drawable not found, use launcher icon
                    try {
                        appIconImage.setImageDrawable(
                            androidx.core.content.ContextCompat.getDrawable(ctx, R.mipmap.ic_launcher)
                        )
                    } catch (e: Exception) {
                        // Silently fail if drawable not found
                    }
                }

                // Star on GitHub button - opens browser with GitHub repo URL
                starButton.setOnClickListener {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/rajk2007/novacast"))
                        startActivity(intent)
                    } catch (e: Exception) {
                        // Handle case where no browser is available
                    }
                }

                binding.telegramButton.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/novacast_official"))
                    startActivity(intent)
                }

                // Maybe Later button - skip to SetupLayout (final setup step)
                skipButton.setOnClickListener {
                    findNavController().navigate(R.id.action_navigation_setup_github_star_to_navigation_setup_layout)
                }
            }
        }
    }
}
