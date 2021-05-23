package com.example.profileapplication4.viewmodel.viewmodelmodule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.profileapplication4.di.viewmodelfactory.ViewModelFactory
import com.example.profileapplication4.di.viewmodelfactory.ViewModelKey
import com.example.profileapplication4.viewmodel.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun splashViewModel(viewModel: MainViewModel): ViewModel


}