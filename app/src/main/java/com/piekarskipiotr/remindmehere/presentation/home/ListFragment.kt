package com.piekarskipiotr.remindmehere.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.piekarskipiotr.remindmehere.R

@Composable
fun ListFragment(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        IconButton(
            onClick = { navController.navigate("mapFragment") },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                painterResource(id = R.drawable.map_icon),
                contentDescription = "Map view of reminders"
            )
        }
    }
}