package ru.rougsig.ambercard.features.place.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

/**
 * Created by rougs on 11-Oct-17.
 */
open class CategoryModel constructor() : RealmObject() {
    @PrimaryKey
    var id: Int = 0
        private set

    @Required
    lateinit var name: String
        private set

    @Required
    lateinit var icon: String
        private set

    @Required
    lateinit var picture: String
        private set

    constructor(
            id: Int,
            name: String = "",
            icon: String = "",
            picture: String = ""
    ) : this() {
        this.id = id
        this.name = name
        this.icon = icon
        this.picture = picture
    }
}