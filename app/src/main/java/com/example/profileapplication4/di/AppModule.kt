package com.example.profileapplication4.di

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.profileapplication4.R
import dagger.Module
import dagger.Provides


@Module
class AppModule {


    @Module
    companion object {


        @Provides
        @JvmStatic
        fun provideProgressDrawable(application: Application): CircularProgressDrawable {
            return CircularProgressDrawable(application).also {
                it.strokeWidth = 10f
                it.centerRadius = 50f
                it.start()
            }
        }


        @Provides
        @JvmStatic
        fun provideRequestOptions(circularProgressDrawable: CircularProgressDrawable): RequestOptions {
            return RequestOptions.placeholderOf(circularProgressDrawable)
                .error(R.drawable.button_background)
        }


        @Provides
        @JvmStatic
        fun provideGlideInstance(
            application: Application,
            requestOptions: RequestOptions
        ): RequestManager {
            return Glide.with(application)
                .setDefaultRequestOptions(requestOptions)
        }





    }


}