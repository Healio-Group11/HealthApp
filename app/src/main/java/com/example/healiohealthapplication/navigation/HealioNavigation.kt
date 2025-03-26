package com.example.healiohealthapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import com.example.healiohealthapplication.ui.screens.medicine.AddMedicineScreen
import com.example.healiohealthapplication.ui.screens.medicine.MedicineDetailScreen
import com.example.healiohealthapplication.ui.screens.medicine.MedicineListScreenContent
import com.example.healiohealthapplication.ui.screens.signup.SignUpScreen
import com.example.healiohealthapplication.ui.screens.start.StartScreen
import com.example.healiohealthapplication.ui.screens.steps.StepsScreen
import com.example.healiohealthapplication.ui.screens.user.UserScreen
import com.example.healiohealthapplication.ui.screens.medicine.MedicineScreen
import com.example.healiohealthapplication.ui.screens.workout.WorkoutScreen
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun HealioNavigation() {
    val navController = rememberNavController()
    // TODO: are we supposed to declare each viewmodel here separately? is this bad practice?
    val homeScreenViewModel: HomeScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

    // TODO: change starting point. If user is logged in, start from homepage. If not, start from starting screen.
    // TODO: add error screen and loading screen functionality. if page is loading show loading screen, if page was loaded successfully show respective screen
    NavHost(
        navController = navController,
        startDestination = Routes.HOME // change HOME to START!
    ) {
        composable(route = Routes.START) { StartScreen(navController, modifier = Modifier) }
        composable(route = Routes.HOME) { HomeScreen(navController, modifier = Modifier, homeScreenViewModel) }
        composable(route = Routes.LOGIN) { LoginScreen(navController, modifier = Modifier) }
        composable(route = Routes.SIGNUP) { SignUpScreen(navController, modifier = Modifier) }
        composable(route = Routes.USER) { UserScreen(navController, modifier = Modifier) }
        composable(route = Routes.DIET) { DietScreen(navController, modifier = Modifier) }
        composable(route = Routes.STEPS) { StepsScreen(navController, modifier = Modifier) }
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