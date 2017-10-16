package ru.rougsig.ambercard.common.DB

import io.realm.DynamicRealm
import io.realm.RealmMigration

/**
 * Created by rougs on 15-Oct-17.
 */
class Migration: RealmMigration {
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        val schema = realm.schema
    }
}