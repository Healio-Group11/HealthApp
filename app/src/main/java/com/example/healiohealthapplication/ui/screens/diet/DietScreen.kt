package com.example.healiohealthapplication.ui.screens.diet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healiohealthapplication.R
import com.example.healiohealthapplication.ui.components.OverlappingCircle
import com.example.healiohealthapplication.ui.theme.Green142

@Composable
fun DietScreen(
    navController: NavController,
    modifier: Modifier
) {
    Box(modifier = Modifier.fillMaxSize()) {

        // Overlapping circles at the top left
        OverlappingCircle()

        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(36.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left: User name
                Text(
                    text = "myfitnesspal",  //TODO: Get the actual user name
                    style = MaterialTheme.typography.headlineMedium.copy(color = Color.Black)
                )

                // Right: Premium label + Notification icon
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Premium \uD83D\uDE0A",
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    // Notification Icon button
                    IconButton(onClick = { /* TODO: Handle notification click */ }) {
                        /*Icon(
                            painter = painterResource(id = R.drawable.ic_notifications),
                            contentDescription = "Notifications",
                            tint = Color.Gray
                        )*/
                    }
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            // 2) "Today" Title
            Text(
                text = "Today",
                style = MaterialTheme.typography.titleLarge.copy(color = Color.Black)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 3) Macros in separate rows (one per row)
            MacroRow(label = "Net Carbs", value = "65", unit = "left")
            Spacer(modifier = Modifier.height(16.dp))
            MacroRow(label = "Fat", value = "34", unit = "left")
            Spacer(modifier = Modifier.height(16.dp))
            MacroRow(label = "Protein", value = "59", unit = "left")

            Spacer(modifier = Modifier.height(36.dp))

            // 4) Water Reminder Section
            WaterReminderSection()

            Spacer(modifier = Modifier.height(36.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_person_drinking_water),
                contentDescription = "Person drinking water",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun MacroRow(label: String, value: String, unit: String) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFF7F7F7),
        tonalElevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .border(
                width = 2.dp,
                color = Green142,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
            )
            Text(
                text = "$value $unit",
                style = MaterialTheme.typography.titleMedium.copy(color = Color.Black)
            )
        }
    }
}

@Composable
fun WaterReminderSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(
                color = Green142,
                shape = RoundedCornerShape(24.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        // Row for icon, text, and button
        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Water bottle icon (placeholder)
            /*Image(
                painter = painterResource(id = R.drawable.ic_water_bottle), // Replace with your resource
                contentDescription = "Water bottle",
                modifier = Modifier.size(60.dp),
                contentScale = ContentScale.Fit
            )*/

            // Column with text and button
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Drink enough water\nStay hydrated",
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.White),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Button with glass icon
                Button(
                    onClick = { /* TODO: handle click */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF404A48))
                ) {
                    // Glass icon
                    /*Icon(
                        painter = painterResource(id = R.drawable.ic_glass), // Replace with your resource
                        contentDescription = "Glass icon",
                        tint = Color.White
                    )*/
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Drink now", color = Color.White)
                }
            }
        }
    }
}
