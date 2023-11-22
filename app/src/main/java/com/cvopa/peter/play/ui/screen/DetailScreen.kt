package com.cvopa.peter.play.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cvopa.peter.billduAsignment.R
import com.cvopa.peter.play.di.composeViewModel
import com.cvopa.peter.play.localDetailVMFactory
import com.cvopa.peter.play.model.Contact
import com.cvopa.peter.play.ui.viewmodel.DetailState
import com.cvopa.peter.play.ui.viewmodel.DetailVM

@Composable
fun DetailScreen(id: Int, navigateBack: () -> Unit) {
    val factory = localDetailVMFactory.current
    val viewModel: DetailVM = composeViewModel {
        factory.create(id = id)
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    DetailScreen(
        state = state,
        onButtonClick = {
            navigateBack()
            viewModel.onSave()
        },
        onFirstNameChange = viewModel::onFirstName,
        onLastNameChange = viewModel::onLastName,
        onPhoneNameChange = viewModel::onPhoneNumber,
    )
}

@Composable
private fun DetailScreen(
    state: DetailState,
    onButtonClick: () -> Unit = {},
    onFirstNameChange: (String) -> Unit = {},
    onLastNameChange: (String) -> Unit = {},
    onPhoneNameChange: (String) -> Unit = {},
) {

    Scaffold(
        topBar = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.detail_title),
                style = TextStyle(fontSize = 22.sp)
            )
        },
    ) {
        Column(
            modifier = Modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.contact.firstName,
                onValueChange = { newValue ->
                    onFirstNameChange(newValue)
                },
                label = { Text(stringResource(id = R.string.first_name)) }
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.contact.lastName,
                onValueChange = { newValue ->
                    onLastNameChange(newValue)
                },
                label = { Text(stringResource(id = R.string.last_name)) }
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.contact.phone,
                onValueChange = { newValue ->
                    onPhoneNameChange(newValue)
                },
                label = { Text(stringResource(id = R.string.phone_number)) }
            )

            Button(
                modifier = Modifier.padding(vertical = 8.dp),
                onClick = { onButtonClick() }
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                    text = stringResource(id = R.string.save).uppercase(),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Preview
@Composable
private fun preview() {
    DetailScreen(
        state = DetailState(
            contact = Contact(
                0,
                "f",
                "fd",
                "435543",
                false
            )
        )
    )
}