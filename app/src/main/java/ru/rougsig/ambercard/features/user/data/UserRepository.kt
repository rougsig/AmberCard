package ru.rougsig.ambercard.features.user.data

import io.realm.Realm
import kotlinx.android.synthetic.main.activity_login.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.rougsig.ambercard.common.API.REST
import ru.rougsig.ambercard.utils.transaction

/**
 * Created by rougs on 16-Oct-17.
 */
object UserRepository {
    fun getToken(
            login: String,
            pass: String,
            onSuccess: (user: UserModel) -> Unit,
            onFailure: () -> Unit,
            onUnauthorized: () -> Unit
    ) {
        REST.api.login(
                login,
                pass
        ).enqueue(object : Callback<Map<String, String>> {
            override fun onResponse(call: Call<Map<String, String>>, response: Response<Map<String, String>>) {
                when (response.code()) {
                    200 -> {
                        val token = response.body()!!["token"]!!
                        val user = UserModel()
                        user.login = login
                        user.token = "Token " + token
                        Realm.getDefaultInstance().executeTransaction { realm ->
                            realm.where(UserModel::class.java).findAll().deleteAllFromRealm()
                            realm.copyToRealmOrUpdate(user)
                        }
                        onSuccess(user)
                    }
                    401 -> onUnauthorized()
                    else -> onFailure()
                }
            }

            override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                onFailure()
            }
        })
    }

    fun getUser(loaded: (user: UserModel?) -> Unit) {
        Realm.getDefaultInstance().executeTransaction { realm ->
            loaded(realm.where(UserModel::class.java).findFirst())
        }
    }

    fun getToken(): String {
        var token: String? = null
        getUser { user ->
            token = user!!.token
        }
        return token!!
    }
}