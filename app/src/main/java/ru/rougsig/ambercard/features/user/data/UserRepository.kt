package ru.rougsig.ambercard.features.user.data

import io.realm.Realm
import ru.rougsig.ambercard.common.API.REST
import ru.rougsig.ambercard.utils.enqueue

/**
 * Created by rougs on 16-Oct-17.
 */
object UserRepository {
    fun getToken(
            login: String,
            pass: String,
            onSuccess: (user: UserModel) -> Unit,
            onLoginUnauthorized: () -> Unit
    ) {
        REST.api.login(
                login,
                pass
        ).enqueue(
                { _, response ->
                    when (response.code()) {
                        200 -> {
                            val token = response.body()!!.token
                            val user = UserModel(
                                    login,
                                    "token $token"
                            )
                            Realm.getDefaultInstance().executeTransaction { realm ->
                                realm.where(UserModel::class.java).findAll().deleteAllFromRealm()
                                realm.copyToRealmOrUpdate(user)
                            }
                            onSuccess(user)
                        }
                        401 -> onLoginUnauthorized()
                    }
                },
                { _, t -> throw t }
        )
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