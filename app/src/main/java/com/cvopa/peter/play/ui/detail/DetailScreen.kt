package com.cvopa.peter.play.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cvopa.peter.play.di.composeViewModel
import com.cvopa.peter.play.localDetailVMFactory
import com.cvopa.peter.play.ui.viewmodel.DetailVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(id: String? = "Detail", oncClick: () -> Unit) {
    val factory = localDetailVMFactory.current
    val viewModel: DetailVM = composeViewModel {
        factory.create(id)
    }
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold() {
        Column(modifier = Modifier.padding(it)) {
            id?.let { it1 -> Text(it1) }
            Button(onClick = {
                oncClick()
            }) {
                Text("Btn")
            }
        }
    }
}

