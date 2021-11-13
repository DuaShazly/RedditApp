package com.reddit.app.dependencyinjection.module;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;


import com.reddit.app.MvvmRedditApplication;

import dagger.android.support.AndroidSupportInjection;


public class AppInjector {
    private AppInjector() {}

    public static void init(MvvmRedditApplication app) {
        DaggerApplicationComponent.builder()
                .application(app)
                .build()
                .inject(app);
        app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                injectIntoActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) { }

            @Override
            public void onActivityResumed(Activity activity) { }

            @Override
            public void onActivityPaused(Activity activity) { }

            @Override
            public void onActivityStopped(Activity activity) { }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) { }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }

    private static void injectIntoActivity(Activity activity) {
        if (activity instanceof FragmentActivity) {
            FragmentActivity fragmentActivity = (FragmentActivity) activity;
            fragmentActivity.getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentLifecycleCallbacks(), true);
        }
    }

    private static class FragmentLifecycleCallbacks extends FragmentManager.FragmentLifecycleCallbacks {
        @Override
        public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
            if (f instanceof Injectable) {
                AndroidSupportInjection.inject(f);
            }
        }
    }
}
