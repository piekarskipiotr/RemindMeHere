package com.piekarskipiotr.remindmehere.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.piekarskipiotr.remindmehere.data.entities.Reminder

@Composable
fun ReminderListItem(reminder: Reminder, homeViewModel: HomeViewModel){
    Row {
        Column (modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .align(Alignment.CenterVertically)
        ){
            Text(text = reminder.description, style = typography.headlineMedium)
            Row  {
                Text(text = reminder.longitude.toString())
                Text(text = reminder.latitude.toString())
            }
            Button(onClick = { homeViewModel.deleteReminder(reminder) }) {
                Text("Delete")
            }
        }
    }
}