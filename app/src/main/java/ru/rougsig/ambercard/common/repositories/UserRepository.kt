package ru.rougsig.ambercard.common.repositories

import io.realm.Realm
import ru.rougsig.ambercard.common.api.ApiRoutes
import ru.rougsig.ambercard.features.user.models.UserModel
import ru.rougsig.ambercard.utils.applySchedulers

/**
 * Created by rougs on 22-Oct-17.
 */
class UserRepository(val api: ApiRoutes) {
    fun signIn(
            login: String,
            password: String
    ) = api.signIn(login, password)
            .applySchedulers()
            .doOnSuccess { token ->
                val user = UserModel(login, "Token " + token.token)
                Realm.getDefaultInstance().executeTransaction { realm ->
                    realm.where(UserModel::class.java).findAll().deleteAllFromRealm()
                    realm.copyToRealmOrUpdate(user)
                }
            }

    fun getNullableUser() = Realm.getDefaultInstance().where(UserModel::class.java).findFirst()
    fun getUser() = Realm.getDefaultInstance().where(UserModel::class.java).findFirst()!!
    fun getToken() = getUser().token
}