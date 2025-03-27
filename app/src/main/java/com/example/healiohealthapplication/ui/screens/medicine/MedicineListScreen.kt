package com.example.healiohealthapplication.ui.screens.medicine

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.healiohealthapplication.R
import com.example.healiohealthapplication.data.models.Medicine
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun MedicineListScreenContent(navController: NavController) {
    val medicines = listOf(
        Medicine("Burana", "Pain reliever", "After meal", "1 pill", "5 days"),
        Medicine("Vitamin C", "Boosts immune system", "Morning", "1 tablet", "Daily"),
        Medicine("Painkiller", "Used for headaches", "If needed", "1 pill", "Only when needed")
    )

    Column {
        medicines.forEach { medicine ->
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        val encodedName = URLEncoder.encode(medicine.name, StandardCharsets.UTF_8.toString())
                        val encodedDesc = URLEncoder.encode(medicine.description, StandardCharsets.UTF_8.toString())
                        val encodedSchedule = URLEncoder.encode(medicine.schedule, StandardCharsets.UTF_8.toString())
                        val encodedAmount = URLEncoder.encode(medicine.amount, StandardCharsets.UTF_8.toString())
                        val encodedDuration = URLEncoder.encode(medicine.duration, StandardCharsets.UTF_8.toString())

                        navController.navigate("medicine_detail/$encodedName/$encodedDesc/$encodedSchedule/$encodedAmount/$encodedDuration")
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
