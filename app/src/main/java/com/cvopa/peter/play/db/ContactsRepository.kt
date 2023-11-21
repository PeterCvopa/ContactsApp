package com.cvopa.peter.play.db

import com.cvopa.peter.play.model.Contact
import com.cvopa.peter.play.model.Favorite
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class ContactsRepositoryImpl @Inject constructor(val db: ContactsDatabase) : ContactsRepository {
    val dao = db.contactsDao()
    override fun getContact(id: String?): Contact {
        TODO("Not yet implemented")
    }

    override fun getAllFavorites(): Flow<List<Favorite>> {
        TODO("Not yet implemented")
    }

    override fun getAllContacts(): Flow<List<Favorite>> {
        TODO("Not yet implemented")
    }

    override fun setContact(contact: Contact) {
        TODO("Not yet implemented")
    }

    override fun deleteContact(id: String) {
        TODO("Not yet implemented")
    }

    override fun toggleFavoriteStatus(id: String) {
        TODO("Not yet implemented")
    }
}

interface ContactsRepository {
    fun getContact(id: String?): Contact
    fun getAllFavorites(): Flow<List<Favorite>>
    fun getAllContacts(): Flow<List<Favorite>>
    fun setContact(contact: Contact)
    fun deleteContact(id: String)
    fun toggleFavoriteStatus(id: String)
}