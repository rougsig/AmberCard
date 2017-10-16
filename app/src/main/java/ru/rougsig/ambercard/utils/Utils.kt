package ru.rougsig.ambercard.utils

import io.realm.Realm
import ru.rougsig.ambercard.common.API.REST

/**
 * Created by rougs on 16-Oct-17.
 */

fun transaction(transaction: (realm: Realm) -> Unit) = Realm.Transaction(transaction)