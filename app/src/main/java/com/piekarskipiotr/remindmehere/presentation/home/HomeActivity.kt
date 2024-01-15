package com.piekarskipiotr.remindmehere.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.piekarskipiotr.remindmehere.presentation.add_reminder.AddReminderActivity
import com.piekarskipiotr.remindmehere.ui.theme.RemindMeHereTheme


class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RemindMeHereTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeContainer()
                }
            }
        }
    }
}


@Composable
fun HomeContainer() {
    val navController = rememberNavController()
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val intent = Intent(context, AddReminderActivity::class.java)
                    context.startActivity(intent)
                },
            ) {
                Icon(Icons.Rounded.Add, contentDescription = "Add new reminder")
            }
        },
        content = { paddingValues ->
            NavHost(
                navController,
                startDestination = "mapFragment",
                Modifier.padding(paddingValues)
            ) {
                composable("mapFragment") { MapFragment(navController) }
                composable("listFragment") { ListFragment(navController) }
            }
        }
    )
}



