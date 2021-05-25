package com.example.profileapplication4.ui.loginActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.profileapplication4.repository.Repository
import javax.inject.Inject


class LoginActivityViewModel @Inject constructor(val repository: Repository) : ViewModel() {


    fun signInEmailPassword(email: String, password: String) {
        repository.userLogIn(email, password)
    }

    fun observeLiveData(): LiveData<Resource<String?>> {
        return repository.observeLiveData()
    }


}