package video.frigate.android.ext

import android.app.Activity
import androidx.core.view.WindowCompat
import androidx.databinding.ViewDataBinding
import video.frigate.android.databinding.ActivityDashboardBinding

fun ViewDataBinding.applyBindingInsets() {
    if (root.context is Activity) {
        WindowCompat.setDecorFitsSystemWindows((root.context as Activity).window, false)
    }

    when (this) {
        is ActivityDashboardBinding -> {

        }
    }
}