package com.cvopa.peter.play.util

import com.cvopa.peter.play.db.ContactEntity
import com.cvopa.peter.play.model.Contact
import com.cvopa.peter.play.model.Favorite

fun ContactEntity.toContact(): Contact {
    return Contact(
        id = id,
        firstName = firstName,
        lastName = lastName,
        phone = phone,
        isFavorite = isFavorite
    )
}

fun Contact.toEntity(): ContactEntity{
    return ContactEntity(id, firstName, lastName, phone, isFavorite)
}

fun ContactEntity.toFavorite(): Favorite {
    return Favorite(
        id = id,
        firstName = firstName,
        lastName = lastName,
        phone = phone
    )
}