package no.uio.ifi.in2000.natalan.havvarselapp.ui.home

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.content.res.AppCompatResources
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import no.uio.ifi.in2000.natalan.havvarselapp.model.spot.Spot
import no.uio.ifi.in2000.natalan.havvarselapp.ui.components.*

@Composable
fun HomeScreen(
    navController: NavController,
    homeScreenViewModel: HomeScreenViewModel,
) {
    //UI-state: List<Spot?>
    val spotsUIState by homeScreenViewModel.spotsUIState.collectAsState()
    val spots = spotsUIState.spots

    //UI-state: Spot?
    val spotUIState by homeScreenViewModel.spotUIState.collectAsState()

    val thumbUIState by homeScreenViewModel.thumbUIState.collectAsState()
    val thumbs = thumbUIState.thumbs

    //Variables for map
    val context = LocalContext.current.applicationContext
    val mapView = createMapScreen(context)


    homeScreenViewModel.updateThumbsUIState(spots)
    AddAnnotationsToMap(spots, context, mapView, thumbs, navController)


    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            // Map
            AndroidView(
                factory = { mapView },
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .align(Alignment.TopCenter)
            ) {
                TopBar(infoButtonClick = { navController.navigate("InfoScreen") })
            }

            // NavBar
            Box(
                modifier = Modifier
                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                    .align(Alignment.BottomCenter)
            ) {
                NavBar(navController)
            }
        }
    }
}

@Composable
fun createMapScreen(context: Context): MapView {
    val mapView = remember { MapView(context) }

    mapView.mapboxMap.setCamera(
        CameraOptions.Builder()
            .center(Point.fromLngLat(7.99, 58.146))
            .pitch(0.0)
            .zoom(6.0)
            .bearing(0.0)
            .build()
    )
    return mapView
}

@Composable
fun AddAnnotationsToMap(
    spots: List<Spot>,
    context: Context,
    mapView: MapView,
    iconId: Map<String, Int>, // Name to icon
    navController: NavController
) {
    LaunchedEffect(mapView) {
        mapView.mapboxMap.loadStyle(Style.MAPBOX_STREETS) { style ->
            val annotationManager = mapView.annotations.createPointAnnotationManager()
            annotationManager.addClickListener(com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationClickListener {
                // Handle click event here
                    navController.navigate("FavouriteScreen")
                // For example, you can show a toast message
                Toast.makeText(context, "Marker clicked", Toast.LENGTH_SHORT).show()
                true // Return true if the click event is consumed
            })
            spots.forEach { spot ->
                // Load your custom icon as a Bitmap
                val drawable = iconId[spot.predefinedSpot.coordinates]?.let {
                    AppCompatResources.getDrawable(context, it)
                }
                val bitmap = convertDrawableToBitmap(drawable)
                if (bitmap != null) {
                    // Add the bitmap as a custom icon in the Mapbox style.
                    style.addImage(iconId[spot.predefinedSpot.coordinates].toString(), bitmap)
                    val coordinates = spot.predefinedSpot.coordinates.split(",").map { it.toDouble() }
                    val point = Point.fromLngLat(coordinates[1], coordinates[0])
                    val annotationOptions = PointAnnotationOptions()
                        .withPoint(point)
                        .withIconImage(iconId[spot.predefinedSpot.coordinates].toString())
                    annotationManager.create(annotationOptions)
                }
            }
        }
    }
}


// Convert Drawable to Bitmap using this method
private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
    if (sourceDrawable == null) {
        return null
    }
    return if (sourceDrawable is BitmapDrawable) {
        sourceDrawable.bitmap
    } else {
        // Copy the drawable object to avoid manipulation on the same reference
        val constantState = sourceDrawable.constantState ?: return null
        val drawable = constantState.newDrawable().mutate()
        val bitmap: Bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth, drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        bitmap
    }
}