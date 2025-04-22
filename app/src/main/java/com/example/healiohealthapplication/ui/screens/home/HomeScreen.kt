package com.example.healiohealthapplication.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healiohealthapplication.R
import com.example.healiohealthapplication.navigation.Routes
import com.example.healiohealthapplication.ui.components.BottomNavBar
import com.example.healiohealthapplication.ui.components.CircleButton
import com.example.healiohealthapplication.ui.components.OverlappingCircle
import com.example.healiohealthapplication.ui.screens.shared.SharedViewModel
import com.example.healiohealthapplication.ui.theme.Green142

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier, viewModel: HomeScreenViewModel, sharedViewModel: SharedViewModel) {
    Scaffold(
        topBar = { HomeScreenTopNavBar("Home", navController, viewModel.expanded, { viewModel.toggleExpanded() }, viewModel = viewModel) },
        bottomBar = { BottomNavBar(navController) }

    ) { innerPadding ->
        // CollectAsState automatically observes changes.
        // Apparently this does not break MVVM architecture! TODO: check whether this is true and that we can use this in UI
        val userData by sharedViewModel.userData.collectAsState()
        // val steps by viewModel.stepCount.collectAsState()

        LaunchedEffect(userData?.id) {
           //  userData?.id?.let { viewModel.loadSteps(it) }
        }

        /*Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        ) {
            Text(
                text = "Currently logged in user's email: ${userData?.email ?: "Not logged in"}!"
            )
        }*/

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            OverlappingCircle()
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item {
                Spacer(modifier = Modifier.height(120.dp))

                // Title: "Today's step"
                Text(
                    text = "Today's step",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = Color.Black
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                // Row: dog-walking illustration + circle with "100"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Placeholder image: person walking dog
                    Image(
                        painter = painterResource(id = R.drawable.ic_walking_with_dog), // replace with your resource
                        contentDescription = "Walking dog illustration",
                        modifier = Modifier
                            .size(120.dp),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.width(36.dp))

                    // Pink circle for "100"
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .border(
                                width = 2.dp,
                                color = Green142,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "0",// steps?.dailyStepsTaken.toString(),
                            style = MaterialTheme.typography.headlineMedium.copy(color = Color.Black)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(36.dp))
            }

            item {
                // Title: "Today's activities"
                Text(
                    text = "Today's activities",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = Color.Black
                    )
                )

                Spacer(modifier = Modifier.height(36.dp))
            }

            item {
                // Row (or FlowRow) for the three activity circles
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 1) Exercise circle
                    CircleButton("Workout",
                        onClick = {navController.navigate(Routes.WORKOUT)}
                    )

                    // 2) Calorie intake circle
                    CircleButton("Diet",
                        onClick = { navController.navigate(Routes.DIET) }
                    )
                }
                // Row (or FlowRow) for the three activity circles
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 3) Water intake circle
                    CircleButton("Medicine",
                        onClick = {navController.navigate(Routes.MEDICINE)},
                        modifier = Modifier
                    )

                }
            }
        }
    }
}


