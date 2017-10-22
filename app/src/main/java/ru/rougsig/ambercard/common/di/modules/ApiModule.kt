package ru.rougsig.ambercard.common.di.modules

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.rougsig.ambercard.common.api.ApiRoutes
import ru.rougsig.ambercard.common.repositories.CategoryRepository
import ru.rougsig.ambercard.common.repositories.PlaceRepository
import ru.rougsig.ambercard.common.repositories.UserRepository
import javax.inject.Singleton

/**
 * Created by rougs on 21-Oct-17.
 */
@Module
class ApiModule {
    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit) = retrofit.create(ApiRoutes::class.java)

    @Provides
    @Singleton
    fun provideCategoryRepository(api: ApiRoutes) = CategoryRepository(api)

    @Provides
    @Singleton
    fun providePlaceRepository(api: ApiRoutes, userRepository: UserRepository) = PlaceRepository(api, userRepository)

    @Provides
    @Singleton
    fun provideUserRepository(api: ApiRoutes) = UserRepository(api)
}