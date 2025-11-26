package com.example.artspaceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspaceapp.data.MyImage
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

    val images = listOf(
        MyImage(
            imageRes =
                R.drawable.xmas_9214261_1280,
            titleRes = R.string.xmas_title,
            artistRes = R.string.xmas_artist,
        ),
        MyImage(
            imageRes =
                R.drawable.ant_9904052_1280,
            titleRes = R.string.ant_title,
            artistRes = R.string.ant_artist,
        ),
        MyImage(
            imageRes =
                R.drawable.facade_9641925_1280,
            titleRes = R.string.facade_title,
            artistRes = R.string.facade_artist,
        )

    )

    var index by remember { mutableStateOf(0) }



    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(1f))

        ArtSpaceImageAndText(
            images[index],
            modifier.padding(bottom = 20.dp)
        )

        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
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
    myImage: MyImage,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .background(color = Color.White)
                .wrapContentSize()
                .shadow(elevation = 60.dp)

        ) {
            Image(
                painter = painterResource(myImage.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Inside,
                modifier = Modifier.padding(20.dp)
            )
        }
        Spacer(Modifier.height(100.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White),
        ) {
            Text(
                text = stringResource(myImage.titleRes),
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(7.dp)
            )
            Text(
                text = stringResource(myImage.artistRes),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(7.dp)
            )
        }
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