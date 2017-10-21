package ru.rougsig.ambercard.common.api

import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import ru.rougsig.ambercard.common.api.responses.TokenResponse

/**
 * Created by rougs on 21-Oct-17.
 */
interface ApiRoutes {
    @FormUrlEncoded
    @POST("login")
    fun signIn(
            @Field("username") login: String,
            @Field("password") password: String
    ): Observable<TokenResponse>
}