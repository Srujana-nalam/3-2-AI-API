package project.aiapi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _aiResult = MutableLiveData<String>()
    val aiResult: LiveData<String> get() = _aiResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun fetchAiResponse(prompt: String) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val requestBody = GeminiRequest(
                    contents = listOf(
                        Content(parts = listOf(Part(text = prompt)))
                    )
                )

                // API Key is handled in RetrofitClient interceptor
                val response = RetrofitClient.instance.getAiResponse(requestBody)

                if (response.isSuccessful && response.body() != null) {
                    val aiText = response.body()!!.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                        ?: "No text returned."
                    _aiResult.postValue(aiText.trim())
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    _aiResult.postValue("Error ${response.code()}: $errorBody")
                }
            } catch (e: Exception) {
                _aiResult.postValue("Connection Failed: ${e.localizedMessage}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}