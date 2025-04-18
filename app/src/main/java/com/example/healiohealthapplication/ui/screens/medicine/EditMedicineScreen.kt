package com.example.healiohealthapplication.ui.screens.medicine

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.healiohealthapplication.ui.components.BottomNavBar
import com.example.healiohealthapplication.ui.components.TopNavBar
import com.example.healiohealthapplication.data.models.Medicine
import com.example.healiohealthapplication.ui.components.OverlappingCircle
import com.example.healiohealthapplication.ui.screens.shared.SharedViewModel
import com.example.healiohealthapplication.ui.theme.Green142
import kotlinx.coroutines.time.delay

@Composable
fun EditMedicineScreen(
    medicineId: String,
    navController: NavController,
    viewModel: MedicineViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel
) {
    val userData by sharedViewModel.userData.collectAsState()

    LaunchedEffect(userData?.id) {
        userData?.id?.let { id ->
            viewModel.setUserId(id)
            viewModel.getMedicineById(id, medicineId)
        }
    }

    // Get the medicine details from the ViewModel state
    val medicine = viewModel.medicine.collectAsState().value

    // Show loading indicator while medicine is being fetched
    if (medicine == null) {
        // This is just a placeholder UI when medicine is being fetched
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        // States to hold updated values (initialized with existing medicine details)
        var newMedicineName by remember { mutableStateOf(medicine.name) }
        var newDescription by remember { mutableStateOf(medicine.description) }
        var newSchedule by remember { mutableStateOf(medicine.schedule) }
        var newAmount by remember { mutableStateOf(medicine.amount) }
        var newDuration by remember { mutableStateOf(medicine.duration) }

        // Scaffold to wrap the screen with TopBar, BottomBar, and content
        Scaffold(
            topBar = { TopNavBar("Edit Medicine", navController) },
            bottomBar = { BottomNavBar(navController) }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                OverlappingCircle()
                // Main content of the screen
                LazyColumn (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding)
                        .padding(top = 20.dp, start = 16.dp, end = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item { Text("Edit Medicine", fontSize = 20.sp, color = Color.Black) }

                    item { Spacer(modifier = Modifier.height(16.dp)) }
                    // Edit Fields using the renamed MedicineInputField
                    item { EditMedicineInputField(
                        label = "Medicine Name",
                        value = newMedicineName,
                        onValueChange = { newMedicineName = it }
                    ) }

                    item { Spacer(modifier = Modifier.height(8.dp)) }

                    item { EditMedicineInputField(
                        label = "Description",
                        value = newDescription,
                        onValueChange = { newDescription = it }
                    ) }

                    item { Spacer(modifier = Modifier.height(8.dp)) }

                    item { EditMedicineInputField(
                        label = "Schedule",
                        value = newSchedule,
                        onValueChange = { newSchedule = it }
                    ) }

                    item { Spacer(modifier = Modifier.height(8.dp)) }

                    item { EditMedicineInputField(
                        label = "Amount",
                        value = newAmount,
                        onValueChange = { newAmount = it }
                    ) }

                    item { Spacer(modifier = Modifier.height(8.dp)) }

                    item { EditMedicineInputField(
                        label = "Duration",
                        value = newDuration,
                        onValueChange = { newDuration = it }
                    ) }

                    item { Spacer(modifier = Modifier.height(24.dp)) }

                    // Save Button
                    item { Button(
                        onClick = {
                            // Use the medicineId to update the medicine with the new details
                            val updatedMedicine = medicine.copy(
                                name = newMedicineName,
                                description = newDescription,
                                schedule = newSchedule,
                                amount = newAmount,
                                duration = newDuration
                            )

                            // Call the ViewModel to update the medicine in the repository
                            viewModel.updateMedicine(updatedMedicine) { success ->
                                if (success) {
                                    Toast.makeText(
                                        navController.context,
                                        "Medicine details updated!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    navController.navigateUp()  // Navigate back after updating
                                } else {
                                    Toast.makeText(
                                        navController.context,
                                        "Failed to update medicine.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Green142)
                    ) {
                        Text("Save Changes", color = Color.White, fontSize = 15.sp)
                    } }

                    item { Spacer(modifier = Modifier.height(8.dp)) }

                    // Cancel Button
                    item { OutlinedButton(
                        onClick = {
                            // Navigate back without saving
                            navController.navigateUp()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Cancel", color = Color.White, fontSize = 15.sp)
                    } }
                }
            }
        }
    }
}

@Composable
fun EditMedicineInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label)
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = label) },
            singleLine = true
        )
    }
}

