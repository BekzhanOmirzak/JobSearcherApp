package com.example.profileapplication4.di

import com.example.profileapplication4.di.mainactivitymodule.MainActivityViewModelModule
import com.example.profileapplication4.ui.mainActivity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilderModule {


    @ContributesAndroidInjector(
        modules = [
            MainActivityViewModelModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity


}