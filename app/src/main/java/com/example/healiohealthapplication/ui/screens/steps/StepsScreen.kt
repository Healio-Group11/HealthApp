package com.example.healiohealthapplication.ui.screens.steps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healiohealthapplication.ui.components.BigButton
import com.example.healiohealthapplication.ui.components.BottomNavBar
import com.example.healiohealthapplication.ui.components.TopNavBar
import com.example.healiohealthapplication.ui.screens.shared.SharedViewModel

@Composable
fun StepsScreen(navController: NavController, modifier: Modifier, viewModel: StepsViewModel, sharedViewModel: SharedViewModel) {
    Scaffold(
        topBar = { TopNavBar("Steps", navController) },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        val userData by sharedViewModel.userData.collectAsState()
        val stepsData by viewModel.stepsData.collectAsState()
        val newSteps by viewModel.stepCounter.stepCount.collectAsState()
        val progress by viewModel.progress.collectAsState()

        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        ) {
            CircularProgressIndicator(
                progress = progress,
                modifier = Modifier.size(120.dp),
                strokeWidth = 8.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "${stepsData?.dailyStepsTaken ?: 0} / ${stepsData?.dailyStepGoal ?: 0} steps")
            Spacer(modifier = Modifier.height(40.dp))
            BigButton(
                text = "Reset or create steps",
                onClick = { viewModel.initializeStepsData(userData?.id ?: "") }
            )
            BigButton(
                text = "Update the steps taken part",
                onClick = { viewModel.updateStepsTakenData(userData?.id ?: "", newSteps) }
            )
            BigButton(
                text = "Get current steps from firestore",
                onClick = { viewModel.getCurrentStepData(userData?.id ?: "") }
            )
            StepsOutlinedTextField(
                value = viewModel.currentStepGoal,
                onValueChange = { viewModel.currentStepGoal = it },
                label = "Step Goal",
                keyboardType = KeyboardType.Number,
            )
            BigButton(
                text = "Update the step goal",
                onClick = { viewModel.updateDailyStepGoal(userData?.id ?: "", viewModel.currentStepGoal ?: 0) }
            )
        }
    }
}