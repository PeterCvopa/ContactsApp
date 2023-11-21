package com.cvopa.peter.play.db

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Update
import io.reactivex.Completable

@Dao
interface ContactsDao {

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    abstract suspend fun insert(contact: ContactEntity)
}

@Entity(tableName = "contact")
data class ContactEntity(
    @PrimaryKey(autoGenerate = true)val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val isFavorite: Boolean = false
)

