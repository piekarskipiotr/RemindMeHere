package com.piekarskipiotr.remindmehere.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.piekarskipiotr.remindmehere.presentation.add_reminder.AddReminderActivity
import com.piekarskipiotr.remindmehere.ui.theme.RemindMeHereTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RemindMeHereTheme {
                HomeContent(homeViewModel)
            }
        }
    }
}

@Composable
fun HomeContent(homeViewModel: HomeViewModel) {
    val navController = rememberNavController()
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
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
                composable("mapFragment") { MapFragment(navController, homeViewModel) }
                composable("listFragment") { ListFragment(navController) }
            }
        }
    )
}



