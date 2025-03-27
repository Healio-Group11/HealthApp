package com.example.healiohealthapplication.ui.screens.medicine

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.healiohealthapplication.R
import com.example.healiohealthapplication.ui.components.BottomNavBar
import com.example.healiohealthapplication.ui.components.TopNavBar
import androidx.compose.ui.graphics.Brush
import androidx.compose.material3.FloatingActionButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import com.example.healiohealthapplication.data.Medicine
import com.example.healiohealthapplication.navigation.Routes
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun MedicineScreen(navController: NavController, modifier: Modifier) {
    Scaffold(
        topBar = { TopNavBar("Medicine", navController) },
        bottomBar = { BottomNavBar(navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {navController.navigate(Routes.ADD_MEDICINE)},
                containerColor = Color(0xFF00796B)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Medicine", tint = Color.White)
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF1E3A5F), Color(0xFF1E1E1E))
                    )
                )
                .padding(innerPadding)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Medicine Reminder!",
                    fontSize = 20.sp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Circle behind the icon
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(120.dp)
                        .background(Color(0xFF3A75C4), shape = CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.medicine),
                        contentDescription = "Medicine Icon",
                        modifier = Modifier.size(80.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                MedicineListContent(navController)
            }
        }
    }
}


@Composable
fun MedicineListContent(navController: NavController) {
    val medicines = listOf(
        Medicine("Burana", "Pain reliever", "After meal", "2 pills/day", "3 months"),
        Medicine("Vitamin C", "Immune booster", "Morning", "1 pill/day", "1 month"),
        Medicine("Painkiller", "For headache", "If needed", "1 pill", "As required")
    )

    Column {
        medicines.forEach { medicine ->
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        // Encode all parameters before passing them to the navigation
                        val name = URLEncoder.encode(medicine.name, StandardCharsets.UTF_8.toString())
                        val description = URLEncoder.encode(medicine.description, StandardCharsets.UTF_8.toString())
                        val schedule = URLEncoder.encode(medicine.schedule, StandardCharsets.UTF_8.toString())
                        val amount = URLEncoder.encode(medicine.amount, StandardCharsets.UTF_8.toString())
                        val duration = URLEncoder.encode(medicine.duration, StandardCharsets.UTF_8.toString())

                        // Navigate to MedicineDetailScreen with all the parameters
                        navController.navigate("medicine_detail/$name/$description/$schedule/$amount/$duration")
                    }
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.medicine),
                        contentDescription = "Pill",
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(text = medicine.name, fontSize = 16.sp)
                }
            }
        }
    }
}

