package ru.rougsig.ambercard.common.di.modules

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.rougsig.ambercard.common.api.apiUrl
import ru.rougsig.ambercard.common.json.CategoryFromIdAdapter
import ru.rougsig.ambercard.common.json.RealmListMoshiJsonAdapter
import javax.inject.Singleton

/**
 * Created by rougs on 21-Oct-17.
 */
@Module
class RetrofitModule {
    @Provides
    @Singleton
    fun provideRetrofit(converterFactory: MoshiConverterFactory) = Retrofit.Builder()
            .client(OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(converterFactory)
            .baseUrl(apiUrl)
            .build()

    @Provides
    @Singleton
    fun provideConverterFactory(moshi: Moshi) = MoshiConverterFactory.create(moshi)

    @Provides
    @Singleton
    fun provideMoshi() = Moshi.Builder()
            .add(RealmListMoshiJsonAdapter())
            .add(CategoryFromIdAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()
}