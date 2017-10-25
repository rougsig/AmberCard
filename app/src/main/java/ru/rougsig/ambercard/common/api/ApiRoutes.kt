package ru.rougsig.ambercard.common.api

import android.location.Geocoder
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*
import ru.rougsig.ambercard.features.place.models.ContentResponse
import ru.rougsig.ambercard.features.place.models.PlaceModel
import ru.rougsig.ambercard.features.user.auth.models.TokenResponse

/**
 * Created by rougs on 21-Oct-17.
 */
interface ApiRoutes {
    @FormUrlEncoded
    @POST("login")
    fun signIn(
            @Field("username") login: String,
            @Field("password") password: String
    ): Single<TokenResponse>

    @GET("content")
    fun getContent(
            @Header("Authorization") token: String
    ): Single<ContentResponse>

    @GET("point/{id}")
    fun getPlaceById(
            @Path("id") id: Int,
            @Header("Authorization") token: String
    ): Single<PlaceModel>

    // ForJobTest
    @FormUrlEncoded
    @POST("http://test.rougsig.ru/test/send.php")
    fun sendEmail(
            @Field("jobTag") jobTag: String
    ): Call<Void>
}