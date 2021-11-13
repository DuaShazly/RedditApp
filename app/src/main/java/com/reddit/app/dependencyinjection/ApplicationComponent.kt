package com.raddit.app.dependencyinjection

import android.app.Application
import com.reddit.app.MvvmRedditApplication
import javax.inject.Singleton
import com.reddit.app.dependencyinjection.module.ApplicationModule
import com.reddit.app.dependencyinjection.module.ClientModule
import com.reddit.app.view.FeedActivity
import dagger.BindsInstance
import dagger.Component

@Singleton
@Component(modules = [ApplicationModule::class, ClientModule::class])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application?): Builder?
        fun build(): ApplicationComponent?
    }

    fun inject(activity: FeedActivity?)
    fun inject(app: MvvmRedditApplication?)
}