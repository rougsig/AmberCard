package ru.rougsig.ambercard.features.user.data

import io.realm.Realm
import mu.KotlinLogging
import ru.rougsig.ambercard.common.API.REST
import ru.rougsig.ambercard.common.CommonRepository
import ru.rougsig.ambercard.utils.enqueue

/**
 * Created by rougs on 16-Oct-17.
 */
object UserRepository {
    private val logger = KotlinLogging.logger("UserRepository")

    fun login(
            login: String,
            pass: String,
            onSuccess: () -> Unit,
            onUnauthorized: () -> Unit,
            onFailure: () -> Unit
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
                            onSuccess()
                        }
                        401 -> onUnauthorized()
                        else -> onFailure()
                    }
                },
                { _, t ->
                    logger.error("login", t)
                    onFailure()
                }
        )
    }

    fun getUser(): UserModel? = Realm.getDefaultInstance().where(UserModel::class.java).findFirst()

    fun getToken(): String = getUser()!!.token
}