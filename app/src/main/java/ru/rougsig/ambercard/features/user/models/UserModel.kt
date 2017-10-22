package ru.rougsig.ambercard.features.user.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

/**
 * Created by rougs on 22-Oct-17.
 */
open class UserModel constructor() : RealmObject() {
    @PrimaryKey
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