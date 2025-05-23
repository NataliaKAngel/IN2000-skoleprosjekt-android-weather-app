package no.uio.ifi.in2000.natalan.havvarselapp.ui.home

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.content.res.AppCompatResources
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import no.uio.ifi.in2000.natalan.havvarselapp.model.spot.Spot
import no.uio.ifi.in2000.natalan.havvarselapp.ui.components.*
import no.uio.ifi.in2000.natalan.havvarselapp.ui.theme.DefaultBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    homeScreenViewModel: HomeScreenViewModel,
) {
    //UI-state: List<Spot>
    val spotsUIState by homeScreenViewModel.spotsUIState.collectAsState()
    val spots = spotsUIState.spots

    //UI-state: Spot
    val spotUIState by homeScreenViewModel.spotUIState.collectAsState()
    val spot = spotUIState.spot

    //UI-state: Boolean
    val clickedUIState by homeScreenViewModel.clickedUIState.collectAsState()
    val clicked = clickedUIState.clicked

    //Variables for MapBox
    val context = LocalContext.current.applicationContext
    val mapView = createMapView(context)

    //Variables for ModalBottomSheet
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    AddAnnotationsToMap(spots, context, mapView, homeScreenViewModel)

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

            if (clicked) {
                ModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = {
                        isSheetOpen = false
                        homeScreenViewModel.updateClickedUIState(false)
                        },
                    modifier = Modifier.fillMaxWidth(),
                    containerColor= DefaultBlue,

                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 64.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (spot != null) {
                            SpotBoxWithFrame(spot, navController)
                        }
                    }
                }
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
fun createMapView(context: Context): MapView {
    val mapView = remember { MapView(context) }

    mapView.mapboxMap.setCamera(
        CameraOptions.Builder()
            .center(Point.fromLngLat(7.46, 58.02))
            .pitch(0.0)
            .zoom(6.8)
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
    homeScreenViewModel: HomeScreenViewModel
) {
    val annotationDataMap = remember { mutableMapOf<String, String>() } // Map to store annotation data

    LaunchedEffect(mapView, spots) {
        mapView.mapboxMap.loadStyle(Style.MAPBOX_STREETS) { style ->
            val annotationManager = mapView.annotations.createPointAnnotationManager()

            if (spots.isNotEmpty()) {
                spots.forEach { spot ->
                    if (spot.spotDetails.isNotEmpty()) { // Check if spotDetails is not empty
                        val kiteRecommendationSmallThumb =
                            spot.spotDetails.first().kiteRecommendationSmallThumb
                        val drawable =
                            AppCompatResources.getDrawable(context, kiteRecommendationSmallThumb)
                        val bitmap = drawable?.let { convertDrawableToBitmap(it) }

                        bitmap?.let { bitmap1 ->
                            //Add image to map style
                            style.addImage(kiteRecommendationSmallThumb.toString(), bitmap1)

                            //Gets the coordinate from the Spot-object and adds a point on the map
                            val coordinates =
                                spot.predefinedSpot.coordinates.split(",").map { it.toDouble() }
                            val point = Point.fromLngLat(coordinates[1], coordinates[0])

                            //Convert Spot to json text and create a json element
                            val spotJsonString = Gson().toJson(spot)
                            val spotJson = JsonParser.parseString(spotJsonString)

                            //Create annotation settings
                            val annotationOptions = PointAnnotationOptions().apply {
                                withPoint(point)
                                withIconImage(kiteRecommendationSmallThumb.toString())
                                withData(spotJson)
                            }

                            //Create the annotation, add it to the map and save the annotation id in map
                            val annotation = annotationManager.create(annotationOptions)
                            annotationDataMap[annotation.id] = spotJsonString

                            //On annotation marker click
                            annotationManager.addClickListener { clickedAnnotation ->
                                //Gets the clicked spot and updates UI-state
                                val clickedSpotJson = annotationDataMap[clickedAnnotation.id]
                                val clickedSpot = Gson().fromJson(clickedSpotJson, Spot::class.java)
                                homeScreenViewModel.updateSpotUIState(clickedSpot.predefinedSpot.coordinates)

                                //Update clicked UI-state
                                homeScreenViewModel.updateClickedUIState(true)

                                //Return true to indicate that the click was handled
                                true
                            }
                        }
                    }
                }
            }
        }
    }
}


// Convert Drawable to Bitmap using this method
private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
    // Check if the sourceDrawable is null, if so, return null
    if (sourceDrawable == null) {
        return null
    }

    // If the sourceDrawable is a BitmapDrawable, return the bitmap directly
    if (sourceDrawable is BitmapDrawable) {
        return sourceDrawable.bitmap
    }

    // If the sourceDrawable is not a BitmapDrawable, create a new Drawable and draw it onto a new Bitmap
    val constantState = sourceDrawable.constantState ?: return null
    val drawable = constantState.newDrawable().mutate()
    val bitmap: Bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    // Draw the drawable onto the bitmap using a Canvas
    Canvas(bitmap).apply {
        drawable.setBounds(0, 0, width, height)
        drawable.draw(this)
    }

    // Return the resulting bitmap
    return bitmap
}