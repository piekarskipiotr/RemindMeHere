package com.piekarskipiotr.remindmehere.presentation.add_reminder

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.piekarskipiotr.remindmehere.ui.theme.RemindMeHereTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddReminderActivity : ComponentActivity() {
    private val addReminderViewModel: AddReminderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val latitude = intent.getDoubleExtra("latitude", 0.0)
        val longitude = intent.getDoubleExtra("longitude", 0.0)
        addReminderViewModel.insertionSuccess.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        setContent {
            RemindMeHereTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AddReminderView(addReminderViewModel, LatLng(latitude, longitude))
                }
            }
        }
    }
}

@Composable
fun MapView(addReminderViewModel: AddReminderViewModel, latLng: LatLng) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(latLng, 15f)
    }

    Box(
        modifier = Modifier
            .height(400.dp)
            .fillMaxWidth()
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(zoomControlsEnabled = false)
        )

        Icon(
            Icons.Default.Place, contentDescription = "Pin", modifier = Modifier
                .align(Alignment.Center)
                .size(32.dp)
        )

        LaunchedEffect(cameraPositionState.isMoving) {
            if (!cameraPositionState.isMoving) {
                val center = cameraPositionState.position.target
                addReminderViewModel.updateLocation(center.longitude, center.latitude)
            }
        }
    }
}

@Composable
fun AddReminderView(addReminderViewModel: AddReminderViewModel, latLng: LatLng) {
    val descriptionError = addReminderViewModel.descriptionError

    Scaffold(
        content = { paddingValues ->
            Column {
                MapView(addReminderViewModel, latLng)
                Column(
                    Modifier
                        .padding(paddingValues)
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Spacer(modifier = Modifier.height(16.dp))
                        TextField(
                            value = addReminderViewModel.description,
                            onValueChange = addReminderViewModel::onDescriptionChange,
                            label = { Text("Description") },
                            isError = descriptionError != null,
                            modifier = Modifier.fillMaxWidth()
                        )
                        descriptionError?.let {
                            Text(
                                text = it,
                                color = Color.Red,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    Button(
                        onClick = { addReminderViewModel.onSave() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text("Add")
                    }
                }
            }
        }
    )
}

