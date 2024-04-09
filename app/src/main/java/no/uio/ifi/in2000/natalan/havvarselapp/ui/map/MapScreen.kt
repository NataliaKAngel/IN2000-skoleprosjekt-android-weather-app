package no.uio.ifi.in2000.natalan.havvarselapp.ui.map

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style

class MapScreen {
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
}
