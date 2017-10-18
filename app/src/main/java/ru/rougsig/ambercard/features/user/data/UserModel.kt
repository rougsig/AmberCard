package ru.rougsig.ambercard.features.user.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

/**
 * Created by rougs on 16-Oct-17.
 */
open class UserModel constructor() : RealmObject() {
    @PrimaryKey
    @Required
    lateinit var login: String
        private set

    @Required
    lateinit var token: String
        private set

    constructor(
            login: String,
            token: String
    ) : this() {
        this.login = login
        this.token = token
    }
}