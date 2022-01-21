package video.frigate.android.stream

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.isVisible
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.ext.rtmp.RtmpDataSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import video.frigate.android.Constants
import video.frigate.android.databinding.ActivityStreamBinding
import video.frigate.android.ext.applyBindingInsets
import video.frigate.android.ext.themeBinding

class StreamActivity : AppCompatActivity() {

    /**
     * UI
     */
    private lateinit var binding: ActivityStreamBinding
    private lateinit var player: ExoPlayer

    /**
     * Data
     */
    private val cameraName: String by lazy { intent.getStringExtra(Constants.EXTRA_CAMERA_NAME) ?: "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViews()
    }

    override fun onStart() {
        super.onStart()

        if (Build.VERSION.SDK_INT > 23) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()

        if ((Build.VERSION.SDK_INT <= 23 || !::player.isInitialized)) {
            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()

        if (Build.VERSION.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()

        if (Build.VERSION.SDK_INT > 23) {
            releasePlayer()
        }
    }

    private fun bindViews() {
        binding = ActivityStreamBinding.inflate(layoutInflater)
        setContentView(binding.root)
        themeBinding(binding)
        binding.applyBindingInsets()
        loadData()
    }

    private fun loadData() {
        player = ExoPlayer.Builder(this).build()
        binding.stream.isVisible = true
        binding.stream.player = player
        binding.stream.keepScreenOn = true
        binding.stream.controllerAutoShow = false
        player.playWhenReady = true
        //player.repeatMode = if (intent.getBooleanExtra(EXTRA_VIDEO_REPEAT, false)) Player.REPEAT_MODE_ONE else Player.REPEAT_MODE_OFF
        //player.seekTo(viewModel.currentWindow, viewModel.playbackPosition)
        //binding.video.hideController()
        //binding.loading = false
    }

    private fun initializePlayer() {

        fun buildMediaSource(uri: Uri): MediaSource =
            ProgressiveMediaSource.Factory(RtmpDataSource.Factory()).createMediaSource(MediaItem.fromUri(uri))

        "rtmp://192.168.50.106:1935/live/$cameraName".toUri().let { uri ->
            player.setMediaSource(buildMediaSource(uri))
            player.prepare()

            //if (viewModel.shouldResumeVideo()) {
            //    player.seekTo(viewModel.currentWindow, viewModel.playbackPosition)
            //}
        }
    }

    private fun releasePlayer() {
        //viewModel.setPlayables(player.playWhenReady, player.currentPosition, player.currentMediaItemIndex)
        player.release()
    }
}