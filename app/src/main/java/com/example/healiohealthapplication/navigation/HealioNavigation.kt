package com.example.healiohealthapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.healiohealthapplication.ui.screens.calendar.CalendarScreen
import com.example.healiohealthapplication.ui.screens.diet.DietScreen
import com.example.healiohealthapplication.ui.screens.home.HomeScreen
import com.example.healiohealthapplication.ui.screens.home.HomeScreenViewModel
import com.example.healiohealthapplication.ui.screens.login.LoginScreen
import com.example.healiohealthapplication.ui.screens.login.LoginViewModel
import com.example.healiohealthapplication.ui.screens.medicine.AddMedicineScreen
import com.example.healiohealthapplication.ui.screens.medicine.MedicineDetailScreen
import com.example.healiohealthapplication.ui.screens.medicine.MedicineListScreenContent
import com.example.healiohealthapplication.ui.screens.signup.SignUpScreen
import com.example.healiohealthapplication.ui.screens.start.StartScreen
import com.example.healiohealthapplication.ui.screens.steps.StepsScreen
import com.example.healiohealthapplication.ui.screens.user.UserScreen
import com.example.healiohealthapplication.ui.screens.medicine.MedicineScreen
import com.example.healiohealthapplication.ui.screens.shared.SharedViewModel
import com.example.healiohealthapplication.ui.screens.signup.SignUpViewModel
import com.example.healiohealthapplication.ui.screens.steps.StepsViewModel
import com.example.healiohealthapplication.ui.screens.workout.WorkoutScreen
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun HealioNavigation() {
    val navController = rememberNavController()
    // TODO: are we supposed to declare each viewmodel here separately? is this bad practice?
    val sharedViewModel: SharedViewModel = hiltViewModel()

    // TODO: change starting point. If user is logged in, start from homepage. If not, start from starting screen.
    // TODO: add error screen and loading screen functionality. if page is loading show loading screen, if page was loaded successfully show respective screen
    NavHost(
        navController = navController,
        startDestination = Routes.START // change HOME to START!
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
        composable(route = Routes.STEPS) {
            val stepsViewModel: StepsViewModel = hiltViewModel()
            StepsScreen(navController, modifier = Modifier, stepsViewModel, sharedViewModel)
        }
        composable(route = Routes.WORKOUT) { WorkoutScreen(navController, modifier = Modifier) }
        composable(route = Routes.MEDICINE) { MedicineScreen(navController, modifier = Modifier) }
        composable(route = Routes.CALENDAR) { CalendarScreen(navController, modifier = Modifier) }


        // Medicine List Screen (Main Page)
        composable(route = Routes.MEDICINE_LIST) {
            MedicineListScreenContent(navController)
        }

        // Medicine Detail Screen (Navigates with arguments)
        composable(
            route = "medicine_detail/{medicineName}/{description}/{schedule}/{amount}/{duration}",
            arguments = listOf(
                navArgument("medicineName") { type = NavType.StringType },
                navArgument("description") { type = NavType.StringType },
                navArgument("schedule") { type = NavType.StringType },
                navArgument("amount") { type = NavType.StringType },
                navArgument("duration") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val name = URLDecoder.decode(
                backStackEntry.arguments?.getString("medicineName") ?: "Unknown",
                StandardCharsets.UTF_8.toString()
            )
            val description = URLDecoder.decode(
                backStackEntry.arguments?.getString("description") ?: "No description available",
                StandardCharsets.UTF_8.toString()
            )
            val schedule = URLDecoder.decode(
                backStackEntry.arguments?.getString("schedule") ?: "No schedule",
                StandardCharsets.UTF_8.toString()
            )
            val amount = URLDecoder.decode(
                backStackEntry.arguments?.getString("amount") ?: "No amount",
                StandardCharsets.UTF_8.toString()
            )
            val duration = URLDecoder.decode(
                backStackEntry.arguments?.getString("duration") ?: "No duration",
                StandardCharsets.UTF_8.toString()
            )

            MedicineDetailScreen(name, description, schedule, amount, duration, navController)
        }

        //adding medicine page
        composable(route = Routes.ADD_MEDICINE) {
            AddMedicineScreen(navController)
        }
    }
}