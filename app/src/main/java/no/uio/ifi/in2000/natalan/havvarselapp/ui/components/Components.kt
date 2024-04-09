package no.uio.ifi.in2000.natalan.havvarselapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
    val BtnActive: Color = Color(0xFF96CFF5)
    val StandardRadius: Dp = 16.dp
}

object whiteBoxNavbar {
    val White: Color = Color(0xFFFFFFFF)
    val StandardRadius: Dp = 16.dp
}

object blueNotClicked {
    val BtnDefault: Color = Color(0xFFD5ECFB)
    val StandardRadius: Dp = 16.dp
}

class Components {
    @Composable
    fun TopBar() {
        Box(
            modifier = Modifier
                .width(268.76471.dp)
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
                infoButton()
            }
        }
    }
    @Composable
    fun infoButton(){
        Box(
            modifier = Modifier
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
    fun navButton(text: String, icon: Int) { // Endret parameterne til tekst og ikon
        Box(
            Modifier
                .width(88.dp)
                .height(72.dp)
                .background(
                    color = blueBoxMap.BtnActive,
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
    fun navBar(){
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
                navButton(text = "Kart", icon = R.drawable.map)
                navButton(text = "Favoritter", icon = R.drawable.favourite)
                navButton(text = "Instillinger", icon = R.drawable.settings)
            }

        }
    }
}


@Preview
@Composable
fun PreviewInfo() {
    val components = Components()
    components.navBar()
}
