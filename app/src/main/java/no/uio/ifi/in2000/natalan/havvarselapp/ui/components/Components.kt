package no.uio.ifi.in2000.natalan.havvarselapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import no.uio.ifi.in2000.natalan.havvarselapp.R
import no.uio.ifi.in2000.natalan.havvarselapp.model.spot.Spot
import no.uio.ifi.in2000.natalan.havvarselapp.ui.theme.*
import coil.compose.AsyncImage
//OBS! Må legge inn avhengighet?

//Standard radius for box corners
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
            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.signature),
                contentDescription = "image description",
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
            .padding(start = 12.dp, top = 12.dp, end = 12.dp, bottom = 12.dp)
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
                contentDescription = "image description",
                contentScale = ContentScale.None
            )
        }
    }
}
@Composable
fun NavButton(navController: NavController, route: String, text: String, icon: Int) { // Parameters: Text and icon
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
                contentDescription = "image description",
                contentScale = ContentScale.None
            )
            Text(
                text = text, // Using the text sent to the component as a parameter
                style = TextStyle(
                    fontSize = 9.sp,
                    fontFamily = FontFamily(Font(R.font.inter_font)),
                    fontWeight = FontWeight(500),
                    color = BlueSignature,
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
            .padding(16.dp)
    ){
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
            verticalAlignment = Alignment.Bottom,
        ) {
            NavButton(navController = navController, route = "HomeScreen", text = "Kart", icon = R.drawable.map)
            NavButton(navController = navController, route = "FavouriteScreen", text = "Favoritter", icon = R.drawable.favourite)
            NavButton(navController = navController, route = "SettingsScreen", text = "Instillinger", icon = R.drawable.settings)
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
            contentDescription = "image description",
            contentScale = ContentScale.None
        )
    }
}
@Composable
fun KiteConditionInfoBox() {
    Column(
        modifier = Modifier
            .background(color = White, shape = RoundedCornerShape(size = StandardRadius))
            .padding(top = 16.dp)
            .fillMaxWidth(),
            // Setter en minimumsbredde på 200dp og en maksimal bredde på 400dp
            //.heightIn(min = 100.dp, max = 424.dp),
        // verticalArrangement = Arrangement.SpaceBetween,
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
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}


            @Composable
            fun InfoColorsColumn(){
                LazyColumn(modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
                    .height(360.dp)
                )
                {
                item {
                    KiteConditionColorBox(
                        LightGrayCircle,
                        icon = R.drawable.thumbdown,
                        "Ingen kiteforhold",
                        "0-5 m/s og/eller feil\n vindretning. Umulig å kite."
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                }
                item {
                    KiteConditionColorBox(
                        BlueCircle,
                        icon = R.drawable.thumbup,
                        "Middels kiteforhold",
                        "5<7 m/s og riktig vindretning.\nBør ha større kite."
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    KiteConditionColorBox(
                        GreenCircle,
                        icon = R.drawable.thumbup,
                        "Anbefalte kiteforhold",
                        "7<11 m/s. Riktig vindstyrke\nog vindretning."
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    KiteConditionColorBox(
                        YellowCircle,
                        icon = R.drawable.thumbup,
                        "Vanskelige kiteforhold",
                        "11<15 m/s. Sterk vind\nFare for overrigging"
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    KiteConditionColorBox(
                        OrangeCircle,
                        icon = R.drawable.thumbdown,
                        "Ingen kiteforhold",
                        "15<19 m/s\nKan være stor fare."
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    KiteConditionColorBox(
                        RedCircle,
                        icon = R.drawable.thumbdown,
                        "Ingen kiteforhold",
                        "19< m/s. Ekstrem fare\nog ekstremvær"
                    )
                    Spacer(modifier = Modifier.height(48.dp))
                }
            }

        }


@Composable
fun KiteConditionColorBox(color: Color, icon: Int, title: String, info: String) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                Modifier
                    .size(32.dp) // Du kan justere størrelsen etter behov
                    .background(color = color, shape = CircleShape)
//                    .width(32.dp)
//                    .height(32.dp)
//                    .background(color = color, shape = CircleShape)
                    .padding(8.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = "image description",
                        contentScale = ContentScale.None
                    )
                }
            }

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
fun SpotBox(navController: NavController, spot: Spot){
    Box(
        modifier = Modifier
            .widthIn(max = 266.dp)
    )
    {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start,
        ) {
            //Row with title of spot and "goToSpot" button
            Row {
                Box(
                    Modifier
//                        .clickable {
                    //navigate to SpotScreen with the chosen spot
//                            navController.navigate("SpotScrren/${spot.id}") },
                ){
                    Image(
                        painter = painterResource(id = R.drawable.gotospot),
                        contentDescription = "go to spot",
                        contentScale = ContentScale.None
                    )
                }
            }

            // Picture of spot
            Box(
                Modifier
                ){
                AsyncImage(
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(RectangleShape),
                    contentScale = ContentScale.Crop,
                    model = spot.photo,
                    contentDescription = "Photo of spot"
                )
                }


            //Box with condition for kiting (including thumb, color, wind info)
            Box(
                Modifier

            ){

                Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start,
            ) {
                //Time right now - can be replaced by actual time
                    Text(
                        text = "Akkurat nå:",
                        style = TextStyle(
                            fontSize = 9.sp,
                            fontFamily = FontFamily(Font(R.font.inter_font)),
                            fontWeight = FontWeight(500),
                            color = BlueSignature,
                        )
                    )
                Row {
                    //ConditonCircle with thumb
                    Box(
                        Modifier
                            .width(50.dp)
                            .height(50.dp)
                            .background(
                                color = GreenCircle,
                                shape = RoundedCornerShape(size = 32.dp)
                            )
                    ){ Image(
                                painter = painterResource(id = ),
                                contentDescription = "image description",
                                contentScale = ContentScale.None
                            )
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                        horizontalAlignment = Alignment.End,
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                            verticalAlignment = Alignment.Bottom,
                        ) {Text(
                            text = "8",
                            style = TextStyle(
                                fontSize = 32.sp,
                                fontFamily = FontFamily(Font(R.font.inter_font)),
                                fontWeight = FontWeight(300),
                                color = TextColor,
                            )
                        )
                            Text(
                                text = "m/s ",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.inter_font)),
                                    fontWeight = FontWeight(400),
                                    color = TextColor,
                                )
                            )
                        }
                        Text(
                            text = "vindstyrke",
                            style = TextStyle(
                                fontSize = 9.sp,
                                fontFamily = FontFamily(Font(R.font.inter_font)),
                                fontWeight = FontWeight(400),
                                color = TextColor,
                            )
                        )


                    }

                    //Coloumn with wind direction
                    Column {
                        Row (
                                    horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
                                    verticalAlignment = Alignment.Bottom
                            ){
                            //Box with wind direction arrow
                                Image(
                                    painter = painterResource(id = R.drawable.arrowSouthwest),
                                    contentDescription = "arrow shows wind direction",
                                    contentScale = ContentScale.None
                                )
                                    Text(
                                        text = "sørvest",
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            fontFamily = FontFamily(Font(R.font.inter_font)),
                                            fontWeight = FontWeight(400),
                                            color = TextColor,

                                            )
                                    )
                            }

                        }
                    Text(
                        text = "vindretning",
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
            }
    }
}





/*@Preview
@Composable
fun PreviewInfo() {
    val components = Components()
    val navButtonClick: (String) -> Unit = { buttonText ->
        // Define actions to be performed when a button is clicked in the preview
        // For example, you can print the clicked button text to the logcat
        println("Clicked button: $buttonText")
    }

    components.navBar(navButtonClick)
}*/

@Preview
@Composable
fun KiteConditionColorBoxPreview() {
  KiteConditionColorBox(GreenCircle, R.drawable.thumbdown, "Hei", "dette er en prove\nharflere linjer\nggjeoheoiio")
}