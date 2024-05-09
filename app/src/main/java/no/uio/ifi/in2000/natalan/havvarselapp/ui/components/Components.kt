package no.uio.ifi.in2000.natalan.havvarselapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import no.uio.ifi.in2000.natalan.havvarselapp.R
import no.uio.ifi.in2000.natalan.havvarselapp.model.spot.Spot
import no.uio.ifi.in2000.natalan.havvarselapp.model.spot.SpotInfo
import no.uio.ifi.in2000.natalan.havvarselapp.ui.theme.*

//Standard radius for box corners and often padding
private val StandardRadius: Dp = 16.dp

@Composable
fun TopBar(infoButtonClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp)
            .background(
                color = White,
                shape = RoundedCornerShape(size = StandardRadius)
            )
            .padding(StandardRadius)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.signature),
                contentDescription = "Signatur: Havvarsel - Kitevarsel",
                contentScale = ContentScale.None
            )
            InfoButton(text = "Info", onClick = infoButtonClick)
        }
    }
}
@Composable
fun InfoButton(text: String, onClick: () -> Unit){
    Box(
        modifier = Modifier
            .clickable(onClick = onClick)
            .width(65.dp)
            .height(44.dp)
            .background(
                color = DefaultBlue,
                shape = RoundedCornerShape(size = 12.dp)
            )
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start)
        ) {
            Text(
                text = text,
                style = TextStyle(
                    fontSize = 9.sp,
                    fontWeight = FontWeight.W500,
                    color = BlueSignature
                )
            )
            Image(
                painter = painterResource(id = R.drawable.info),
                contentDescription = "Knapp: Info",
                contentScale = ContentScale.None
            )
        }
    }
}

@Composable
fun NavButton(navController: NavController, route: String, text: String, icon: Int, contentDescription: String) { // Parameters: Text and icon
    var clicked by remember { mutableStateOf(false) }

    Box(
        Modifier
            .width(88.dp)
            .height(72.dp)
            .background(
                color = if (clicked) ActionBlue else DefaultBlue,
                shape = RoundedCornerShape(size = StandardRadius)
            )
            .clickable {
                clicked = true
                navController.navigate(route)
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = icon), // Using icon parameter
                contentDescription = contentDescription,
                contentScale = ContentScale.None
            )
            Text(
                text = text, // Using the text sent to the component as a parameter
                style = TextStyle(
                    fontSize = 9.sp,
                    fontFamily = FontFamily(Font(R.font.inter_font)),
                    fontWeight = FontWeight(500),
                    color = BlueSignature
                )
            )
        }
    }
}

@Composable
fun NavBar(navController: NavController){ //NavBar on the bottom of the screen. Can be used to navigate to "HomeScreen", "FavouriteScreen" and "SettingScreen"
    Box(
        Modifier
            .width(328.dp)
            .height(104.dp)
            .background(
                color = White,
                shape = RoundedCornerShape(size = StandardRadius)
            )
            .padding(StandardRadius)
    ){
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
            verticalAlignment = Alignment.Bottom,
        ) {
            NavButton(navController = navController, route = "HomeScreen", text = "Kart", icon = R.drawable.map, contentDescription = "Knapp: Naviger til kart")
            NavButton(navController = navController, route = "FavouriteScreen", text = "Favoritter", icon = R.drawable.favourite, contentDescription = "Knapp: Naviger til favoritter")
            NavButton(navController = navController, route = "SettingsScreen", text = "Instillinger", icon = R.drawable.settings, contentDescription = "Knapp: Naviger til innstillinger")
        }
    }
}

@Composable
fun GoToMap(navController: NavController){
    Box(
        Modifier
            .clickable {
            navController.popBackStack() },
    ){
        Image(
            painter = painterResource(id = R.drawable.gotomap),
            contentDescription = "Knapp: Tilbake til kart",
            contentScale = ContentScale.None
        )
    }
}

