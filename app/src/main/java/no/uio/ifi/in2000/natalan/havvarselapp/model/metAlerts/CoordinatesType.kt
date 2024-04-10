package no.uio.ifi.in2000.natalan.havvarselapp.model.metAlerts

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable
enum class CoordinatesType {
    @SerialName("MultiPolygon")
    MULTIPOLYGON,

    @SerialName("Polygon")
    POLYGON
}

@Serializable(with = SectionSerializer::class)
sealed class Coordinates {
    // Enforcing that every subclass should have a variable called type.
    @SerialName("type")
    abstract val type: CoordinatesType
}

@Serializable
data class MultipolygonVO(
    override val type: CoordinatesType = CoordinatesType.MULTIPOLYGON,
    val coordinates: List<List<List<List<String?>>>>
) : Coordinates()

@Serializable
data class PolygonVO(
    override val type: CoordinatesType = CoordinatesType.POLYGON,
    val coordinates: List<List<List<String?>>>
) : Coordinates()

object SectionSerializer :
    JsonContentPolymorphicSerializer<Coordinates>(
        Coordinates::class
    ) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<Coordinates> {
        return when (element.jsonObject["type"]?.jsonPrimitive?.content) {
            "Multipolygon" -> MultipolygonVO.serializer()
            "Polygon" -> PolygonVO.serializer()
            else -> throw Exception("ERROR: No Serializer found. Serialization failed.")
        }
    }
}