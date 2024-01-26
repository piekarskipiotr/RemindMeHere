package com.piekarskipiotr.remindmehere.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.piekarskipiotr.remindmehere.R


@Composable
fun ListFragment(navController: NavController, homeViewModel: HomeViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {

        val reminders = homeViewModel.getReminders().observeAsState(initial = emptyList())
        //val reminders = remember { DataProvider.reminderList }
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(
                items = reminders.value ?: emptyList(),
                itemContent = {
                    ReminderListItem(reminder = it,  homeViewModel = homeViewModel)
                }
            )
        }
        IconButton(
            onClick = { navController.navigate("mapFragment") },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .background(Color.White, shape = CircleShape)
        ) {
            Icon(
                painterResource(id = R.drawable.map_icon),
                contentDescription = "Map view of reminders"
            )
        }
    }
}