@Composable
fun KiteConditionInfoBox() {
    Column(
        modifier = Modifier
            .background(color = White, shape = RoundedCornerShape(size = StandardRadius))
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {

            Text(
                text = "Kitevarsel",
                style = TextStyle(
                    letterSpacing = (-0.05).em,
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.inter_font)),
                    fontWeight = FontWeight(600),
                    color = TextColor,
                )
            )
            Text(
                text = "Informasjon",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.inter_font)),
                    fontWeight = FontWeight(400),
                    color = TextColor,
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.width(300.dp),
                text = "Kitevarsel gir kitere anbefalinger om kiteforhold på utvalgte kitespotter langs kysten av Norge. Anbefalingene er fargekodet slik:",
                style = TextStyle(
                    fontSize = 9.sp,
                    fontFamily = FontFamily(Font(R.font.inter_font)),
                    fontWeight = FontWeight(400),
                    color = TextColor,
                )
            )
        }
    }
}

 @Composable
fun InfoColorsColumn(){
    LazyColumn(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .height(360.dp)
    ) {
        item {
            KiteConditionColorBox(
                icon = R.drawable.sgreythumb,
                contentDescription = "Ikon: Grå tommel ned",
                title ="Ingen kiteforhold",
                info = "0-5 m/s og/eller feil\n vindretning. Umulig å kite."
            )
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            KiteConditionColorBox(
                icon = R.drawable.sbluethumb,
                contentDescription = "Ikon: Blå tommel opp",
                title = "Middels kiteforhold",
                info = "5<7 m/s og riktig vindretning.\nBør ha større kite."
            )
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            KiteConditionColorBox(
                icon = R.drawable.sgreenthumb,
                contentDescription = "Ikon: Grønn tommel opp",
                title = "Anbefalte kiteforhold",
                info ="7<11 m/s. Riktig vindstyrke\nog vindretning."
            )
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            KiteConditionColorBox(
                icon = R.drawable.syellowthumb,
                contentDescription = "Ikon: Gul tommel opp",
                title = "Vanskelige kiteforhold",
                info = "11<15 m/s. Sterk vind\nFare for overrigging"
            )
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            KiteConditionColorBox(
                icon = R.drawable.sorangethumb,
                contentDescription = "Ikon: Oransje tommel ned",
                title = "Ingen kiteforhold",
                info = "15<19 m/s\nKan være stor fare."
            )
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            KiteConditionColorBox(
                icon = R.drawable.sredthumb,
                contentDescription = "Ikon: Rød tommel ned",
                title = "Ingen kiteforhold",
                info = "19< m/s. Ekstrem fare\nog ekstremvær"
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun KiteConditionColorBox(icon: Int, contentDescription: String, title: String, info: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = contentDescription,
            contentScale = ContentScale.None
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.inter_font)),
                    fontWeight = FontWeight(400),
                    color = TextColor,
                )
            )
            Text(
                text = info,
                style = TextStyle(
                    fontSize = 9.sp,
                    fontFamily = FontFamily(Font(R.font.inter_font)),
                    fontWeight = FontWeight(400),
                    color = TextColor,
                )
            )
        }
    }
}

