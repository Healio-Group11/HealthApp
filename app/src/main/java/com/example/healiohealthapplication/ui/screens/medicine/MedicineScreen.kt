package com.example.healiohealthapplication.ui.screens.medicine

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.healiohealthapplication.R
import com.example.healiohealthapplication.data.models.Medicine
import com.example.healiohealthapplication.navigation.Routes
import com.example.healiohealthapplication.ui.components.BottomNavBar
import com.example.healiohealthapplication.ui.components.TopNavBar
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun MedicineScreen(navController: NavController, viewModel: MedicineViewModel = hiltViewModel()) {
    val medicines by remember { viewModel.medicines }.collectAsState()

    // Fetch medicines from Firestore when the screen loads
    LaunchedEffect(Unit) {
        viewModel.fetchMedicines()
    }

    Scaffold(
        topBar = { TopNavBar("Medicine", navController) },
        bottomBar = { BottomNavBar(navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Routes.ADD_MEDICINE) },
                containerColor = Color(0xFF00796B)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Medicine", tint = Color.White)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(Color(0xFF1E3A5F), Color(0xFF1E1E1E))))
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Medicine Reminder!", fontSize = 20.sp, color = Color.White)

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

            if (medicines.isEmpty()) {
                Text("No medicines added yet.", color = Color.White)
            } else {
                MedicineListContent(medicines, navController)
            }
        }
    }
}

@Composable
fun MedicineListContent(medicines: List<Medicine>, navController: NavController) {
    Column {
        medicines.forEach { medicine ->
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        try {
                            val name = medicine.name?.let { URLEncoder.encode(it, StandardCharsets.UTF_8.toString()) } ?: "Unknown"
                            val description = medicine.description?.let { URLEncoder.encode(it, StandardCharsets.UTF_8.toString()) } ?: "No description"
                            val schedule = medicine.schedule?.let { URLEncoder.encode(it, StandardCharsets.UTF_8.toString()) } ?: "No schedule"
                            val amount = medicine.amount?.let { URLEncoder.encode(it, StandardCharsets.UTF_8.toString()) } ?: "No amount"
                            val duration = medicine.duration?.let { URLEncoder.encode(it, StandardCharsets.UTF_8.toString()) } ?: "No duration"

                            navController.navigate("medicine_detail/$name/$description/$schedule/$amount/$duration")
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
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

                    Text(text = medicine.name ?: "Unknown Medicine", fontSize = 16.sp)
                }
            }
        }
    }
}

