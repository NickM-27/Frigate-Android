package video.frigate.android.ext

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import androidx.databinding.ViewDataBinding
import video.frigate.android.databinding.ActivityDashboardBinding

private fun View.setDarkStatusIcons() {
    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> windowInsetsController?.let {
            it.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
            it.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS, WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS)
        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
            @Suppress("DEPRECATION")
            systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
        Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1 -> {
            @Suppress("DEPRECATION")
            systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}

fun Context.isDarkTheme(): Boolean =
    when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_YES, Configuration.UI_MODE_NIGHT_UNDEFINED -> true
        else -> false
    }

private fun Context.hideSystemShadows() {
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && this is Activity)
            window.isNavigationBarContrastEnforced = false
    } catch (e: NoSuchMethodError) {
    }
}

fun Context.themeBinding(binding: ViewDataBinding) {
    hideSystemShadows()

    if (!isDarkTheme()) {
        binding.root.setDarkStatusIcons()
    }

    when (binding) {
        is ActivityDashboardBinding -> {

        }
    }
}