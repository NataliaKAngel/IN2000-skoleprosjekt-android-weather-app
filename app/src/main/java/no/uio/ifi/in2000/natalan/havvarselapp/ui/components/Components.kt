package no.uio.ifi.in2000.natalan.havvarselapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
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
import no.uio.ifi.in2000.natalan.havvarselapp.ui.theme.*
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
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
            .padding(StandardRadius)
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
            .padding(StandardRadius)
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
            //.padding(top = 0.dp, start = 16.dp)
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
                    fontSize = 16.sp,
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
                    fontSize = 12.sp,
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
                    //.padding(16.dp)
                    .height(360.dp)
                )
                {
                item {
                    KiteConditionColorBox(
                        icon = R.drawable.sgreythumb,
                        "Ingen kiteforhold",
                        "0-5 m/s og/eller feil\n vindretning. Umulig å kite."
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    KiteConditionColorBox(
                        icon = R.drawable.sbluethumb,
                        "Middels kiteforhold",
                        "5<7 m/s og riktig vindretning.\nBør ha større kite."
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    KiteConditionColorBox(
                        icon = R.drawable.sgreenthumb,
                        "Anbefalte kiteforhold",
                        "7<11 m/s. Riktig vindstyrke\nog vindretning."
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    KiteConditionColorBox(
                        icon = R.drawable.syellowthumb,
                        "Vanskelige kiteforhold",
                        "11<15 m/s. Sterk vind\nFare for overrigging"
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    KiteConditionColorBox(
                        icon = R.drawable.sorangethumb,
                        "Ingen kiteforhold",
                        "15<19 m/s\nKan være stor fare."
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    KiteConditionColorBox(
                        icon = R.drawable.sredthumb,
                        "Ingen kiteforhold",
                        "19< m/s. Ekstrem fare\nog ekstremvær"
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

        }

@Composable
fun KiteConditionColorBox(icon: Int, title: String, info: String) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "image description",
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


//Currently working on:
//SpotBox that pops up on HomeScreen when a marker with spot is clicked
//Shows relevant information from that spot
@Composable
fun SpotBox() {
    Box(
        modifier = Modifier
            .widthIn(max = 254.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start,
        ) {
            //Row with title of spot and "goToSpot" button
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            text = "Hamresanden",
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontFamily = FontFamily(Font(R.font.inter_font)),
                                fontWeight = FontWeight(700),
                                color = TextColor,
                                letterSpacing = (-0.05).sp
                            )
                        )
                        Text(
                            text = "Kristiansand",
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
                        painter = painterResource(id = R.drawable.gotospot),
                        contentDescription = "go to spot",
                        contentScale = ContentScale.FillBounds
                    )
                }
            }

            // Picture of spot
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.hamresanden),
                    contentDescription = null,
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
                    verticalArrangement = Arrangement.SpaceBetween,
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
                        Box(
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.bgreenthumb),
                                contentDescription = "color thumb",
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
                                    text = "8",
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
                                    painter = painterResource(id = R.drawable.arrow_southwest),
                                    contentDescription = "arrow shows wind direction",
                                    contentScale = ContentScale.None,
                                    modifier = Modifier.size(32.dp)
                                )
                                Text(
                                    text = "sørvest",
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







//Bottom Sheet to HomeScreen
//Includes NavBar
// and SpotBox when a spot is clicked
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpotBottomSheet()
{
    Surface {
        ModalBottomSheet(onDismissRequest = { /*TODO*/ }
        ) {
            SpotBox()
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

@Preview
@Composable
fun SpotBoxWithFrame(){
    Box(modifier = Modifier

        .background(White, shape = RoundedCornerShape(size = StandardRadius))
        .padding(StandardRadius)) {
        SpotBox()
    }
}

/*
@Preview
@Composable
fun SettingsScreenTextPreview (){
    SettingsScreenText()
}*/