@Composable
fun SpotBox(spot: Spot, navController: NavController) {
    Box(
        modifier = Modifier
            .widthIn(max = 296.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            //Row with title of spot and "goToSpot" button
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                //SpotName and CityName
                Box(modifier = Modifier
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            text = spot.predefinedSpot.spotName,
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontFamily = FontFamily(Font(R.font.inter_font)),
                                fontWeight = FontWeight(700),
                                color = TextColor,
                                letterSpacing = (-0.05).sp
                            )
                        )
                        Text(
                            text = spot.predefinedSpot.cityName,
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.inter_font)),
                                fontWeight = FontWeight(400),
                                color = TextColor
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))

                //Button: More detailed information about the spot
                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("SpotScreen/${spot.predefinedSpot.coordinates}")
                        },
                    contentAlignment = Alignment.CenterEnd
                ){
                    Image(
                        painter = painterResource(id = R.drawable.gotospot),
                        contentDescription = "Knapp: Detaljert informasjon om kitespot",
                        contentScale = ContentScale.Fit
                    )
                }
            }

            // Picture of spot
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                val painter = painterResource(id = spot.predefinedSpot.spotImage)
                Image(
                    painter = painter,
                    contentDescription = "Bilde: Strand",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(96.dp)
                        .clip(shape = RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            //Box with condition for kiting (including thumb, color, wind info)
            Box {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = "Akkurat nå:",
                        style = TextStyle(
                            fontSize = 9.sp,
                            fontFamily = FontFamily(Font(R.font.inter_font)),
                            fontWeight = FontWeight(500),
                            color = TextColor
                        )
                    )

                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        //ConditonCircle with thumb
                        Box {
                            Image(
                                painter = painterResource(id = spot.spotDetails[0].kiteRecommendationBigThumb),
                                contentDescription = "Ikon: Tommel for kiteanbefaling. Farge: ${spot.spotDetails[0].kiteRecommendationColorString}",
                                contentScale = ContentScale.None
                            )
                        }

                        Column(
                            verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
                            horizontalAlignment = Alignment.End,
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
                                verticalAlignment = Alignment.Bottom,
                            ) {
                                Text(
                                    text = "${spot.spotDetails[0].windSpeedValue}",
                                    style = TextStyle(
                                        fontSize = 24.sp,
                                        fontFamily = FontFamily(Font(R.font.inter_font)),
                                        fontWeight = FontWeight(300),
                                        color = TextColor
                                    )
                                )
                                Text(
                                    text = "m/s",
                                    style = TextStyle(
                                        fontSize = 24.sp,
                                        fontFamily = FontFamily(Font(R.font.inter_font)),
                                        fontWeight = FontWeight(400),
                                        color = TextColor
                                    )
                                )
                            }
                            Text(
                                text = "vindstyrke",
                                style = TextStyle(
                                    fontSize = 9.sp,
                                    fontFamily = FontFamily(Font(R.font.inter_font)),
                                    fontWeight = FontWeight(400),
                                    color = TextColor
                                )
                            )
                        }

                        //Column with wind direction
                        Column(
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.End,
                        ) {
                            Row (
                                horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.Start)
                            ){
                                Image(
                                    painter = painterResource(id = spot.spotDetails[0].windDirectionIcon),
                                    contentDescription = "Ikon: Pil som viser vindretning, ${spot.spotDetails[0].windDirectionString}",
                                    contentScale = ContentScale.None,
                                    modifier = Modifier.size(32.dp)
                                )
                                Text(
                                    text = "${spot.spotDetails[0].windDirectionString}",
                                    style = TextStyle(
                                        fontSize = 24.sp,
                                        fontFamily = FontFamily(Font(R.font.inter_font)),
                                        fontWeight = FontWeight(400),
                                        color = TextColor
                                    )
                                )
                            }
                            Text(
                                text = "vindretning",
                                style = TextStyle(
                                    fontSize = 9.sp,
                                    fontFamily = FontFamily(Font(R.font.inter_font)),
                                    fontWeight = FontWeight(400),
                                    color = TextColor
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SpotBoxForSpotScreen(spot: Spot) {
    Box {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            //Row with title of spot and "goToSpot" button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        //SpotName
                        Text(
                            text = spot.predefinedSpot.spotName,
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontFamily = FontFamily(Font(R.font.inter_font)),
                                fontWeight = FontWeight(700),
                                color = TextColor,
                                letterSpacing = (-0.05).sp
                            )
                        )
                        //CityName
                        Text(
                            text = spot.predefinedSpot.cityName,
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.inter_font)),
                                fontWeight = FontWeight(400),
                                color = TextColor
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Box {
                    Image(
                        painter = painterResource(id = R.drawable.setfavourite),
                        contentDescription = "Knapp: Sett som favoritt",
                        contentScale = ContentScale.Fit
                    )
                }
            }
            //Box with condition for kiting (including thumb, color, wind info)
            Box {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Akkurat nå:",
                        style = TextStyle(
                            fontSize = 9.sp,
                            fontFamily = FontFamily(Font(R.font.inter_font)),
                            fontWeight = FontWeight(500),
                            color = TextColor
                        )
                    )
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        //ConditonCircle with thumb
                        Box {
                            Image(
                                painter = painterResource(id = spot.spotDetails[0].kiteRecommendationBigThumb),
                                contentDescription = "Ikon: Tommel for kiteanbefaling. Farge: ${spot.spotDetails[0].kiteRecommendationColorString}",
                                contentScale = ContentScale.None
                            )
                        }
                        Column(
                            verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
                            horizontalAlignment = Alignment.End,
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
                                verticalAlignment = Alignment.Bottom,
                            ) {
                                Text(
                                    text = "${spot.spotDetails[0].windSpeedValue}",
                                    style = TextStyle(
                                        fontSize = 24.sp,
                                        fontFamily = FontFamily(Font(R.font.inter_font)),
                                        fontWeight = FontWeight(300),
                                        color = TextColor
                                    )
                                )
                                Text(
                                    text = "m/s",
                                    style = TextStyle(
                                        fontSize = 24.sp,
                                        fontFamily = FontFamily(Font(R.font.inter_font)),
                                        fontWeight = FontWeight(400),
                                        color = TextColor
                                    )
                                )
                            }
                            Text(
                                text = "vindstyrke",
                                style = TextStyle(
                                    fontSize = 9.sp,
                                    fontFamily = FontFamily(Font(R.font.inter_font)),
                                    fontWeight = FontWeight(400),
                                    color = TextColor
                                )
                            )
                        }

                        //Column with wind direction
                        Column(
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.End,
                        ) {
                            Row (horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.Start)){
                                Image(
                                    painter = painterResource(id = spot.spotDetails[0].windDirectionIcon),
                                    contentDescription = "Ikon: Pil som viser vindretning, ${spot.spotDetails[0].windDirectionString}",
                                    contentScale = ContentScale.None,
                                    modifier = Modifier.size(32.dp)
                                )
                                Text(
                                    text = "${spot.spotDetails[0].windDirectionString}",
                                    style = TextStyle(
                                        fontSize = 24.sp,
                                        fontFamily = FontFamily(Font(R.font.inter_font)),
                                        fontWeight = FontWeight(400),
                                        color = TextColor
                                    )
                                )
                            }
                            Text(
                                text = "vindretning",
                                style = TextStyle(
                                    fontSize = 9.sp,
                                    fontFamily = FontFamily(Font(R.font.inter_font)),
                                    fontWeight = FontWeight(400),
                                    color = TextColor
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

//Takes a warning message from Spot, and changes background color accordingly
@Composable
fun WarningBox (
    spot: Spot
) {
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(spot.alerts) { alert ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 120.dp)
                    .background(
                        color = alert?.riskMatrixColor ?: White,
                        shape = RoundedCornerShape(size = StandardRadius)
                    )
                    .padding(StandardRadius)
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 4.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Farevarsel!",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.inter_font)),
                            fontWeight = FontWeight(600),
                            color = TextColor,
                            letterSpacing = (-0.05).sp
                        )
                    )
                    LazyColumn {
                        item {
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = alert?.riskMatrixColor ?: White,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = alert?.description ?: "",
                                    style = TextStyle(
                                        fontSize = 9.sp,
                                        fontFamily = FontFamily(Font(R.font.inter_font)),
                                        fontWeight = FontWeight(400),
                                        color = TextColor,
                                    ),
                                    modifier = Modifier.widthIn(max = 120.dp) // Limiting width to allow text wrapping
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TimeBox(details: SpotInfo) {
    Box (
        modifier = Modifier
            .border(
                width = 4.dp,
                color = details.kiteRecommendationColor,
                shape = RoundedCornerShape(size = StandardRadius)
            )
            .width(64.dp)
            .height(72.dp)
            .background(
                color = White,
                shape = RoundedCornerShape(size = StandardRadius)
            )
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                //Timestamp
                text = details.time,
                style = TextStyle(
                    fontSize = 9.sp,
                    fontFamily = FontFamily(Font(R.font.inter_font)),
                    fontWeight = FontWeight(400),
                    color = TextColor,
                )
            )
            Text(
                //Wind speed
                text = "${details.windSpeedValue} m/s",
                style = TextStyle(
                    fontSize = 9.sp,
                    fontFamily = FontFamily(Font(R.font.inter_font)),
                    fontWeight = FontWeight(400),
                    color = TextColor,
                    )
            )
            Text(
                //Wind direction
                text = "${details.windDirectionString}",
                style = TextStyle(
                    fontSize = 9.sp,
                    fontFamily = FontFamily(Font(R.font.inter_font)),
                    fontWeight = FontWeight(400),
                    color = TextColor,
                )
            )
        }
    }
}

@Composable
fun DaysBoxRow(details: List<SpotInfo>){
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        //Date
        Text(
            text = details[0].date,
            style = TextStyle(
                fontSize = 9.sp,
                fontFamily = FontFamily(Font(R.font.inter_font)),
                fontWeight = FontWeight(400),
                color = TextColor
            )
        )
        LazyRow {
            items(details) { detail ->
                TimeBox(detail)
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Composable
fun ButtonRow(navController : NavController){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box (
            Modifier.clickable {
                navController.popBackStack()},
            contentAlignment = Alignment.CenterStart
        ){
            Image(
                painter = painterResource(id = R.drawable.gotomap),
                contentDescription = "Knapp: Gå til kart",
                contentScale = ContentScale.Fit
            )
        }
        Box (
            contentAlignment = Alignment.CenterEnd
        ){
            Image(
                painter = painterResource(id = R.drawable.infospotbutton),
                contentDescription = "Knapp: Mer info om kitespot.",
                contentScale = ContentScale.Fit
            )
        }
    }
}

//SettingsScreen text
@Composable
fun SettingsScreenText() {
    Column(
        modifier = Modifier
    ) {
        Text(
            text = "Innstillinger",
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.inter_font)),
                fontWeight = FontWeight(600),
                color = TextColor,
            )
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                text = "Skru på push-varsler\nfor favorittsted",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily(Font(R.font.inter_font)),
                    fontWeight = FontWeight(400),
                    color = TextColor,
                    letterSpacing = 0.5.sp,
                )
            )
            SwitchSettings()
        }
    }
}

