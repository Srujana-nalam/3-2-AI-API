package project.aiapi

import com.google.gson.annotations.SerializedName

// Gemini Request Structure
data class GeminiRequest(
    @SerializedName("contents") val contents: List<Content>
)

data class Content(
    @SerializedName("parts") val parts: List<Part>
)

data class Part(
    @SerializedName("text") val text: String
)

// Gemini Response Structure
data class GeminiResponse(
    @SerializedName("candidates") val candidates: List<Candidate>?
)

data class Candidate(
    @SerializedName("content") val content: GeminiContent?
)

data class GeminiContent(
    @SerializedName("parts") val parts: List<Part>?
)