package ru.rougsig.ambercard.common.API

import retrofit2.Call
import retrofit2.http.*
import ru.rougsig.ambercard.features.place.data.PlaceModel
import ru.rougsig.ambercard.features.user.data.UserRepository
import java.util.*

/**
 * Created by rougs on 14-Oct-17.
 */

//http://138.68.68.166:9999/admin
//http://138.68.68.166:9999/api/1/login/
//http://138.68.68.166:9999/api/1/me/
//http://138.68.68.166:9999/api/1/me/extended
//http://138.68.68.166:9999/api/1/points/all
//http://138.68.68.166:9999/api/1/routes/all
//http://138.68.68.166:9999/api/1/point/{id}
//http://138.68.68.166:9999/api/1/content
//http://138.68.68.166:9999/api/1/favorites/{id}
//http://138.68.68.166:9999/api/1/visited/{id}
//http://138.68.68.166:9999/api/1/wish/{id}

interface RESTRoutes {
    @FormUrlEncoded
    @POST("login")
    fun login(
            @Field("username") user: String,
            @Field("password") pass: String
    ): Call<Map<String, String>>

    @GET("point/{id}")
    fun getPlaceById(
            @Path("id") id: Int,
            @Header("Authorization") token: String = UserRepository.getToken()
    ): Call<PlaceModel>

    // TODO me/
    // TODO me/extended/
    // TODO points/all
    // TODO routes/all
    // TODO content/
    // TODO favorites/{id}
    // TODO visited/{id}
    // TODO wish/{id}
}