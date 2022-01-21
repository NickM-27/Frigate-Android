package video.frigate.android.camera

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import video.frigate.android.Constants
import video.frigate.android.api.FrigateCamera
import video.frigate.android.stream.StreamActivity

class CamerasPresenter(private val context: AppCompatActivity) {

    fun onCameraSnapshotClicked(camera: FrigateCamera) {
        context.startActivity(Intent(context, StreamActivity::class.java).apply {
            putExtra(Constants.EXTRA_CAMERA_NAME, camera.name)
        })
    }
}