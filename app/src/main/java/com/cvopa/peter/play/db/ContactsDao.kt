package com.cvopa.peter.play.db

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: ContactEntity)

    @Query("SELECT * FROM contact WHERE id = :id")
    suspend fun getContract(id: Int): ContactEntity

    @Query("UPDATE contact SET isFavorite = 1 WHERE id = :id")
    suspend fun markAsFavorite(id: Int)

    @Query("UPDATE contact SET isFavorite = 0 WHERE id = :id")
    suspend fun removeMarkAsFavorite(id: Int)

    @Query("SELECT * FROM contact")
    fun observeAllContacts(): Flow<List<ContactEntity>>

    @Query("SELECT * FROM contact WHERE isFavorite = 1")
    fun observeAllFavorite(): Flow<List<ContactEntity>>

    @Query("DELETE FROM contact WHERE id = :id")
    suspend fun delete(id: Int)
}

@Entity(tableName = "contact")
data class ContactEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val isFavorite: Boolean = false
)

