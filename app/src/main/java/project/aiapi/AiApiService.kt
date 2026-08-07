package project.aiapi

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AiApiService {
    @POST("v1beta/models/gemini-1.5-flash:generateContent")
    suspend fun getAiResponse(
        @Body request: GeminiRequest
    ): Response<GeminiResponse>
}