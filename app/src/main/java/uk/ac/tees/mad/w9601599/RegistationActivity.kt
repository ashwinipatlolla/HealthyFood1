package uk.ac.tees.mad.w9601599

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class RegistationActivity : AppCompatActivity() {

    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registation)

        val emailEditText = findViewById<TextInputEditText>(R.id.emailEditText)
        val passwordEditText = findViewById<TextInputEditText>(R.id.passwordEditText)
        val confirmPasswordEditText = findViewById<TextInputEditText>(R.id.confirmPasswordEditText)
        val registerButton = findViewById<Button>(R.id.registerButton)
        val loginTextView = findViewById<TextView>(R.id.loginTextView)


        viewModel.userLiveData.observe(this) { firebaseUser ->
            if (firebaseUser != null) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        viewModel.errorLiveData.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty() && password == confirmPassword) {
                viewModel.register(email, password)
            } else {
                Toast.makeText(this, "Please enter valid details or make sure passwords match", Toast.LENGTH_SHORT).show()
            }
        }
        loginTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
