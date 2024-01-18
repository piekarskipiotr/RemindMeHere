package com.piekarskipiotr.remindmehere.presentation.add_reminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.piekarskipiotr.remindmehere.ui.theme.RemindMeHereTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddReminderActivity : ComponentActivity() {
    private val addReminderViewModel: AddReminderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RemindMeHereTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AddReminderView(addReminderViewModel)
                }
            }
        }
    }
}

@Composable
fun AddReminderView(addReminderViewModel: AddReminderViewModel) {
    val placeError = addReminderViewModel.placeError
    val descriptionError = addReminderViewModel.descriptionError

    Scaffold(
        content = { paddingValues ->
            Column {
                Box(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primary)
                )
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
                            value = addReminderViewModel.place,
                            onValueChange = addReminderViewModel::onPlaceChange,
                            label = { Text("Place") },
                            isError = placeError != null,
                            modifier = Modifier.fillMaxWidth()
                        )
                        placeError?.let {
                            Text(
                                text = it,
                                color = Color.Red,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
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
                        Slider(
                            value = addReminderViewModel.sliderValue,
                            onValueChange = addReminderViewModel::onSliderValueChange,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    Button(
                        onClick = { /* Handle add action */ },
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

