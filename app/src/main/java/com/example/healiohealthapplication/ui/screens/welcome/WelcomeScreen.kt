package com.example.healiohealthapplication.ui.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
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
fun WelcomeScreen(
  navController: NavController,
  modifier: Modifier
) {
    // A vertical layout for the entire screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        // 1) TOP SECTION
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            OverlappingCircle();

            // Healio logo
            // TODO: Need to fix the image
            Image(
                painter = painterResource(id = R.drawable.ic_dancing_woman_healio_text),
                contentDescription = "Dancing woman with Healio text",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 30.dp)
                    .fillMaxHeight(0.6f),
                contentScale = ContentScale.Fit
            )
        }

        // 2) MIDDLE SECTION
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            // Large circle behind phone & icons
            Box(
                modifier = Modifier
                    .fillMaxSize(0.8f)
                    .background(
                        color = Green142,
                        shape = CircleShape
                    )
            )

            // Phone + icons illustration
            // TODO: Need to fix the image
            Image(
                painter = painterResource(id = R.drawable.ic_phone_and_icons),
                contentDescription = "Phone with icons",
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                contentScale = ContentScale.Fit
            )
        }

        // 3) BOTTOM SECTION
        // "Welcome to our app" text bubble
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(80.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        color = Color(0xFFEFEFEF),
                        shape = RoundedCornerShape(20.dp)
                    )
            )
            Text(
                text = "Welcome to our app",
                style = MaterialTheme.typography.headlineSmall.copy(color = Color.Black)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bottom illustration
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_feeling_happy_bhy8),
                contentDescription = "Woman with balloon",
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}
