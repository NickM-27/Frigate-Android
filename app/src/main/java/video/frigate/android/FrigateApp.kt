package video.frigate.android

import android.app.Application
import kotlinx.serialization.json.Json

class FrigateApp : Application() {

    override fun onCreate() {
        super.onCreate()

    }

    companion object {

        val json: Json = Json { ignoreUnknownKeys = true }
    }
}