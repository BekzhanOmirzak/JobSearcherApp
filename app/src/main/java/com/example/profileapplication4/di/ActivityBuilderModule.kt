package com.example.profileapplication4.di

import com.example.profileapplication4.di.loginActivitymodule.LoginActivityModule
import com.example.profileapplication4.di.mainactivitymodule.MainActivityModule
import com.example.profileapplication4.ui.loginActivity.LogInActivity
import com.example.profileapplication4.ui.mainActivity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilderModule {


    @ContributesAndroidInjector(
        modules = [
            MainActivityModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(
        modules = [
            LoginActivityModule::class
        ]
    )
    abstract fun contributeLoginActivity(): LogInActivity


}