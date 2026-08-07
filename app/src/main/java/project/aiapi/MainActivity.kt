package project.aiapi

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Initialize the ViewModel correctly without .kt in the class name
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Bind Views
        val tvResponse = findViewById<TextView>(R.id.tvResponse)
        val etPrompt = findViewById<EditText>(R.id.etPrompt)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        // 1. Observe AI response changes
        viewModel.aiResult.observe(this) { resultText ->
            tvResponse.text = resultText
        }

        // 2. Observe loading state to toggle progress bar visibility
        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
                btnSubmit.isEnabled = false
            } else {
                progressBar.visibility = View.GONE
                btnSubmit.isEnabled = true
            }
        }

        // 3. Handle Button Clicks
        btnSubmit.setOnClickListener {
            val prompt = etPrompt.text.toString().trim()
            if (prompt.isNotEmpty()) {
                viewModel.fetchAiResponse(prompt)
            } else {
                Toast.makeText(this, "Please enter a prompt first!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}