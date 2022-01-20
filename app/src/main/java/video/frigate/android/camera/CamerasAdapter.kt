package video.frigate.android.camera

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import video.frigate.android.api.FrigateCamera
import video.frigate.android.databinding.ViewHolderCameraBinding

class CamerasAdapter(context: Context) : ListAdapter<FrigateCamera, RecyclerView.ViewHolder>(FrigateCamera.DIFF_CALLBACK) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        CameraViewHolder(ViewHolderCameraBinding.inflate(layoutInflater, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as CameraViewHolder).bindData(getItem(position))

    private class CameraViewHolder(private val binding: ViewHolderCameraBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: FrigateCamera) {
            binding.camera = data
            binding.executePendingBindings()
        }
    }
}