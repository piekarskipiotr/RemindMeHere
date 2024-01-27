package com.piekarskipiotr.remindmehere.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapFragment(navController: NavController, homeViewModel: HomeViewModel) {
    val reminders = homeViewModel.getReminders().observeAsState(initial = emptyList())

    Box(modifier = Modifier.fillMaxSize()) {
        val userLocation by homeViewModel.currentLocation.collectAsState()
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(userLocation, 15f)
        }

        LaunchedEffect(userLocation) {
            cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(zoomControlsEnabled = false)
        ) {
            reminders.value.forEach { reminder ->
                Marker(
                    state = MarkerState(position = LatLng(reminder.latitude, reminder.longitude)),
                    title = reminder.description,
                )
            }
        }

        IconButton(
            onClick = { navController.navigate("listFragment") },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .background(Color.White, shape = CircleShape)
        ) {
            Icon(Icons.Rounded.List, contentDescription = "List view of reminders")
        }
    }
}
