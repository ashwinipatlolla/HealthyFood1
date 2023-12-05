package uk.ac.tees.mad.w9601599

import android.content.Intent
import android.os.Build
import androidx.biometric.BiometricPrompt
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        findViewById<FrameLayout>(R.id.recipe).setOnClickListener{
            startActivity(Intent(applicationContext,Receipe::class.java))
        }
        findViewById<Button>(R.id.logoutButton).setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(applicationContext,LoginActivity::class.java))
            finish()
        }
        findViewById<FrameLayout>(R.id.dietcalc).setOnClickListener{
            startActivity(Intent(applicationContext,DietCalculator::class.java))
        }

        findViewById<FrameLayout>(R.id.store).setOnClickListener{
            startActivity(Intent(applicationContext,HealthyStore::class.java))
        }

        findViewById<FrameLayout>(R.id.comm).setOnClickListener{
            startActivity(Intent(applicationContext,Community::class.java))
        }
        findViewById<FrameLayout>(R.id.foods).setOnClickListener{
            startActivity(Intent(applicationContext,ImageViewer::class.java))
        }
        showBiometricPrompt()
    }


    override fun onStart() {
        super.onStart()
        if(FirebaseAuth.getInstance().currentUser==null){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun isBiometricReady(): Boolean {
        val biometricManager = BiometricManager.from(this)
        return biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS
    }

    private fun showBiometricPrompt() {
        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = androidx.biometric.BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                // Handle error
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                // Authentication succeeded, proceed with the secure action
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                // Handle failure
            }
        })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Fingerprint Authentication")
            .setDescription("Use your fingerprint to authenticate")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}