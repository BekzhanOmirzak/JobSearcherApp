package com.example.profileapplication4.ui.mainActivity

import androidx.lifecycle.ViewModel
import com.example.profileapplication4.Repository
import javax.inject.Inject



class MainActivityViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    fun provideString() = repository.provideString()

}