package com.example.healiohealthapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import android.net.Uri
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.collectAsState
import com.example.healiohealthapplication.ui.screens.calendar.CalendarScreen
import com.example.healiohealthapplication.ui.screens.diet.DietScreen
import com.example.healiohealthapplication.ui.screens.home.HomeScreen
import com.example.healiohealthapplication.ui.screens.home.HomeScreenViewModel
import com.example.healiohealthapplication.ui.screens.login.LoginScreen
import com.example.healiohealthapplication.ui.screens.login.LoginViewModel
import com.example.healiohealthapplication.ui.screens.medicine.AddMedicineScreen
import com.example.healiohealthapplication.ui.screens.medicine.EditMedicineScreen
import com.example.healiohealthapplication.ui.screens.medicine.MedicineDetailScreen
import com.example.healiohealthapplication.ui.screens.medicine.MedicineListScreenContent
import com.example.healiohealthapplication.ui.screens.signup.SignUpScreen
import com.example.healiohealthapplication.ui.screens.start.StartScreen
import com.example.healiohealthapplication.ui.screens.steps.StepsScreen
import com.example.healiohealthapplication.ui.screens.user.UserScreen
import com.example.healiohealthapplication.ui.screens.medicine.MedicineScreen
import com.example.healiohealthapplication.ui.screens.medicine.MedicineViewModel
import com.example.healiohealthapplication.ui.screens.shared.SharedViewModel
import com.example.healiohealthapplication.ui.screens.signup.SignUpViewModel
import com.example.healiohealthapplication.ui.screens.steps.StepsViewModel
import com.example.healiohealthapplication.ui.screens.user.EditUserScreen
import com.example.healiohealthapplication.ui.screens.user.UserViewModel
import com.example.healiohealthapplication.ui.screens.welcome.WelcomeScreen
import com.example.healiohealthapplication.ui.screens.workout.AddWorkoutScreen
import com.example.healiohealthapplication.ui.screens.workout.EditWorkoutScreen
import com.example.healiohealthapplication.ui.screens.workout.ProgressScreen
import com.example.healiohealthapplication.ui.screens.workout.WorkoutScreen
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun HealioNavigation() {
    val navController = rememberNavController()
    val sharedViewModel: SharedViewModel = hiltViewModel()

    // TODO: add error screen and loading screen functionality. if page is loading show loading screen, if page was loaded successfully show respective screen
    NavHost(
        navController = navController,
        startDestination = Routes.START
    ) {
        composable(route = Routes.START) { StartScreen(navController, modifier = Modifier) }
        composable(route = Routes.HOME) {
            val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
            HomeScreen(navController, modifier = Modifier, homeScreenViewModel, sharedViewModel)
        }
        composable(route = Routes.LOGIN) {
            val loginScreenViewModel: LoginViewModel = hiltViewModel()
            LoginScreen(navController, loginScreenViewModel, sharedViewModel)
        }
        composable(route = Routes.SIGNUP) {
            val signUpViewModel: SignUpViewModel = hiltViewModel()
            SignUpScreen(navController, signUpViewModel, sharedViewModel)
        }
        composable(route = Routes.USER) {
            val userViewModel: UserViewModel = hiltViewModel()
            UserScreen(navController, userViewModel, sharedViewModel)
        }
        composable(route = Routes.EDIT_USER) {
            val userViewModel: UserViewModel = hiltViewModel()
            EditUserScreen(navController, userViewModel, sharedViewModel)
        }
        composable(route = Routes.DIET) { DietScreen(navController, modifier = Modifier) }
        composable(route = Routes.STEPS) {
            val stepsViewModel: StepsViewModel = hiltViewModel()
            StepsScreen(navController, modifier = Modifier, stepsViewModel, sharedViewModel)
        }
        // composable(route = Routes.WORKOUT) { WorkoutScreen(navController) }
        // composable(route = Routes.MEDICINE) { MedicineScreen(navController, modifier = Modifier) }
        composable(route = Routes.CALENDAR) { CalendarScreen(navController, modifier = Modifier) }
        composable(route = Routes.WELCOME) { WelcomeScreen(navController, modifier = Modifier) }

        // Workouts Screen
        composable(Routes.WORKOUT) {
            WorkoutScreen(navController = navController, sharedViewModel)
        }


        // Editing a workout
        composable(
            route = "edit_workout/{userId}/{workoutId}/{workoutName}/{workoutDuration}",
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType },
                navArgument("workoutId") { type = NavType.StringType },
                navArgument("workoutName") { type = NavType.StringType },
                navArgument("workoutDuration") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            val workoutId = backStackEntry.arguments?.getString("workoutId")  // <-- Added workoutId
            val workoutName = backStackEntry.arguments?.getString("workoutName")?.let { Uri.decode(it) }
            val workoutDuration = backStackEntry.arguments?.getString("workoutDuration")?.let { Uri.decode(it) }

            if (userId != null && workoutId != null && workoutName != null && workoutDuration != null) {
                EditWorkoutScreen(
                    userId = userId,
                    workoutId = workoutId,  // <-- Pass workoutId instead of using workoutName
                    workoutName = workoutName,
                    workoutDuration = workoutDuration,
                    navController = navController
                )
            } else {
                // Handle missing arguments (e.g., show an error screen or navigate back)
            }
        }



        // Viewing progress
        composable(route = Routes.VIEW_PROGRESS) {
            ProgressScreen(navController, workouts = listOf()) // Pass empty list or real data
        }

        // Adding new workout
        composable(
            route = "add_workout/{uid}",
            arguments = listOf(navArgument("uid") { type = NavType.StringType })
        ) { backStackEntry ->
            val uid = backStackEntry.arguments?.getString("uid")
            if (uid != null) {
                AddWorkoutScreen(navController = navController, userId = uid)
            } else {
                // Handle error case if userId is null
            }
        }





        // Medicine Main Screen
        composable(Routes.MEDICINE) {
            MedicineScreen(navController)
        }

        // Medicine List Screen
        composable(Routes.MEDICINE_LIST) {
            MedicineListScreenContent(navController)
        }

        // Medicine Detail Screen with Arguments (including medicineId)
        composable(
            route = "medicine_detail/{medicineId}/{medicineName}/{description}/{schedule}/{amount}/{duration}",
            arguments = listOf(
                navArgument("medicineId") { type = NavType.StringType },  // Add medicineId as required
                navArgument("medicineName") { type = NavType.StringType },
                navArgument("description") { type = NavType.StringType },
                navArgument("schedule") { type = NavType.StringType },
                navArgument("amount") { type = NavType.StringType },
                navArgument("duration") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val medicineId = backStackEntry.arguments?.getString("medicineId") ?: ""
            val name = backStackEntry.getDecodedArgument("medicineName")
            val description = backStackEntry.getDecodedArgument("description")
            val schedule = backStackEntry.getDecodedArgument("schedule")
            val amount = backStackEntry.getDecodedArgument("amount")
            val duration = backStackEntry.getDecodedArgument("duration")

            MedicineDetailScreen(
                medicineId = medicineId,
                medicineName = name,
                description = description,
                schedule = schedule,
                amount = amount,
                duration = duration,
                navController = navController,
                sharedViewModel = sharedViewModel
            )
        }




        // Adding medicine page
        composable(Routes.ADD_MEDICINE) {
            AddMedicineScreen(navController)
        }

        //Edit medicine
        composable(
            "edit_medicine/{userId}/{medicineId}",
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType },
                navArgument("medicineId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            val medicineId = backStackEntry.arguments?.getString("medicineId") ?: ""
            val medicineViewModel: MedicineViewModel = hiltViewModel()
            medicineViewModel.getMedicineById(userId, medicineId)

            val medicine = medicineViewModel.medicine.collectAsState().value

            if (medicine != null) {
                EditMedicineScreen(
                    medicineId = medicineId,
                    navController = navController,
                    sharedViewModel = sharedViewModel
                )
            } else {
                // Handle error state or loading state
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }




    }
}

// Helper Function to Decode Arguments
private fun androidx.navigation.NavBackStackEntry.getDecodedArgument(argName: String): String {
    return URLDecoder.decode(arguments?.getString(argName) ?: "Unknown", StandardCharsets.UTF_8.toString())
}

