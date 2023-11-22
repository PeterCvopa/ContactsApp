package com.cvopa.peter.play.ui.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cvopa.peter.billduAsignment.R
import com.cvopa.peter.play.model.Contact
import com.cvopa.peter.play.ui.viewmodel.ContactsVM
import com.cvopa.peter.play.ui.viewmodel.State

@Composable
fun ContactsScreen(onItemClicked: (id: Int) -> Unit = {}) {
    val viewModel: ContactsVM = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    ContactsScreen(
        state = state,
        onItemClicked = onItemClicked,
        onFavorite = viewModel::onSetFavorite,
        onDelete = viewModel::onDelete,
        onSearch = viewModel::onSearch,
        onResetSearch = viewModel::onResetSearch,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContactsScreen(
    state: State,
    onSearch: (value: String) -> Unit = {},
    onItemClicked: (id: Int) -> Unit = {},
    onFavorite: (id: Int, value: Boolean) -> Unit = { _, _ -> },
    onDelete: (id: Int) -> Unit = {},
    onResetSearch: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.contacts_title),
                style = TextStyle(fontSize = 22.sp)
            )
        },
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                value = state.searchValue,
                onValueChange = { value ->
                    onSearch(value)
                },
                label = { Text(stringResource(id = R.string.search)) },
                trailingIcon = {
                    if (state.searchValue.isNotEmpty()) {
                        Box(modifier = Modifier.clickable { onResetSearch() }) {
                            Icon(imageVector = Icons.Filled.Clear, contentDescription = null)
                        }
                    }
                }
            )

            LazyColumn {
                items(state.contactItems, { item -> item.id }) { item ->
                    val dismissState = rememberDismissState()
                    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                        onDelete(item.id)
                    }
                    SwipeToDismiss(
                        state = dismissState,
                        directions = setOf(DismissDirection.EndToStart),
                        background = {

                            val color by animateColorAsState(
                                when (dismissState.targetValue) {
                                    DismissValue.Default -> MaterialTheme.colorScheme.background
                                    DismissValue.DismissedToStart -> Color.Red
                                    DismissValue.DismissedToEnd -> MaterialTheme.colorScheme.background
                                }, label = ""
                            )

                            val scale by animateFloatAsState(
                                if (dismissState.targetValue == DismissValue.Default) 0.55f else 1.3f, label = ""
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = color)
                            ) {
                                Icon(
                                    Icons.Outlined.Delete,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .padding(8.dp)
                                        .scale(scale)
                                )
                            }
                        },

                        dismissContent = {
                            ContactItem(
                                contact = item,
                                onClick = onItemClicked,
                                onFavorite = onFavorite
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ContactItem(
    contact: Contact, onClick: (id: Int) -> Unit,
    onFavorite: (id: Int, value: Boolean) -> Unit
) {

    Surface(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick(contact.id) }
        .padding(start = 12.dp, top = 4.dp, bottom = 4.dp)
    ) {
        Row {
            Column {
                Row {
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp),
                        fontWeight = FontWeight.Bold,
                        text = stringResource(id = R.string.first_name),

                        )
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp),
                        text = contact.firstName
                    )
                }
                Row {
                    Text(
                        modifier = Modifier.padding(
                            vertical = 4.dp,
                            horizontal = 4.dp
                        ),
                        fontWeight = FontWeight.Bold,
                        text = stringResource(id = R.string.last_name)
                    )
                    Text(modifier = Modifier.padding(vertical = 4.dp), text = contact.lastName)
                }
                Row {
                    Text(
                        modifier = Modifier.padding(
                            vertical = 4.dp,
                            horizontal = 4.dp
                        ),
                        fontWeight = FontWeight.Bold,
                        text = stringResource(id = R.string.phone_number)
                    )
                    Text(modifier = Modifier.padding(vertical = 4.dp), text = contact.phone)
                }
            }
            Spacer(modifier = Modifier.weight(1f))

            val icon = if (contact.isFavorite) {
                Icons.Filled.Favorite
            } else {
                Icons.Outlined.FavoriteBorder
            }
            Box(
                modifier = Modifier
                    .clickable { onFavorite(contact.id, contact.isFavorite.not()) }
                    .align(CenterVertically)
            ) {
                Icon(
                    modifier = Modifier
                        .padding(16.dp),
                    imageVector = icon,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
private fun ContactsScreenPreview() {
    ContactsScreen(
        state = State(
            listOf(
                Contact(1, "Test", "Test", "f43434343", true),
                Contact(2, "Test", "T1111", "f43434343", false)
            )
        )
    )
}
