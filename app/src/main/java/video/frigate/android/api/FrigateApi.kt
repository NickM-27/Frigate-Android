package video.frigate.android.api

class FrigateApi private constructor() {

    companion object {

        fun getInstance(): FrigateApi =
            FrigateApi()
    }
}