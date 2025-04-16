package com.example.healiohealthapplication.di

import android.content.Context
import com.example.healiohealthapplication.data.remote.FoodApi
import com.example.healiohealthapplication.data.remote.RetrofitInstance
import com.example.healiohealthapplication.data.remote.RetrofitInstance.foodApi
import com.example.healiohealthapplication.utils.StepCounter
import com.example.healiohealthapplication.utils.StepPrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStepPrefs(@ApplicationContext context: Context): StepPrefs {
        return StepPrefs(context)
    }

    @Provides
    @Singleton
    fun provideStepCounter(
        @ApplicationContext context: Context,
        stepPrefs: StepPrefs
    ): StepCounter {
        return StepCounter(context, stepPrefs)
    }

    @Singleton
    @Provides
    fun provideFoodApi(): FoodApi = foodApi

}