package uk.ac.tees.mad.w9601599

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private val viewModel: UserViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailEditText = findViewById<TextInputEditText>(R.id.emailEditText)
        val passwordEditText = findViewById<TextInputEditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val createAccountButton = findViewById<TextView>(R.id.createAccountButton)


        viewModel.userLiveData.observe(this) { firebaseUser ->
            if (firebaseUser != null) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        viewModel.errorLiveData.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }


        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(email, password)
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }


        createAccountButton.setOnClickListener {
            val intent = Intent(this, RegistationActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        if(FirebaseAuth.getInstance().currentUser!=null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
