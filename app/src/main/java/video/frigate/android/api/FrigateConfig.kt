package video.frigate.android.api

import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement

@Serializable
@Keep
class FrigateConfig(
    var cameras_literal: JsonObject
) {

    fun getCameras(): List<FrigateCamera> =
        cameras_literal.values.map { jsonElement ->
            Json.decodeFromJsonElement(jsonElement)
        }
}

@Serializable
@Keep
class FrigateCamera(
    var name: String,
    var detectConfig: DetectConfig
) {

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FrigateCamera>() {

            override fun areItemsTheSame(
                oldItem: FrigateCamera,
                newItem: FrigateCamera
            ): Boolean = oldItem.name == newItem.name

            override fun areContentsTheSame(
                oldItem: FrigateCamera,
                newItem: FrigateCamera
            ): Boolean = oldItem.detectConfig.enabled == newItem.detectConfig.enabled
        }
    }
}

@Serializable
@Keep
class DetectConfig(
    var enabled: Boolean,
    var fps: Int = 5
)