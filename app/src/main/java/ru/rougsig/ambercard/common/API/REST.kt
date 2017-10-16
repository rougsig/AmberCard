package ru.rougsig.ambercard.common.API

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.rougsig.ambercard.utils.JsonParser


/**
 * Created by rougs on 16-Oct-17.
 */
class REST private constructor() {
    private object Holder {
        val api = Retrofit.Builder()
                .client(
                        OkHttpClient.Builder()
                                .addInterceptor(
                                        HttpLoggingInterceptor()
                                                .apply { level = HttpLoggingInterceptor.Level.BODY }
                                )
                                .build()
                )
                .baseUrl(apiUrl)
                .addConverterFactory(MoshiConverterFactory.create(JsonParser.parser))
                .build()!!
                .create(RESTRoutes::class.java)!!
    }

    companion object {
        val baseURL = "http://138.68.68.166:9999/"
        val apiUrl = "http://138.68.68.166:9999/api/1/"

        val api: RESTRoutes by lazy { Holder.api }
    }
}