package com.ucne.fitnessdarck.presentation.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ucne.fitnessdarck.presentation.screens.authentication.AuthState
import com.ucne.fitnessdarck.presentation.screens.authentication.AuthViewModel

@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel,
) {
    val authState by authViewModel.authState.observeAsState(AuthState.Unauthenticated)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 16.dp)
        )
        val email = when (authState) {
            is AuthState.Authenticated -> authViewModel.auth.currentUser?.email ?: "Unknown"
            else -> "Unknown"
        }
        Text(text = email, style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Show Routines")
        }
    }
}
