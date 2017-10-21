package ru.rougsig.ambercard.common.di.modules

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.rougsig.ambercard.common.api.ApiRoutes
import javax.inject.Singleton

/**
 * Created by rougs on 21-Oct-17.
 */
@Module
class ApiModule {
    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit) = retrofit.create(ApiRoutes::class.java)
}