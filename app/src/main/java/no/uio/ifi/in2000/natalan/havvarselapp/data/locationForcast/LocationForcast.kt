
package no.uio.ifi.in2000.natalan.havvarselapp.data.locationForcast


import kotlinx.serialization.Serializable

import com.fasterxml.jackson.annotation.JsonProperty

@Serializable
data class WeatherResponse(
    val type: String,
    val geometry: Geometry,
    val properties: Properties,
)

@Serializable
data class Geometry(
    val type: String,
    val coordinates: List<Double>,
)

@Serializable
data class Properties(
    val meta: Meta,
    val timeseries: List<Series>,
)

@Serializable
data class Meta(
    @JsonProperty("updated_at")
    val updatedAt: String,
    val units: Units,
)

@Serializable
data class Units(
    @JsonProperty("air_pressure_at_sea_level")
    val airPressureAtSeaLevel: String,
    @JsonProperty("air_temperature")
    val airTemperature: String,
    @JsonProperty("air_temperature_max")
    val airTemperatureMax: String,
    @JsonProperty("air_temperature_min")
    val airTemperatureMin: String,
    @JsonProperty("air_temperature_percentile_10")
    val airTemperaturePercentile10: String,
    @JsonProperty("air_temperature_percentile_90")
    val airTemperaturePercentile90: String,
    @JsonProperty("cloud_area_fraction")
    val cloudAreaFraction: String,
    @JsonProperty("cloud_area_fraction_high")
    val cloudAreaFractionHigh: String,
    @JsonProperty("cloud_area_fraction_low")
    val cloudAreaFractionLow: String,
    @JsonProperty("cloud_area_fraction_medium")
    val cloudAreaFractionMedium: String,
    @JsonProperty("dew_point_temperature")
    val dewPointTemperature: String,
    @JsonProperty("fog_area_fraction")
    val fogAreaFraction: String,
    @JsonProperty("precipitation_amount")
    val precipitationAmount: String,
    @JsonProperty("precipitation_amount_max")
    val precipitationAmountMax: String,
    @JsonProperty("precipitation_amount_min")
    val precipitationAmountMin: String,
    @JsonProperty("probability_of_precipitation")
    val probabilityOfPrecipitation: String,
    @JsonProperty("probability_of_thunder")
    val probabilityOfThunder: String,
    @JsonProperty("relative_humidity")
    val relativeHumidity: String,
    @JsonProperty("ultraviolet_index_clear_sky")
    val ultravioletIndexClearSky: String,
    @JsonProperty("wind_from_direction")
    val windFromDirection: String,
    @JsonProperty("wind_speed")
    val windSpeed: String,
    @JsonProperty("wind_speed_of_gust")
    val windSpeedOfGust: String,
    @JsonProperty("wind_speed_percentile_10")
    val windSpeedPercentile10: String,
    @JsonProperty("wind_speed_percentile_90")
    val windSpeedPercentile90: String,
)

@Serializable
data class Series(
    val time: String,
    val data: Data,
)

@Serializable
data class Data(
    val instant: Instant,
    @JsonProperty("next_12_hours")
    val next12Hours: Next12Hours?,
    @JsonProperty("next_1_hours")
    val next1Hours: Next1Hours?,
    @JsonProperty("next_6_hours")
    val next6Hours: Next6Hours?,
)

@Serializable
data class Instant(
    val details: Details,
)

@Serializable
data class Details(
    @JsonProperty("air_pressure_at_sea_level")
    val airPressureAtSeaLevel: Double,
    @JsonProperty("air_temperature")
    val airTemperature: Double,
    @JsonProperty("air_temperature_percentile_10")
    val airTemperaturePercentile10: Double,
    @JsonProperty("air_temperature_percentile_90")
    val airTemperaturePercentile90: Double,
    @JsonProperty("cloud_area_fraction")
    val cloudAreaFraction: Double,
    @JsonProperty("cloud_area_fraction_high")
    val cloudAreaFractionHigh: Double,
    @JsonProperty("cloud_area_fraction_low")
    val cloudAreaFractionLow: Double,
    @JsonProperty("cloud_area_fraction_medium")
    val cloudAreaFractionMedium: Double,
    @JsonProperty("dew_point_temperature")
    val dewPointTemperature: Double,
    @JsonProperty("fog_area_fraction")
    val fogAreaFraction: Double?,
    @JsonProperty("relative_humidity")
    val relativeHumidity: Double,
    @JsonProperty("ultraviolet_index_clear_sky")
    val ultravioletIndexClearSky: Double?,
    @JsonProperty("wind_from_direction")
    val windFromDirection: Double,
    @JsonProperty("wind_speed")
    val windSpeed: Double,
    @JsonProperty("wind_speed_of_gust")
    val windSpeedOfGust: Double?,
    @JsonProperty("wind_speed_percentile_10")
    val windSpeedPercentile10: Double,
    @JsonProperty("wind_speed_percentile_90")
    val windSpeedPercentile90: Double,
)

@Serializable
data class Next12Hours(
    val summary: Summary,
    val details: Details2,
)

@Serializable
data class Summary(
    @JsonProperty("symbol_code")
    val symbolCode: String,
    @JsonProperty("symbol_confidence")
    val symbolConfidence: String,
)

@Serializable
data class Details2(
    @JsonProperty("probability_of_precipitation")
    val probabilityOfPrecipitation: Double,
)

@Serializable
data class Next1Hours(
    val summary: Summary2,
    val details: Details3,
)

@Serializable
data class Summary2(
    @JsonProperty("symbol_code")
    val symbolCode: String,
)

@Serializable
data class Details3(
    @JsonProperty("precipitation_amount")
    val precipitationAmount: Double,
    @JsonProperty("precipitation_amount_max")
    val precipitationAmountMax: Double,
    @JsonProperty("precipitation_amount_min")
    val precipitationAmountMin: Double,
    @JsonProperty("probability_of_precipitation")
    val probabilityOfPrecipitation: Double,
    @JsonProperty("probability_of_thunder")
    val probabilityOfThunder: Double,
)
@Serializable
data class Next6Hours(
    val summary: Summary3,
    val details: Details4,
)

@Serializable
data class Summary3(
    @JsonProperty("symbol_code")
    val symbolCode: String,
)

@Serializable
data class Details4(
    @JsonProperty("air_temperature_max")
    val airTemperatureMax: Double,
    @JsonProperty("air_temperature_min")
    val airTemperatureMin: Double,
    @JsonProperty("precipitation_amount")
    val precipitationAmount: Double,
    @JsonProperty("precipitation_amount_max")
    val precipitationAmountMax: Double,
    @JsonProperty("precipitation_amount_min")
    val precipitationAmountMin: Double,
    @JsonProperty("probability_of_precipitation")
    val probabilityOfPrecipitation: Double,
)