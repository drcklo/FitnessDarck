package com.ucne.fitnessdarck.presentation.screens.exercises

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.ucne.fitnessdarck.data.local.entities.ExerciseEntity
import com.ucne.fitnessdarck.util.Constants

@Composable
fun ExerciseItem(
    exercise: ExerciseEntity, onClick: () -> Unit
) {
    Card(shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(max(LocalConfiguration.current.screenHeightDp.dp / 3, 200.dp))
            .padding(8.dp)
            .clickable { onClick() }) {
        Column {
            val imageUrl = "${Constants.BASE_URL}images/${exercise.id}/0.jpg"

            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(max(LocalConfiguration.current.screenHeightDp.dp / 6, 100.dp)),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(8.dp)) {
                exercise.name?.let {
                    Text(
                        text = it, style = MaterialTheme.typography.headlineSmall.copy(
                            fontSize = 20.sp, fontWeight = FontWeight.Bold
                        )
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Primary Muscle: ${
                        exercise.primaryMuscles?.joinToString(", ")
                            ?.replaceFirstChar(Char::uppercase)
                    }", style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Equipment: ${exercise.equipment?.replaceFirstChar(Char::uppercase)}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Level: ${exercise.level?.replaceFirstChar(Char::uppercase)}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}