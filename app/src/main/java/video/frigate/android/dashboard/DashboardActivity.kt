package video.frigate.android.dashboard

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collectLatest
import video.frigate.android.camera.CamerasAdapter
import video.frigate.android.databinding.ActivityDashboardBinding
import video.frigate.android.ext.applyBindingInsets
import video.frigate.android.ext.themeBinding
import video.frigate.android.helper.EndOffsetItemDecoration

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
        themeBinding(binding)
        binding.applyBindingInsets()
        binding.cameras.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CamerasAdapter(this@DashboardActivity).also { adapter -> camerasAdapter = adapter }
        }

        binding.toolbar.doOnPreDraw {
            if (binding.cameras.itemDecorationCount == 0) {
                binding.cameras.addItemDecoration(EndOffsetItemDecoration())
            }
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