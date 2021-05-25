package com.example.profileapplication4.ui.registerActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.profileapplication4.repository.Repository
import com.example.profileapplication4.ui.loginActivity.Resource
import javax.inject.Inject


class RegisterActivityViewModel @Inject constructor(val repository: Repository) : ViewModel() {


    fun registeringTheUser(
        email: String,
        password: String,
        name: String,
        surname: String,
        userType: Int
    ) {
        repository.registerTheUser(email, password, name, surname, userType)
    }

    fun observingLiveDataRegisteringUser(): LiveData<Resource<String?>> {
        return repository.observingLiveDataRegisteringUser()
    }




}