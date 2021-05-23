package com.example.profileapplication4.viewmodel

import androidx.lifecycle.ViewModel
import com.example.profileapplication4.Repository
import javax.inject.Inject



class MainViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    fun provideString() = repository.provideString()

}