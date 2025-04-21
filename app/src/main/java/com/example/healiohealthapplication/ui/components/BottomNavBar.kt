package com.example.healiohealthapplication.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.healiohealthapplication.R
import com.example.healiohealthapplication.data.models.BottomNavTabItem
import com.example.healiohealthapplication.navigation.Routes
import com.example.healiohealthapplication.ui.theme.NavBarTextWhite

@Composable
// displays the bottom bar used in navigation
fun BottomNavBar(navController: NavController) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val tabs = listOf(
        BottomNavTabItem(stringResource(R.string.bottom_nav_bar_first_tab_text), Icons.Filled.Home, route = Routes.HOME),
        BottomNavTabItem(stringResource(R.string.bottom_nav_bar_second_tab_text), Icons.Filled.Favorite, route = Routes.MEDICINE),
        BottomNavTabItem(stringResource(R.string.bottom_nav_bar_third_tab_text), Icons.Filled.Face, route = Routes.USER)
    )
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        tabs.forEach { tab ->
            val selected = tab.route === backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = { navController.navigate(tab.route) },
                label = {
                    Text(
                        tab.label,
                        style = MaterialTheme.typography.bodyLarge,
                        color = NavBarTextWhite
                    )
                },
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = null,
                        tint = if (selected) MaterialTheme.colorScheme.tertiary else NavBarTextWhite.copy(alpha = 0.8f)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.secondary,
                    selectedIconColor = MaterialTheme.colorScheme.tertiary,
                    unselectedIconColor = NavBarTextWhite.copy(alpha = 0.7f),
                    selectedTextColor = MaterialTheme.colorScheme.tertiary,
                    unselectedTextColor = NavBarTextWhite.copy(alpha = 0.7f),
                )
            )
        }
    }
}