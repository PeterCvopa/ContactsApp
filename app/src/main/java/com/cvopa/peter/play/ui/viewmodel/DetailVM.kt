package com.cvopa.peter.play.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.cvopa.peter.play.db.ContactsRepository
import com.cvopa.peter.play.model.Contact
import com.cvopa.peter.play.ui.common.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class DetailVM @AssistedInject constructor(
    private val repository: ContactsRepository,
    @Assisted id: Int,
) : BaseViewModel<DetailState>() {

    init {
        if (id.isNewContact().not()) {
            viewModelScope.launch {
                val contact = repository.getContact(id)
                emitState(state.value.copy(contact = contact))
            }
        }
    }

    override val initialState: DetailState
        get() = DetailState()

    fun onFirstName(value: String) {
        emitState(state.value.copy(contact = state.value.contact.copy(firstName = value)))
    }

    fun onLastName(value: String) {
        emitState(state.value.copy(contact = state.value.contact.copy(lastName = value)))
    }

    fun onPhoneNumber(value: String) {
        emitState(state.value.copy(contact = state.value.contact.copy(phone = value)))
    }

    fun onSave() {
        viewModelScope.launch {
            repository.setContact(state.value.contact)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(id: Int): DetailVM
    }
}

data class DetailState(
    val contact: Contact = emptyContact,
)

val emptyContact = Contact(0, "", "", "", false)

private fun Int.isNewContact(): Boolean {
    return this < 0
}

