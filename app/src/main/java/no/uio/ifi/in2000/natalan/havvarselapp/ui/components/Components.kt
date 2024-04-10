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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import no.uio.ifi.in2000.natalan.havvarselapp.R


object BoxInfo {
    val BtnDefault: Color = Color(0xFFD5ECFB)
}

object InfoText {
    val Brand: Color = Color(0xFF3E6BF6)
}

object whiteBox {
    val White: Color = Color(0xFFFFFFFF)
    val StandardRadius: Dp = 16.dp
}

object blueBoxMap {
    val color: Color = Color(0xFF96CFF5)
    val StandardRadius: Dp = 16.dp
}

object whiteBoxNavbar {
    val White: Color = Color(0xFFFFFFFF)
    val StandardRadius: Dp = 16.dp
}

object blueNotClicked {
    val color: Color = Color(0xFFD5ECFB)
    val StandardRadius: Dp = 16.dp
}

object textColor {
    val textColor: Color = Color(0xFF05134D)
}
object redCircle {
    val color: Color = Color(0xFFF7524B)
}
object orangeCircle {
    val color: Color = Color(0xFFF7934B)
}
object yellowCircle {
    val color: Color = Color(0xFFFFC42C)
}

object greenCircle {
    val color: Color = Color(0xFF00B680)
}

