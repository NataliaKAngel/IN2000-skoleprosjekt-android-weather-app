
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
import no.uio.ifi.in2000.natalan.havvarselapp.data.locationForecast.LocationForecastDataSource
import no.uio.ifi.in2000.natalan.havvarselapp.data.locationForecast.LocationForecastRepository
import no.uio.ifi.in2000.natalan.havvarselapp.ui.locationForecast.LocationForecastViewModel
import no.uio.ifi.in2000.natalan.havvarselapp.ui.locationForecast.LocationForecastScreen
import no.uio.ifi.in2000.natalan.havvarselapp.ui.metAlerts.MetAlertsScreen
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
                    // Creates instances of datasources and repositories
                    val locationForecastDataSource = LocationForecastDataSource()
                    val locationForecastRepository = LocationForecastRepository(locationForecastDataSource)


                    // Creates instances of viewModels and Screens
                    val locationForecastScreen = LocationForecastScreen()
                    val locationForestViewModel : LocationForecastViewModel = LocationForecastViewModel(locationForecastRepository)
                    val metAlertsScreen = MetAlertsScreen()
                    val metAlertViewModel : MetAlertViewModel = viewModel()
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "WarningScreen") {
                        // Navigere til de ulike
                        composable("HomeScreen") {locationForecastScreen.HomeScreen(navController = navController, locationForestViewModel = locationForestViewModel)}
                        composable("WarningScreen") { metAlertsScreen.WarningScreen(navController = navController, metAlertViewModel = metAlertViewModel) }
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
