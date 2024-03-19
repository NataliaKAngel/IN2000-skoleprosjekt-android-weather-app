package no.uio.ifi.in2000.natalan.havvarselapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import no.uio.ifi.in2000.natalan.havvarselapp.ui.theme.HavvarselAppTheme
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import no.uio.ifi.in2000.natalan.havvarselapp.data.IfiProxyDataSource
import no.uio.ifi.in2000.natalan.havvarselapp.data.IfiProxyRepository
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import no.uio.ifi.in2000.natalan.havvarselapp.model.locationForcast.LocationForestScreen
import no.uio.ifi.in2000.natalan.havvarselapp.model.locationForcast.LocationForestViewModel
import no.uio.ifi.in2000.natalan.havvarselapp.model.metAlerts.MetAlertsScreen
import no.uio.ifi.in2000.natalan.havvarselapp.model.metAlerts.MetAlertsViewModel

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
    locationForcastViewModel: LocationForestViewModel = viewModel()
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "metAlerts") {
        composable("metAlerts") { MetAlertsScreen(navController) }
        composable("locationForcast") { LocationForestScreen(navController) }
    }
}