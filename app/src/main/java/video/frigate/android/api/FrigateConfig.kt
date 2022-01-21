package video.frigate.android.api

import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import video.frigate.android.FrigateApp
import java.util.*

@Serializable
@Keep
class FrigateConfig(

    @SerialName("cameras")
    var cameras_literal: JsonObject
) {

    fun getCameras(): List<FrigateCamera> =
        cameras_literal.values.map { jsonElement ->
            FrigateApp.json.decodeFromJsonElement(jsonElement)
        }
}

@Serializable
@Keep
class FrigateCamera(

    var name: String,

    @SerialName("detect")
    var detectConfig: DetectConfig
) {

    fun getFormattedName(): String =
        name.split('_')
            .map { string -> string.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } }
            .joinToString(separator = " ") { it }

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