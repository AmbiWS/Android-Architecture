package com.ambiws.androidarchitecture.features.splash

import android.view.View
import android.view.animation.AccelerateInterpolator
import com.ambiws.androidarchitecture.base.BaseFragment
import com.ambiws.androidarchitecture.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment<FragmentSplashBinding>(
    FragmentSplashBinding::inflate
) {

    private val logoFadeInDurationMs = 1300L
    private val splashScreenFadeOutDurationMs = 500L
    private val splashScreenIdleDurationMs = 700L

    fun runSplashAnimation(onSplashAnimationEnd: (() -> Unit)? = null) {
        with(binding) {
            ivSplashLogo.fadeAnimation(1f, logoFadeInDurationMs) {
                splashScreenContainer.fadeAnimation(
                    0f,
                    splashScreenFadeOutDurationMs,
                    splashScreenIdleDurationMs
                ) {
                    onSplashAnimationEnd?.invoke()
                }
            }
        }
    }

    private fun View.fadeAnimation(
        toValue: Float,
        durationMs: Long,
        startDelayMs: Long = 0,
        endAction: (() -> Unit)? = null
    ) {
        this.animate()
            .alpha(toValue)
            .setDuration(durationMs)
            .setInterpolator(AccelerateInterpolator())
            .withEndAction {
                endAction?.invoke()
            }
            .setStartDelay(startDelayMs)
            .start()
    }
}
