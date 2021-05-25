package com.example.profileapplication4.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.profileapplication4.di.loginActivitymodule.LoginActivityModule
import com.example.profileapplication4.ui.loginActivity.LoginActivityViewModel
import com.example.profileapplication4.ui.mainActivity.MainActivityViewModel
import com.example.profileapplication4.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory


    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActViewModel(viewModel: MainActivityViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(LoginActivityViewModel::class)
    abstract fun bindLoginActivityViewModel(viewModel: LoginActivityViewModel): ViewModel


}