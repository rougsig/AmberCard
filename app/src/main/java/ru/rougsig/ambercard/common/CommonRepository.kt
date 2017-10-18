package ru.rougsig.ambercard.common

import io.realm.Realm
import mu.KotlinLogging
import ru.rougsig.ambercard.common.API.REST.Companion.api
import ru.rougsig.ambercard.utils.enqueue

/**
 * Created by rougs on 17-Oct-17.
 */
object CommonRepository {
    private val logger = KotlinLogging.logger("CommonRepository")

    fun getContent(
            onSuccess: () -> Unit,
            onFailure: () -> Unit
    ) {
        api.getContent().enqueue(
                { _, response ->
                    when (response.code()) {
                        200 -> {
                            val data = response.body()!!
                            Realm.getDefaultInstance().executeTransaction { realm ->
                                realm.insertOrUpdate(data.places)
                                realm.insertOrUpdate(data.categories)
                            }
                            onSuccess()
                        }
                        else -> onFailure()
                    }
                },
                { _, t ->
                    logger.error("getContent", t)
                    onFailure()
                }
        )
    }
}