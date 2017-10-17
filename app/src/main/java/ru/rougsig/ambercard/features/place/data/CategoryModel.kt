package ru.rougsig.ambercard.features.place.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

/**
 * Created by rougs on 11-Oct-17.
 */
open class CategoryModel : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    @Required
    var name: String? = null
    @Required
    var icon: String? = null
    @Required
    var picture: String? = null
}