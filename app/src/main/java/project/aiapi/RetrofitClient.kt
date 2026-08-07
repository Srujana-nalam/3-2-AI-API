package project.aiapi

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    // REMOVED SECRET FOR GITHUB PUSH PROTECTION
    // Replace "YOUR_API_KEY_HERE" with your real key starting with AIzaSy
    private const val API_KEY = "YOUR_API_KEY_HERE"

    private val client = OkHttpClient.Builder().addInterceptor { chain ->
        val original = chain.request()
        val url = original.url().newBuilder()
            .addQueryParameter("key", API_KEY)
            .build()

        val request = original.newBuilder().url(url).build()
        chain.proceed(request)
    }.build()

    val instance: AiApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AiApiService::class.java)
    }
}