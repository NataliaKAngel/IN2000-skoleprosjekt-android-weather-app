
package no.uio.ifi.in2000.natalan.havvarselapp
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import no.uio.ifi.in2000.natalan.havvarselapp.ui.theme.HavvarselAppTheme
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import no.uio.ifi.in2000.natalan.havvarselapp.ui.locationForecast.LocationForestViewModel
import no.uio.ifi.in2000.natalan.havvarselapp.ui.locationForecast.HomeScreen
import no.uio.ifi.in2000.natalan.havvarselapp.ui.metAlerts.WarningScreen
import no.uio.ifi.in2000.natalan.havvarselapp.ui.metAlerts.MetAlertViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HavvarselAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val homeScreen = HomeScreen()
                    val locationForestViewModel : LocationForestViewModel = viewModel()
                    val warningScreen = WarningScreen()
                    val metAlertViewModel : MetAlertViewModel = viewModel()
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "WarningScreen") {
                        // Navigere til de ulike
                        composable("HomeScreen") {homeScreen.HomeScreen(navController = navController, locationForestViewModel = locationForestViewModel)}
                        composable("WarningScreen") { warningScreen.WarningScreen(navController = navController, metAlertViewModel = metAlertViewModel) }
                    }
                }
            }
        }
    }
}

/*
class MainActivity : ComponentActivity() {
    private val metAlertsViewModel by viewModels<MetAlertsViewModel>()
    private val weatherAndWindViewModel by viewModels<LocationForestViewModel>()
    private val ifiProxyRepository = IfiProxyRepository(IfiProxyDataSource()) // Testing MetAlerts
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HavvarselAppTheme {
// A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
// Launch a coroutine to execute internetCheck
                    LaunchedEffect(Unit) {
                        try {
                            internetCheck()
                            println("Internet is available")
                        } catch (e: Exception) {
                            println("No internet connection")
                        }
                    }
// Call getMetAlerts function inside a coroutine scope
                    LaunchedEffect(Unit) {
                        try {
                            val response = ifiProxyRepository.getIfiProxy()
                            println("Met alerts response status: ${response.status}")
                            weatherAndWindViewModel.fetchWeatherAndWindData()
                        } catch (e: Exception) {
                            println("Error fetching met alerts: ${e.message}")
                        }
                    }
                    HavvarselApp(metAlertsViewModel, weatherAndWindViewModel)
                }
            }
        }
    }
}
suspend fun internetCheck() {
    val client = HttpClient(CIO)
    val response: HttpResponse = client.get("https://ktor.io/")
    println(response.status)
    client.close()
}
@Composable
fun HavvarselApp(
    metAlertsViewModel: MetAlertsViewModel = viewModel(),
    weatherAndWindViewModel: LocationForestViewModel = viewModel()
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "metAlerts") {
        composable("metAlerts") { MetAlertsScreen(navController) }
        composable("weatherAndWind") { LocationForestScreen(navController) }
    }
}*/
