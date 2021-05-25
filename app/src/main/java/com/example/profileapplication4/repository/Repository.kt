package com.example.profileapplication4.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.profileapplication4.ui.loginActivity.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject


class Repository @Inject constructor() {

    private val liveDataResourceLogin = MutableLiveData<Resource<String?>>()

    fun userLogIn(email: String, password: String) {
        liveDataResourceLogin.value = Resource.loading(null)
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    liveDataResourceLogin.value =
                        Resource.success(FirebaseAuth.getInstance().currentUser?.email)
                }
            }
            .addOnFailureListener {
                liveDataResourceLogin.value = Resource.error("Failure", null)
            }

    }

    fun observeLiveData(): LiveData<Resource<String?>> {
        return liveDataResourceLogin
    }




}