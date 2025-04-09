package com.example.healiohealthapplication.ui.screens.medicine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.healiohealthapplication.data.models.Medicine
import com.example.healiohealthapplication.ui.components.BottomNavBar
import com.example.healiohealthapplication.ui.components.TopNavBar

@Composable
fun AddMedicineScreen(
    navController: NavController,
    viewModel: MedicineViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var schedule by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }

    // Set the userId when screen loads
    LaunchedEffect(Unit) {
        val currentUserId = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid
        if (!currentUserId.isNullOrEmpty()) {
            viewModel.setUserId(currentUserId)
        }
    }

    Scaffold(
        topBar = { TopNavBar("Add Medicine", navController) },
        bottomBar = { BottomNavBar(navController) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(Color(0xFF1E3A5F), Color(0xFF1E1E1E))
                    )
                )
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Add New Medicine", fontSize = 20.sp, color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))

            MedicineInputField("Medicine Name", name) { name = it }
            MedicineInputField("Description", description) { description = it }
            MedicineInputField("Time & Schedule", schedule) { schedule = it }
            MedicineInputField("Amount", amount) { amount = it }
            MedicineInputField("Duration", duration) { duration = it }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    try {
                        val medicine = Medicine(
                            name = name,
                            description = description,
                            schedule = schedule,
                            amount = amount,
                            duration = duration
                        )
                        viewModel.addMedicine(medicine) { success ->
                            if (success) {
                                navController.navigateUp() // Navigate back after adding
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Add Medicine")
            }
        }
    }
}

@Composable
fun MedicineInputField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.White) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        textStyle = LocalTextStyle.current.copy(color = Color.White)
    )
}
