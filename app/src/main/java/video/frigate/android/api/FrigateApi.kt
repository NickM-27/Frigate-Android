package video.frigate.android.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import video.frigate.android.ext.isTrue

@Suppress("BlockingMethodInNonBlockingContext")
class FrigateApi private constructor() {

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()
    private val client: PrivateClient =
        Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient).addConverterFactory(
            GsonConverterFactory.create()
        ).build().create(PrivateClient::class.java)

    suspend fun getConfig(): FrigateConfig? = withContext(Dispatchers.Default) {
        try {
            client.getConfig().execute().let { response ->
                if (response.isSuccessful.isTrue()) {
                    response.body()
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private interface PrivateClient {

        @GET("config")
        fun getConfig(): Call<FrigateConfig>
    }

    companion object {

        private const val BASE_URL = "192.168.50.106:5000/api/"

        fun getInstance(): FrigateApi =
            FrigateApi()
    }
}