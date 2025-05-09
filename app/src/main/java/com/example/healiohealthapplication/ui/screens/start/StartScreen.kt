package com.example.healiohealthapplication.ui.screens.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healiohealthapplication.R
import com.example.healiohealthapplication.navigation.Routes
import com.example.healiohealthapplication.ui.components.BigButton
import com.example.healiohealthapplication.ui.components.OverlappingCircle
import com.example.healiohealthapplication.ui.theme.Green142

@Composable
fun StartScreen(
    navController: NavController,
    modifier: Modifier
) {
    Box(modifier = Modifier.fillMaxSize()) {

        // Main content column
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.fitness_tracker_y5q5),
                    contentDescription = stringResource(id = R.string.fitness_illustration_desc),
                    modifier = Modifier.size(200.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(48.dp))
            }

            item {
                Text(
                    text = stringResource(id = R.string.stay_fit_stay_healthy),
                    style = MaterialTheme.typography.headlineMedium,
                )

                Spacer(modifier = Modifier.height(48.dp))
            }

            item {
                BigButton(
                    text = stringResource(id = R.string.get_started),
                    onClick = { navController.navigate(Routes.LOGIN) }
                    // For testing the Diet screen
                    //onClick = { navController.navigate(Routes.DIET) }
                    // For testing the Welcome screen
                    //onClick = { navController.navigate(Routes.WELCOME) }
                )
            }
        }

        OverlappingCircle();
    }
}