package com.example.profileapplication4.di

import android.app.Application
import com.example.profileapplication4.MainActivity
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector


@Module
public abstract class ActivityBuilderModule {


    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity




}