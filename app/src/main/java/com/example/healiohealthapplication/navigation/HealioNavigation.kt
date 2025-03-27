package com.example.healiohealthapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.healiohealthapplication.ui.screens.calendar.CalendarScreen
import com.example.healiohealthapplication.ui.screens.diet.DietScreen
import com.example.healiohealthapplication.ui.screens.home.HomeScreen
import com.example.healiohealthapplication.ui.screens.home.HomeScreenViewModel
import com.example.healiohealthapplication.ui.screens.login.LoginScreen
import com.example.healiohealthapplication.ui.screens.login.LoginViewModel
import com.example.healiohealthapplication.ui.screens.signup.SignUpScreen
import com.example.healiohealthapplication.ui.screens.start.StartScreen
import com.example.healiohealthapplication.ui.screens.steps.StepsScreen
import com.example.healiohealthapplication.ui.screens.user.UserScreen
import com.example.healiohealthapplication.ui.screens.medicine.MedicineScreen
import com.example.healiohealthapplication.ui.screens.shared.SharedViewModel
import com.example.healiohealthapplication.ui.screens.signup.SignUpViewModel
import com.example.healiohealthapplication.ui.screens.workout.WorkoutScreen

@Composable
fun HealioNavigation() {
    val navController = rememberNavController()
    // TODO: are we supposed to declare each viewmodel here separately? is this bad practice?
    val sharedViewModel: SharedViewModel = hiltViewModel()

    // TODO: change starting point. If user is logged in, start from homepage. If not, start from starting screen.
    // TODO: add error screen and loading screen functionality. if page is loading show loading screen, if page was loaded successfully show respective screen.
    NavHost(
        navController = navController,
        startDestination = Routes.HOME // change HOME to START!
    ) {
        composable(route = Routes.START) { StartScreen(navController, modifier = Modifier) }
        composable(route = Routes.HOME) {
            val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
            HomeScreen(navController, modifier = Modifier, homeScreenViewModel, sharedViewModel)
        }
        composable(route = Routes.LOGIN) {
            val loginScreenViewModel: LoginViewModel = hiltViewModel()
            LoginScreen(navController, modifier = Modifier, loginScreenViewModel, sharedViewModel)
        }
        composable(route = Routes.SIGNUP) {
            val signUpViewModel: SignUpViewModel = hiltViewModel()
            SignUpScreen(navController, modifier = Modifier, signUpViewModel, sharedViewModel)
        }
        composable(route = Routes.USER) { UserScreen(navController, modifier = Modifier) }
        composable(route = Routes.DIET) { DietScreen(navController, modifier = Modifier) }
        composable(route = Routes.STEPS) { StepsScreen(navController, modifier = Modifier) }
        composable(route = Routes.WORKOUT) { WorkoutScreen(navController, modifier = Modifier) }
        composable(route = Routes.MEDICINE) { MedicineScreen(navController, modifier = Modifier) }
        composable(route = Routes.CALENDAR) { CalendarScreen(navController, modifier = Modifier) }
    }
}