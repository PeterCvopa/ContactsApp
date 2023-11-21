package com.cvopa.peter.play

import androidx.lifecycle.ViewModel
import com.cvopa.peter.play.ui.BottomNavItem
import com.cvopa.peter.play.ui.navigationBarItems
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val state = State()
    private fun onNavigationItemClicked(index: Int) {
        val f = state.bottomBarState.mapIndexed { i, bottomNavItem -> bottomNavItem.isSelected = i == index }
    }
}

data class State(
    val mainScreenState: MainScreenState = MainScreenState(),
    val bottomBarState: List<BottomNavItem> = navigationBarItems,
)

data class MainScreenState(val contactItems: List<Contact> = listOf(Contact("my name ")))
data class Contact(val name: String)
