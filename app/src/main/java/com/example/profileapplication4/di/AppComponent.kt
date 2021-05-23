package com.example.profileapplication4.di

import android.app.Application
import com.example.profileapplication4.BaseApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityBuilderModule::class,
        ViewModelFactoryModule::class]
)
interface AppComponent : AndroidInjector<BaseApplication> {


    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}