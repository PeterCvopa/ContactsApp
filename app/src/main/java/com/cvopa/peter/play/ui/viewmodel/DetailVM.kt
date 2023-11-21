package com.cvopa.peter.play.ui.viewmodel

import com.cvopa.peter.play.db.ContactsRepository
import com.cvopa.peter.play.model.Contact
import com.cvopa.peter.play.ui.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class DetailVM @AssistedInject constructor(
    repository: ContactsRepository,
    @Assisted id: String?
) : BaseViewModel<DetailState>() {

    init {
        repository.getContact(id)
    }

    override val initialState: DetailState
        get() = DetailState()

    @AssistedFactory
    interface Factory {
        fun create(id: String?): DetailVM
    }
}

data class DetailState(
    val contact: Contact? = null,
    val isNew: Boolean = false,
    val mode: DetailMode = DetailMode.EDIT,
)

enum class DetailMode() {
    EDIT,
    NEW,
}


