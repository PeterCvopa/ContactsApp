package com.cvopa.peter.play.db

import androidx.room.Database
import androidx.room.RoomDatabase

const val VERSION = 1
@Database(entities = [ContactEntity::class], version = VERSION, exportSchema = false)
abstract class ContactsDatabase : RoomDatabase() {
    abstract fun contactsDao(): ContactsDao
}