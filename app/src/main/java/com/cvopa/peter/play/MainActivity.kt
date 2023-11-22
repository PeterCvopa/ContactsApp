package com.cvopa.peter.play

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.cvopa.peter.play.ui.ContactsApp
import com.cvopa.peter.play.ui.viewmodel.DetailVM
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var detailVMFactory: DetailVM.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(
                localDetailVMFactory provides detailVMFactory,
            ) {
                ContactsApp()
            }
        }
    }
}

val localDetailVMFactory = staticCompositionLocalOf<DetailVM.Factory> {
    error("No ViewModelFactory provided")
}