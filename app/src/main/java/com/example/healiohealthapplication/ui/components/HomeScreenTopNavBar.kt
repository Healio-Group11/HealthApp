package com.example.healiohealthapplication.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.healiohealthapplication.R
import com.example.healiohealthapplication.navigation.Routes

// TODO: find out if the experimental parts should be switched to something else
// TODO: change colours and make this prettier
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopNavBar(title: String, navController: NavController, expanded: Boolean, toggleExpanded: (Boolean) -> Unit)  {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.secondary
        ),
        title = { Text(text = title) },
        actions = {
            TopNavBarIconButton(
                contentDescription = R.string.home_screen_top_nav_bar_icon_description,
                icon = Icons.Filled.Menu,
                onClick = { toggleExpanded(!expanded) }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { toggleExpanded(false) }
            ) {
                ItemForDropdownMenu(
                    textResId = R.string.home_screen_top_nav_bar_dropdown_text_for_workout,
                    onClick = { navController.navigate(Routes.WORKOUT) }
                )
                ItemForDropdownMenu(
                    textResId = R.string.home_screen_top_nav_bar_dropdown_text_for_medicine,
                    onClick = { navController.navigate(Routes.MEDICINE) }
                )
                ItemForDropdownMenu(
                    textResId = R.string.home_screen_top_nav_bar_dropdown_text_for_steps,
                    onClick = { navController.navigate(Routes.STEPS) }
                )
            }
        }
)
}