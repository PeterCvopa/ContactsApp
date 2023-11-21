package com.cvopa.peter.play.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.cvopa.peter.play.model.Contact
import com.cvopa.peter.play.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class ContactsVM @Inject constructor() : BaseViewModel<State>() {

    private val testFlow = flow{
        kotlinx.coroutines.delay(4000)

        emit(listOf( Contact(1,"2","3", "4334", false)))
    }

    init {
        Timber.d("peter ContactsVM init")
        viewModelScope.launch {
            testFlow.collect{
               emitState(state.value.copy(contactItems = it))
            }
        }
    }

    override val initialState: State
        get() = State()
}

data class State(val contactItems: List<Contact> = listOf(Contact(1,"fdsf", "fsfsdfd" ,"fds",false )))