object blueCircle {
    val color: Color = Color(0xFF0991DD)
}
object greyCircle {
    val color: Color = Color(0xFFB1B3B4)
}
class Components {
    @Composable
    fun TopBar(infoButtonClick: () -> Unit) {
        Box(
            modifier = Modifier
                .width(328.dp)
                .height(76.dp)
                .background(
                    color = whiteBox.White,
                    shape = RoundedCornerShape(size = whiteBox.StandardRadius)
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
                infoButton(onClick = infoButtonClick)
            }
        }
    }
    @Composable
    fun infoButton(onClick: () -> Unit){
        Box(
            modifier = Modifier
                .clickable(onClick = onClick)
                .width(65.dp)
                .height(44.dp)
                .background(
                    color = BoxInfo.BtnDefault,
                    shape = RoundedCornerShape(size = 12.dp)
                )
                .padding(start = 12.dp, top = 12.dp, end = 12.dp, bottom = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start)
            ) {
                Text(
                    text = "Info",
                    style = TextStyle(
                        fontSize = 9.sp,
                        fontWeight = FontWeight.W500,
                        color = InfoText.Brand
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
    fun navButton(text: String, icon: Int, onClick: () -> Unit, color: Color) { // Endret parameterne til tekst og ikon
        Box(
            Modifier
                .width(88.dp)
                .height(72.dp)
                .background(
                    color = color,
                    shape = RoundedCornerShape(size = blueBoxMap.StandardRadius)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = icon), // Bruker ikon-parameteren
                    contentDescription = "image description",
                    contentScale = ContentScale.None
                )
                Text(
                    text = text, // Bruker tekst-parameteren
                    style = TextStyle(
                        fontSize = 9.sp,
                        fontFamily = FontFamily(Font(R.font.inter_font)),
                        fontWeight = FontWeight(500),
                        color = InfoText.Brand,
                    )
                )
            }
        }
    }

    @Composable
    fun navBarKart(navButtonClick: (String) -> Unit){ //
        Box(
            Modifier
                .width(328.dp)
                .height(104.dp)
                .background(
                    color = whiteBoxNavbar.White,
                    shape = RoundedCornerShape(size = whiteBoxNavbar.StandardRadius)
                )
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
        ){
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                verticalAlignment = Alignment.Bottom,
            ) {
                navButton(text = "Kart", icon = R.drawable.map, onClick = { navButtonClick.invoke("Kart") }, color = blueBoxMap.color)
                navButton(text = "Favoritter", icon = R.drawable.favourite, onClick = { navButtonClick.invoke("Favoritter") }, color = blueNotClicked.color)
                navButton(text = "Instillinger", icon = R.drawable.settings, onClick = { navButtonClick.invoke("Instillinger") }, color = blueNotClicked.color)
            }
        }
    }
    @Composable
    fun navBarFavourite(navButtonClick: (String) -> Unit){ //
        Box(
            Modifier
                .width(328.dp)
                .height(104.dp)
                .background(
                    color = whiteBoxNavbar.White,
                    shape = RoundedCornerShape(size = whiteBoxNavbar.StandardRadius)
                )
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
        ){
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                verticalAlignment = Alignment.Bottom,
            ) {
                navButton(text = "Kart", icon = R.drawable.map, onClick = { navButtonClick.invoke("Kart") }, color = blueNotClicked.color)
                navButton(text = "Favoritter", icon = R.drawable.favourite, onClick = { navButtonClick.invoke("Favoritter") }, color = blueBoxMap.color)
                navButton(text = "Instillinger", icon = R.drawable.settings, onClick = { navButtonClick.invoke("Instillinger") }, color = blueNotClicked.color)
            }
        }
    }
    @Composable
    fun navBarInnstillinger(navButtonClick: (String) -> Unit){ //
        Box(
            Modifier
                .width(328.dp)
                .height(104.dp)
                .background(
                    color = whiteBoxNavbar.White,
                    shape = RoundedCornerShape(size = whiteBoxNavbar.StandardRadius)
                )
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
        ){
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                verticalAlignment = Alignment.Bottom,
            ) {
                navButton(text = "Kart", icon = R.drawable.map, onClick = { navButtonClick.invoke("Kart") }, color = blueNotClicked.color)
                navButton(text = "Favoritter", icon = R.drawable.favourite, onClick = { navButtonClick.invoke("Favoritter") }, color = blueNotClicked.color)
                navButton(text = "Instillinger", icon = R.drawable.settings, onClick = { navButtonClick.invoke("Instillinger") }, color = blueBoxMap.color)
            }
        }
    }

    @Composable
    fun goToMap(onClick: () -> Unit){
        Box(modifier = Modifier
            .clickable(onClick = onClick))
        {
            Image(
                painter = painterResource(id = R.drawable.gotomap),
                contentDescription = "image description",
                contentScale = ContentScale.None
            )
        }
    }
    @Composable
    fun kiteForholdInfoBox(){
        Box(
            Modifier
                .width(328.dp)
                .height(437.dp)
                .background(color = whiteBox.White, shape = RoundedCornerShape(size = whiteBox.StandardRadius))
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
        ){
            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.Top),
                horizontalAlignment = Alignment.Start,
            ) {
                Box(
                    Modifier
                        .width(109.dp)
                        .height(17.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            text = "Kitevarsel",
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontFamily = FontFamily(Font(R.font.inter_font)),
                                fontWeight = FontWeight(700),
                                color = textColor.textColor,
                            )
                        )
                        Text(
                            text = "Informasjon",
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.inter_font)),
                                fontWeight = FontWeight(400),
                                color = textColor.textColor,
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
                Box(
                    Modifier
                    .width(270.dp)
                    .height(29.dp)
                ) {
                    Text(
                        text = "Kitevarsel gir kitere anbefalinger om kiteforhold på utvalgte kitespotter langs kysten av Norge. Anbefalingene er fargekodet slik:",
                        style = TextStyle(
                            fontSize = 9.sp,
                            fontFamily = FontFamily(Font(R.font.inter_font)),
                            fontWeight = FontWeight(400),
                            color = textColor.textColor,
                            letterSpacing = 0.5.sp,
                        )
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))
                Box(
                    Modifier
                        .width(193.dp)
                        .height(314.dp)
                        .padding(top = 12.dp, bottom = 12.dp)
                ){
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        kiteForholdColorBox(greyCircle.color, icon = R.drawable.thumbdown, "Ingen kiteforhold", "0-5 m/s og/eller feil vindretning.\nNesten umulig å kite.")
                        kiteForholdColorBox(blueCircle.color, icon = R.drawable.thumbup, "Middels kiteforhold", "5<7 m/s og riktig vindretning.\nBør ha større kite.")
                        kiteForholdColorBox(greenCircle.color, icon = R.drawable.thumbup, "Anbefalte kiteforhold", "7<11 m/s\nRiktig vindstyrke og vindretning.")
                        kiteForholdColorBox(yellowCircle.color, icon = R.drawable.thumbup, "Utfordrende kiteforhold", "11<15 m/s og riktig vindretning.\nSterk vind. Kan være moderat fare.")
                        kiteForholdColorBox(orangeCircle.color, icon = R.drawable.thumbdown, "Ingen kiteforhold", "15<19 m/s\nKan være stor fare.")
                        kiteForholdColorBox(redCircle.color, icon = R.drawable.thumbdown, "Ingen kiteforhold", "19< m/s\nEkstrem fare og ekstremvær")
                    }
                }
            }
        }
    }
    @Composable
    fun kiteForholdColorBox(color: Color, icon: Int, title: String, info: String) {
        Box(
            Modifier
                .width(181.dp)
                .height(35.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
                verticalAlignment = Alignment.Top,
            ) {
                Box(
                    Modifier
                        .width(32.dp)
                        .height(32.dp)
                        .background(color = color, shape = RoundedCornerShape(size = 20.dp))
                        .padding(start = 8.dp, top = 7.dp, end = 8.dp, bottom = 7.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Image(
                            painter = painterResource(id = icon),
                            contentDescription = "image description",
                            contentScale = ContentScale.None
                        )
                    }
                }
                Box(Modifier
                    .width(137.dp)
                    .height(35.dp)){
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                        horizontalAlignment = Alignment.Start,
                    ) {

                        Text(
                            text = title,
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.inter_font)),
                                fontWeight = FontWeight(400),
                                color = textColor.textColor,
                            )
                        )
                        Text(
                            text = info,
                            style = TextStyle(
                                fontSize = 9.sp,
                                fontFamily = FontFamily(Font(R.font.inter_font)),
                                fontWeight = FontWeight(400),
                                color = textColor.textColor,
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
