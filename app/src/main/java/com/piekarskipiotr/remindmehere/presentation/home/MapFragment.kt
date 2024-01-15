package com.piekarskipiotr.remindmehere.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun MapFragment(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        IconButton(
            onClick = { navController.navigate("listFragment") },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(Icons.Rounded.List, contentDescription = "List view of reminders")
        }
    }
}
