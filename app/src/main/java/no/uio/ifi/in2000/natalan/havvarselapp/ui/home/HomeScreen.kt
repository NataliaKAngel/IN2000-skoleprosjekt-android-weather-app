package no.uio.ifi.in2000.natalan.havvarselapp.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

class HomeScreen {
    @SuppressLint("NotConstructor")
    @Composable
    fun HomeScreen(
        navController: NavController,
        homeScreenViewModel: HomeScreenViewModel
    ) {
        val lfUIState by homeScreenViewModel.lfUIState.collectAsState()
        Column {
            Text(
                "Her kommer info om API til Location Forcast",
                modifier = Modifier.padding(8.dp),
                    style = TextStyle(fontSize = 35.sp)
            )
        }
    }
}