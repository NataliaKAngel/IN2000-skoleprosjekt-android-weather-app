package no.uio.ifi.in2000.natalan.havvarselapp.ui.map

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView

class MapScreen {

    @SuppressLint("SuspiciousIndentation")
    @Composable
    fun createMapScreen(
        context: Context): MapView {
        val mapView = MapView(context)
        mapView.mapboxMap.setCamera(
            CameraOptions.Builder()
                .center(Point.fromLngLat(-98.0, 39.5))
                .pitch(0.0)
                .zoom(2.0)
                .bearing(0.0)
                .build()
        )
        return mapView
    }
}
