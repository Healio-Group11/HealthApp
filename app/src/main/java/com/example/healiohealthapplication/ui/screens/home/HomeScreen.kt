package com.example.healiohealthapplication.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.healiohealthapplication.R
import com.example.healiohealthapplication.ui.components.BottomNavBar
import com.example.healiohealthapplication.ui.components.HomeScreenTopNavBar
import com.example.healiohealthapplication.ui.theme.Green142

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier, viewModel: HomeScreenViewModel) {
    Scaffold(
        topBar = { HomeScreenTopNavBar("Home", navController, viewModel.expanded, { viewModel.toggleExpanded() }) },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        Text(
            text = "Home Screen!",
            modifier = Modifier.padding(innerPadding)
        )
        GetStartedScreen();
    }
}

@Composable
fun GetStartedScreen(
    onGetStartedClick: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {

        // Main content column
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.fitness_tracker_y5q5),
                contentDescription = stringResource(id = R.string.fitness_illustration_desc),
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(id = R.string.stay_fit_stay_healthy),
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = onGetStartedClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Green142
                ),
                elevation = ButtonDefaults.buttonElevation(0.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.get_started),
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Black
                )
            }
        }

        // Rounded decorative element on top (drawn later so it's visible)
        Box(
            modifier = Modifier
                .size(240.dp)
                .offset(x = -64.dp, y = 0.dp)
                .background(
                    color = Green142,
                    shape = CircleShape
                )
                // Ensure this element is drawn above the column
                .zIndex(1f)
        )
    }
}