//Favourite Screen text
@Composable
fun FavouriteScreenText() {
    Column(
        modifier = Modifier
    ) {
        Text(
            text = "Favoritter",
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.inter_font)),
                fontWeight = FontWeight(600),
                color = TextColor,
            )
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            Text(
                text = "Her kan du legge til\nditt favorittsted.\nTrykk på knappen\netter at du har valgt\nstedet på kartet.",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily(Font(R.font.inter_font)),
                    fontWeight = FontWeight(400),
                    color = TextColor,
                    letterSpacing = 0.5.sp,
                )
            )
            Spacer(modifier = Modifier.width(24.dp))
            Image(
                painter = painterResource(R.drawable.setfavourite),
                contentDescription = "Knapp: Sett som favoritt",
                contentScale = ContentScale.Fit
            )
        }
    }
}

//Switch to turn on or of push notifications
@Composable
fun SwitchSettings() {
    var checked by remember { mutableStateOf(true) }

    Switch(
        checked = checked,
        onCheckedChange = {
            checked = it
        },
        colors = SwitchDefaults.colors (
            checkedThumbColor = TextColor,
            checkedTrackColor = ActionBlue,
            uncheckedThumbColor = White,
            uncheckedTrackColor = LightGrayCircle,
        )
    )
}

@Composable
fun SpotBoxWithFrame(spot: Spot, navController: NavController){
    Column(modifier = Modifier
        .width(296.dp)
        .background(White, shape = RoundedCornerShape(size = StandardRadius))
        .padding(StandardRadius)
    ) {
        if (spot.alerts.isNotEmpty()) {
            WarningBox(spot)
            Spacer(modifier = Modifier.height(12.dp))
        }
        SpotBox(spot, navController)
    }
}

/*
//Previews
@Preview
@Composable
fun SwitchSettingsPreview() {
    SwitchSettings()
}

@Preview
@Composable
fun KiteConditionColorBoxPreview() {
  KiteConditionColorBox(R.drawable.sgreenthumb, "Hei", "dette er en prove\nharflere linjer\nggjeoheoiio")
}
*/


/*@Preview
@Composable
fun SpotBoxPreview(){
    SpotBox( )
}*/

/*
@Preview
@Composable
fun SettingsScreenTextPreview (){
    SettingsScreenText()
}*/

/*@Preview
@Composable
fun WarningBoxWithFramePreview () {
    WarningBox()
}

@Preview
@Composable
fun TimeBoxPreview(){
    TimeBox(spot)
}

@Preview
@Composable
fun DaysBoxRowPreview(){
    DaysBoxRow(spot)
}

@Preview
@Composable
fun SpotBoxForSpotScreenPreview() {
    SpotBoxForSpotScreen()
}
*/
//@Preview
//@Composable
//fun ButtonRowPreview(){
//    ButtonRow()
//}