package com.example.healiohealthapplication.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.healiohealthapplication.data.models.BottomNavTabItem
import com.example.healiohealthapplication.navigation.Routes

@Composable
// TODO: change icon for medicine at least
// displays the bottom bar used in navigation
fun BottomNavBar(navController: NavController) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val tabs = listOf(
        BottomNavTabItem("Home", Icons.Filled.Home, route = Routes.HOME),
        BottomNavTabItem("Calendar", Icons.Filled.DateRange, route = Routes.CALENDAR),
        BottomNavTabItem("Medicine", Icons.Filled.Face, route = Routes.MEDICINE)
    )
    NavigationBar(
        // containerColor = MaterialTheme.colorScheme.secondary
    ) {
        tabs.forEach { tab ->
            val selected = tab.route === backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = { navController.navigate(tab.route) },
                label = {
                    Text(
                        tab.label,
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = null
                    )
                },
                /* colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.tertiary,
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                ) */
            )
        }
    }
}