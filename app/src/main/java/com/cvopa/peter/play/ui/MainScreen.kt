package com.cvopa.peter.play.ui

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cvopa.peter.billduAsignment.R
import com.cvopa.peter.play.Contact
import com.cvopa.peter.play.ui.detail.ContactsScreen
import com.cvopa.peter.play.ui.detail.DetailScreen
import com.cvopa.peter.play.ui.detail.FavoriteScreen

@Composable
fun ContactsApp() {
    MainScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { MyBottomBar(navController) }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screens.Contacts.route
        ) {
            composable(Screens.Contacts.route) {
                ContactsScreen {
                    navController.navigate("detail/${it}")
                }
            }

            composable(Screens.Favorite.route) {
                FavoriteScreen()
            }

            composable(Screens.Detail.route + "/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType }
                )) { backStackEntry ->
                DetailScreen(backStackEntry.arguments?.getString("id")) {
                    navController.navigate(BottomNavItem.Contacts.route)
                }
            }
        }
    }
}

@Composable
fun MyBottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isBottomBarVisible = currentDestination?.route != Screens.Detail.route
    if (isBottomBarVisible) {
        NavigationBar {
            navigationBarItems.forEach {
                NavigationBarItem(selected = it.isSelected,
                    onClick = {
                        navController.navigate(it.route)
                    },
                    icon = { it.iconSelected }
                )
            }
        }
    }

}

@Composable
fun ContactItem(item: Contact) {
    Text(text = item.name)
}

sealed class Screens(val route: String) {
    data object Detail : Screens("detail")
    data object Favorite : Screens("home")
    data object Contacts : Screens("contacts")
}

val navigationBarItems = listOf(BottomNavItem.Contacts, BottomNavItem.Favorites)

sealed class BottomNavItem(
    val title: String, val iconSelected: Int,
    val iconNotSelected: Int,
    var isSelected: Boolean,
    val route: String,
) {

    data object Contacts : BottomNavItem(
        title = "Contacts",
        iconSelected = R.drawable.ic_contatcs,
        iconNotSelected = R.drawable.ic_contatcs,
        isSelected = true,
        route = Screens.Contacts.route
    )

    data object Favorites : BottomNavItem(
        title = "Favorites",
        iconSelected = R.drawable.ic_favorite,
        iconNotSelected = R.drawable.ic_favorite,
        isSelected = false,
        route = Screens.Favorite.route
    )
}

@Preview
@Composable
fun MainScreenPreview() {

}