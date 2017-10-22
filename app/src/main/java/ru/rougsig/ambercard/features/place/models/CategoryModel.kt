package ru.rougsig.ambercard.features.place.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

/**
 * Created by rougs on 22-Oct-17.
 */
open class CategoryModel : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    @Required
    var name: String = ""
    @Required
    var icon: String = ""
    @Required
    var picture: String = ""
}