package ru.rougsig.ambercard.utils

import io.realm.Realm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by rougs on 16-Oct-17.
 */

fun transaction(transaction: (realm: Realm) -> Unit) = Realm.Transaction(transaction)

fun <T> Call<T>.enqueue(
        onResponse: (call: Call<T>, response: Response<T>) -> Unit,
        onFailure: (call: Call<T>, t: Throwable) -> Unit
) {
    this.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) = onResponse(call, response)
        override fun onFailure(call: Call<T>, t: Throwable) = onFailure(call, t)
    })
}