package com.cvopa.peter.play.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.cvopa.peter.play.db.ContactsRepository
import com.cvopa.peter.play.model.Contact
import com.cvopa.peter.play.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.launch

@HiltViewModel
class ContactsVM @Inject constructor(
    private val repository: ContactsRepository
) : BaseViewModel<State>() {

    private val searchFlow = MutableSharedFlow<Unit>()

    init {
        viewModelScope.launch {
            combine(
                repository.observeAllContacts(),
                searchFlow.onSubscription { emit(Unit) }
            ) { item, _ -> filterBySearch(item) }
                .collect {
                    emitState(state = state.value.copy(contactItems = it))
                }
        }
    }

    override val initialState: State
        get() = State(emptyList())

    fun onResetSearch() {
        emitState(state.value.copy(searchValue = ""))
        triggerSearch()
    }

    fun onDelete(id: Int) {
        viewModelScope.launch {
            repository.deleteContact(id)
        }
    }

    fun onSearch(value: String) {
        emitState(state.value.copy(searchValue = value))
        triggerSearch()
    }

    fun onSetFavorite(id: Int, value: Boolean) {
        viewModelScope.launch {
            if (value) {
                repository.setFavoriteStatus(id)
            } else {
                repository.removeFavoriteStatus(id)
            }
        }
    }

    private fun triggerSearch() {
        viewModelScope.launch {
            searchFlow.emit(Unit)
        }
    }

    private fun filterBySearch(contacts: List<Contact>): List<Contact> {
        return contacts.filter {
            if (state.value.searchValue == "") {
                true
            } else {
                it.firstName.contains(state.value.searchValue) ||
                    it.lastName.contains(state.value.searchValue) ||
                    it.phone.contains(state.value.searchValue)
            }
        }
    }
}

data class State(val contactItems: List<Contact>, val searchValue: String = "")

