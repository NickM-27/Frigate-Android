package video.frigate.android.dashboard

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collectLatest
import video.frigate.android.camera.CamerasAdapter
import video.frigate.android.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var camerasAdapter: CamerasAdapter
    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViews()
    }

    private fun bindViews() {
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.cameras.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CamerasAdapter(context).also { adapter -> camerasAdapter = adapter }
        }
        loadData()
    }

    private fun loadData() {
        lifecycleScope.launchWhenStarted {
            viewModel.getFrigateConfig().collectLatest { config ->
                config ?: return@collectLatest
                camerasAdapter.submitList(config.getCameras())
            }
        }
    }
}