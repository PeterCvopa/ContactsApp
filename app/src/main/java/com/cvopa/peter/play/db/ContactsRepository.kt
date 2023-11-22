package com.cvopa.peter.play.db

import com.cvopa.peter.play.model.Contact
import com.cvopa.peter.play.model.Favorite
import com.cvopa.peter.play.util.toContact
import com.cvopa.peter.play.util.toEntity
import com.cvopa.peter.play.util.toFavorite
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface ContactsRepository {
    suspend fun getContact(id: Int): Contact
    fun observeAllFavorites(): Flow<List<Favorite>>
    fun observeAllContacts(): Flow<List<Contact>>
    suspend fun setContact(contact: Contact)
    suspend fun deleteContact(id: Int)
    suspend fun setFavoriteStatus(id: Int)
    suspend fun removeFavoriteStatus(id: Int)
}

@Singleton
class ContactsRepositoryImpl @Inject constructor(
    val db: ContactsDatabase
) : ContactsRepository {

    private val dao = db.contactsDao()

    override suspend fun getContact(id: Int): Contact {
        return dao.getContract(id).toContact()
    }

    override fun observeAllFavorites(): Flow<List<Favorite>> {
        return dao.observeAllFavorite().map { list -> list.map { it.toFavorite() } }
    }

    override fun observeAllContacts(): Flow<List<Contact>> {
        return dao.observeAllContacts().map { list -> list.map { it.toContact() } }
    }

    override suspend fun setContact(contact: Contact) {
        dao.insert(contact.toEntity())
    }

    override suspend fun deleteContact(id: Int) {
        dao.delete(id)
    }

    override suspend fun setFavoriteStatus(id: Int) {
        dao.markAsFavorite(id)
    }

    override suspend fun removeFavoriteStatus(id: Int) {
        dao.removeMarkAsFavorite(id)
    }
}