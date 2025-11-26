package com.example.artspaceapp

import android.os.Bundle
import android.provider.CalendarContract
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.Rgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspaceapp.ui.theme.ArtSpaceAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceAppTheme { ArtSpaceApp() }

        }
    }
}


@Composable
fun ArtSpaceApp(modifier: Modifier = Modifier) {
    var index by remember { mutableStateOf(0) }


    val images = listOf(
        R.drawable.xmas_9214261_1280,
        R.drawable.ant_9904052_1280,
        R.drawable.facade_9641925_1280
    )
    val titles = listOf(R.string.xmas_title, R.string.ant_title, R.string.facade_title)
    val artists = listOf(R.string.xmas_artist, R.string.ant_artist, R.string.facade_artist)
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
            .padding(40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        ArtSpaceImageAndText(
            picture = images[index],
            artist = artists[index],
            title = titles[index],
            modifier
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ImagesNavigation(
                buttonText = R.string.button_previous,
                onClick = { if (index == 0) index = (images.size - 1) else index-- })
            ImagesNavigation(
                buttonText = R.string.button_next,
                onClick = { if (index == images.size - 1) index = 0 else index++ })
        }


    }
}


@Composable
fun ArtSpaceImageAndText(
    @DrawableRes picture: Int,
    @StringRes artist: Int,
    @StringRes title: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(color = Color.White)
            .wrapContentSize()
            .shadow(elevation = 60.dp)

    ) {
        Image(
            painter = painterResource(picture),
            contentDescription = null,
            contentScale = ContentScale.Inside,
            modifier = modifier.padding(20.dp)
        )
    }
    Spacer(Modifier.height(100.dp))
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White),
    ) {
        Text(
            text = stringResource(title),
            fontSize = 20.sp,
            fontWeight = FontWeight.Light,
            modifier = modifier.padding(7.dp)
        )
        Text(
            text = stringResource(artist),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.padding(7.dp)
        )
    }

}

@Composable
fun ImagesNavigation(
    @StringRes buttonText: Int,
    onClick: () -> Unit
) {
    Button(onClick = onClick) {
        Text(text = stringResource(buttonText))
    }
}


@Preview(showBackground = true)
@Composable
fun ArtSpacePreview() {
    ArtSpaceAppTheme {
        ArtSpaceApp()
    }
}