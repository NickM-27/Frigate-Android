package video.frigate.android.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import video.frigate.android.api.FrigateApi
import video.frigate.android.api.FrigateConfig

class DashboardViewModel : ViewModel() {

    private val client = FrigateApi.getInstance()
    private val frigateConfig: MutableStateFlow<FrigateConfig?> = MutableStateFlow(null)

    fun getFrigateConfig(): StateFlow<FrigateConfig?> {
        viewModelScope.launch(Dispatchers.Default) {
            frigateConfig.emit(client.getConfig())
        }

        return frigateConfig
    }
}