package no.uio.ifi.in2000.natalan.havvarselapp.ui.home

import com.mapbox.maps.plugin.annotation.OnAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
fun interface OnPointAnnotationClickListener : OnAnnotationClickListener<PointAnnotation> {
    abstract override fun onAnnotationClick(annotation: PointAnnotation): Boolean
}