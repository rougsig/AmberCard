package ru.rougsig.ambercard.features.user.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

/**
 * Created by rougs on 16-Oct-17.
 */
open class UserModel : RealmObject() {
    @PrimaryKey
    var login: String? = null
    @Required
    var token: String? = null
}