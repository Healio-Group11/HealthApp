package com.example.healiohealthapplication.ui.screens.medicine

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.healiohealthapplication.ui.components.BottomNavBar
import com.example.healiohealthapplication.ui.components.TopNavBar
import com.example.healiohealthapplication.data.models.Medicine

@Composable
fun MedicineDetailScreen(
    medicineId: String,
    medicineName: String,
    description: String,
    schedule: String,
    amount: String,
    duration: String,
    navController: NavController,
    viewModel: MedicineViewModel = hiltViewModel()
) {
    // State to show confirmation dialog on delete
    val showDeleteDialog = remember { mutableStateOf(false) }

    if (showDeleteDialog.value) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog.value = false },
            title = { Text(text = "Confirm Delete") },
            text = { Text(text = "Are you sure you want to delete this medicine?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteMedicine(medicineId) { success ->
                            if (success) {
                                navController.navigateUp() // Navigate back after successful deletion
                            }
                        }
                        showDeleteDialog.value = false
                    }
                ) {
                    Text("Yes", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog.value = false }) {
                    Text("No")
                }
            }
        )
    }

    Scaffold(
        topBar = { TopNavBar(title = "Medicine Details", navController) },
        bottomBar = { BottomNavBar(navController) }
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
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Medicine Name
                Text(
                    text = medicineName,
                    fontSize = 24.sp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Medicine Icon with Circle Background
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

                Spacer(modifier = Modifier.height(24.dp))

                // Details of Medicine
                MedicineDescriptionCard(description)
                MedicineDetailCard("Time & Schedule:", schedule)
                MedicineDetailCard("Amount:", amount)
                MedicineDetailCard("Duration:", duration)

                Spacer(modifier = Modifier.height(32.dp))

                // Edit Button
                Button(
                    onClick = {
                        navController.navigate("edit_medicine/$medicineId") // Navigate to Edit screen
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Edit Medicine")
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Delete Button
                Button(
                    onClick = {
                        showDeleteDialog.value = true // Show confirmation dialog
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Delete Medicine", color = Color.White)
                }
            }
        }
    }
}


// Detail Card for Medicine Data
@Composable
fun MedicineDetailCard(title: String, value: String) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "$title ", fontSize = 16.sp, color = Color.Black)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = value, fontSize = 16.sp, color = Color.DarkGray)
        }
    }
}

// Medicine Description Card
@Composable
fun MedicineDescriptionCard(description: String) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3A75C4)) // Blue background for contrast
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Description:", fontSize = 18.sp, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = description, fontSize = 16.sp, color = Color.White)
        }
    }
}
