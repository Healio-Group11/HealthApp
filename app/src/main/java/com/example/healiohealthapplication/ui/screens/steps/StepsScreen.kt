package com.example.healiohealthapplication.ui.screens.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healiohealthapplication.ui.components.BigButton
import com.example.healiohealthapplication.ui.components.BottomNavBar
import com.example.healiohealthapplication.ui.components.ErrorCard
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
        val progress by viewModel.progress.collectAsState()
        val isSupported by viewModel.isStepTrackingSupported.collectAsState()

        LaunchedEffect(userData?.id) {
            userData?.id?.let { id ->
                viewModel.userId = id
                viewModel.getCurrentStepData(id)
            }
        }

        LazyColumn(
            modifier = Modifier.padding(innerPadding).fillMaxSize().fillMaxSize().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!isSupported && viewModel.showError) {
                item {
                    ErrorCard(
                        errorMessage = "Step tracking isn't supported on this device.",
                        onDismiss = { viewModel.showError = false }
                    )
                }
            }
            item {
                Text(
                    text = "Today's steps",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            item { Spacer(modifier = Modifier.height(34.dp)) }
            item {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.size(200.dp)) {
                    CircularProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.fillMaxSize(),
                        strokeWidth = 10.dp
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${stepsData?.dailyStepsTaken ?: 0}",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "${stepsData?.dailyStepGoal ?: 0}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(60.dp)) }
            item {
                StepsOutlinedTextField(
                    value = viewModel.currentStepGoal,
                    onValueChange = { viewModel.currentStepGoal = it },
                    label = "Step Goal",
                    keyboardType = KeyboardType.Number,
                )
            }
            item { Spacer(modifier = Modifier.height(12.dp)) }
            item {
                BigButton(
                    text = "Update Goal",
                    onClick = { viewModel.updateDailyStepGoal(userData?.id ?: "", viewModel.currentStepGoal ?: 0) }
                )
            }
            item { Spacer(modifier = Modifier.height(34.dp)) }
        }
    }
}