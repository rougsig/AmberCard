package ru.rougsig.ambercard.common.db

import io.realm.DynamicRealm
import io.realm.RealmMigration

/**
 * Created by rougs on 22-Oct-17.
 */
class Migration: RealmMigration {
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        val schema = realm.schema
    }
}