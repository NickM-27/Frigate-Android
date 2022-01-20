package video.frigate.android.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation

/**
 * Static class which holds binding methods
 */
object BindingAdapter {

    /**
     * Sets a fade-in circular profile image to view
     * @param name of camera
     */
    @BindingAdapter("snapshot")
    @JvmStatic
    fun ImageView.setCameraSnapshot(name: String?) {
        name ?: return
        load("192.168.50.106:5000/api/$name/latest.jpg") {
            crossfade(false)
            transformations(CircleCropTransformation())
        }
    }
}