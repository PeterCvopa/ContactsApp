package com.cvopa.peter.play.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
import com.cvopa.peter.play.model.Favorite
import com.cvopa.peter.play.ui.viewmodel.FavoriteState
import com.cvopa.peter.play.ui.viewmodel.FavoriteVM

@Composable
fun FavoriteScreen(onClick: (id: Int) -> Unit = {}) {
    val viewModel: FavoriteVM = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    FavoriteScreen(state = state, onClick = onClick)
}

@Composable
fun FavoriteScreen(state: FavoriteState, onClick: (id: Int) -> Unit = {}) {
    Scaffold(
        topBar = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.favorite_title),
                style = TextStyle(fontSize = 22.sp)
            )
        },
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            LazyColumn {
                items(state.favorites, { item -> item.id }) {
                    FavoriteItem(it, onClick)
                }
            }
        }
    }
}

@Composable
fun FavoriteItem(fav: Favorite, onClick: (id: Int) -> Unit) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick(fav.id) }
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
                        text = fav.firstName
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
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp),
                        text = fav.lastName
                    )
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
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp),
                        text = fav.phone
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview
@Composable
private fun FavoriteScreenPreview() {
    FavoriteScreen(
        state = FavoriteState(
            listOf(
                Favorite(1, "Test", "Test", "f43434343"),
                Favorite(2, "Test", "T1111", "f43434343")
            )
        )
    )
}
