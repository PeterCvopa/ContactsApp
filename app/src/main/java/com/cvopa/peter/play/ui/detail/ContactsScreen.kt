package com.cvopa.peter.play.ui.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cvopa.peter.play.model.Contact
import com.cvopa.peter.play.ui.viewmodel.ContactsVM

@Composable
fun ContactsScreen(onClick: (id: Int) -> Unit = {}) {
    Contacts(onClick)
}

@Composable
private fun Contacts(onClick: (id: Int) -> Unit = {}) {

    val viewModel: ContactsVM = viewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column {
        LazyColumn {
            items(state.contactItems, { item -> item.id }) {
                ContactItem(it, onClick)
            }
        }
    }
}

@Composable
fun ContactItem(contact: Contact, onClick: (id: Int) -> Unit) {
    Surface(modifier = Modifier.clickable {
        onClick(contact.id)
    }) {
        Column {
            Text(text = contact.firstName)
            Text(text = contact.lastName)
            Text(text = contact.phone)
            if (contact.isFavorite) {
                Icon(Icons.Rounded.Favorite, "")
            }
        }
    }

}

@Preview
@Composable
private fun ContactsScreenPreview() {
    ContactsScreen()
}
