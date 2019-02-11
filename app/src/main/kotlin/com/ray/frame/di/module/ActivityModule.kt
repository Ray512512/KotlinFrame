package com.ray.frame.di.module

import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import dagger.Module
import dagger.Provides
import com.ray.frame.di.scope.PerActivity

/**
 * Created by Ray on 2017/10/18.
 */
@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @PerActivity
    @Provides
    fun providesActivity(): AppCompatActivity = activity

    @PerActivity
    @Provides
    fun providesLayoutInflater(activity: AppCompatActivity): LayoutInflater = LayoutInflater.from(activity)

    @PerActivity
    @Provides
    fun providesFragmentManager(activity: AppCompatActivity): FragmentManager = activity.supportFragmentManager
}