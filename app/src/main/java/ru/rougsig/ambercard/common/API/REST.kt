package ru.rougsig.ambercard.common.API

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.rougsig.ambercard.utils.JsonParser
import ru.rougsig.ambercard.utils.RealmListMoshiJsonAdapter

/**
 * Created by rougs on 16-Oct-17.
 */
class REST private constructor() {
    private object Holder {
        val api = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(MoshiConverterFactory.create(JsonParser.parser))
                .build()!!
                .create(RESTRoutes::class.java)!!
    }

    companion object {
        val baseURL = "http://138.68.68.166:9999/"

        val api: RESTRoutes by lazy { Holder.api }
    }
}