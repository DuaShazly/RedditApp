package com.reddit.app.dependencyinjection.module

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.reddit.app.MvvmRedditApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: MvvmRedditApplication){

    @Singleton
    @Provides
    fun provideContext(application: Application): Context? {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideResources(application: Application): Resources? {
        return application.resources
    }


}