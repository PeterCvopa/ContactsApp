package com.cvopa.peter.play.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cvopa.peter.play.ui.screen.ContactsScreen
import com.cvopa.peter.play.ui.screen.DetailScreen
import com.cvopa.peter.play.ui.screen.FavoriteScreen
import com.cvopa.peter.play.ui.theme.ContactTheme

@Composable
fun ContactsApp() {
    ContactTheme {
        MainScreen()
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { MyBottomBar(navController) },
        floatingActionButton = {
            addContact(navController = navController) {
                navController.navigate("detail/${-1}")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
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
                FavoriteScreen {
                    navController.navigate("detail/${it}")
                }
            }
            composable(
                route = Screens.Detail.route + "/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType }
                )) { backStackEntry ->
                DetailScreen(
                    id = backStackEntry.arguments?.getInt("id") ?: -1
                ) {
                    navController.popBackStack()
                }
            }
        }

    }
}

@Composable
fun addContact(navController: NavHostController, onClick: () -> Unit) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isAddButtonVisible = currentDestination?.route == Screens.Contacts.route
    if (isAddButtonVisible) {
        FloatingActionButton(modifier = Modifier.padding(bottom = 10.dp), onClick = { onClick() }) {
            Icon(Icons.Filled.Add, "floating button")
        }
    }
}

@Composable
fun MyBottomBar(navController: NavHostController) {
    var state by remember { mutableStateOf(MainScreenState()) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isBottomBarVisible = currentDestination?.route != Screens.Detail.route + "/{id}"
    if (isBottomBarVisible) {
        NavigationBar {
            state.navigationBarItems.forEach { bottomNavItem ->
                NavigationBarItem(
                    selected = bottomNavItem.isSelected,
                    onClick = {
                        val newSelected = state.navigationBarItems.map { one ->
                            if (bottomNavItem.route == one.route) {
                                one.apply { isSelected = true }
                            } else {
                                one.apply { isSelected = false }
                            }
                        }
                        state = state.copy(navigationBarItems = newSelected)
                        navController.navigate(bottomNavItem.route)
                    },
                    icon = {
                        Icon(imageVector = bottomNavItem.getIcon(), contentDescription = "null")
                    },
                    label = {
                        Text(text = bottomNavItem.title)
                    },
                    alwaysShowLabel = true,
                )
            }
        }
    }
}

sealed class Screens(val route: String) {
    data object Detail : Screens("detail")
    data object Favorite : Screens("home")
    data object Contacts : Screens("contacts")
}

data class MainScreenState(var navigationBarItems: List<BottomNavItem> = listOf(BottomNavItem.Contacts, BottomNavItem.Favorites))

sealed class BottomNavItem(
    val title: String,
    val iconSelected: ImageVector,
    val iconNotSelected: ImageVector,
    var isSelected: Boolean,
    val route: String,
) {

    data object Contacts : BottomNavItem(
        title = "Contacts",
        iconSelected = Icons.Filled.Face,
        iconNotSelected = Icons.Outlined.Face,
        isSelected = true,
        route = Screens.Contacts.route
    )

    data object Favorites : BottomNavItem(
        title = "Favorites",
        iconSelected = Icons.Filled.Favorite,
        iconNotSelected = Icons.Filled.FavoriteBorder,
        isSelected = false,
        route = Screens.Favorite.route
    )

    fun getIcon(): ImageVector {
        return if (isSelected) {
            iconSelected
        } else {
            iconNotSelected
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}