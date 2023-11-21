package com.cvopa.peter.play.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.cvopa.peter.play.db.ContactsRepository
import com.cvopa.peter.play.model.Favorite
import com.cvopa.peter.play.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class FavoriteVM @Inject constructor(repository: ContactsRepository) : BaseViewModel<FavoriteState>() {

    init {
        viewModelScope.launch {
            repository.getAllFavorites().collect {
                emitState(state.value.copy(favorites = it))
            }
        }
    }

    override val initialState: FavoriteState
        get() = FavoriteState()
}

data class FavoriteState(val favorites: List<Favorite> = emptyList())

