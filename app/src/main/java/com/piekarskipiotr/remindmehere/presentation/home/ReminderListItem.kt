package com.piekarskipiotr.remindmehere.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.piekarskipiotr.remindmehere.data.entities.Reminder

@Composable
fun ReminderListItem(reminder: Reminder, homeViewModel: HomeViewModel){
    Card(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp).fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ){
        Row {
            Column (modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
            ){
                Text(text = reminder.description, style = MaterialTheme.typography.headlineMedium)
                Text(text = reminder.longitude.toString())
                Text(text = reminder.latitude.toString())
                Button(onClick = { homeViewModel.deleteReminder(reminder) }) {
                    Text("Delete")
                }
            }
        }
    }
}