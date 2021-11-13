package com.reddit.app

import android.app.Activity
import org.androidannotations.annotations.EApplication
import android.app.Application
import com.raddit.app.dependencyinjection.ApplicationComponent
import com.reddit.app.dependencyinjection.module.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject


@EApplication
class MvvmRedditApplication : Application() {

    private var applicationComponent: ApplicationComponent? = null

    // implements HasActivityInjector
    @Inject
    var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>? = null

    @Inject
    var androidInjector: DispatchingAndroidInjector<Any>? = null


    private val mAppComponent: ApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)

        applicationComponent!!.inject(this)
    }


    fun androidInjector(): AndroidInjector<Any?>? {
        return androidInjector
    }


}