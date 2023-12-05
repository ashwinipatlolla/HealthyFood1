package uk.ac.tees.mad.w9601599

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class UserViewModel : ViewModel() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    val userLiveData: MutableLiveData<FirebaseUser?> = MutableLiveData()
    val errorLiveData: MutableLiveData<String> = MutableLiveData()

    fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userLiveData.postValue(auth.currentUser)
                } else {
                    errorLiveData.postValue(task.exception?.message)
                }
            }
    }

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userLiveData.postValue(auth.currentUser)
                } else {
                    errorLiveData.postValue(task.exception?.message)
                }
            }
    }
}
