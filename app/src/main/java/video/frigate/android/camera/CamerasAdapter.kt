package video.frigate.android.camera

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import video.frigate.android.R
import video.frigate.android.api.FrigateCamera
import video.frigate.android.databinding.ViewHolderCameraBinding

class CamerasAdapter(context: AppCompatActivity) : ListAdapter<FrigateCamera, RecyclerView.ViewHolder>(FrigateCamera.DIFF_CALLBACK) {

    private val layoutInflater = LayoutInflater.from(context)
    private val presenter = CamerasPresenter(context)
    private val clickHandler: (Int, View) -> Unit = { position, view ->
        when (view.id) {
            R.id.camera_snapshot -> presenter.onCameraSnapshotClicked(getItem(position))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        CameraViewHolder(ViewHolderCameraBinding.inflate(layoutInflater, parent, false), clickHandler)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as CameraViewHolder).bindData(getItem(position))

    private class CameraViewHolder(private val binding: ViewHolderCameraBinding, onClick: (Int, View) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.cameraSnapshot.setOnClickListener { view -> onClick(bindingAdapterPosition, view) }
        }

        fun bindData(data: FrigateCamera) {
            binding.camera = data
            binding.executePendingBindings()
        }
    }
}