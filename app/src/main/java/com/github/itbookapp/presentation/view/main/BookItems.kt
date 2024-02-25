package com.github.itbookapp.presentation.view.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideSubcomposition
import com.bumptech.glide.integration.compose.RequestState
import com.github.itbookapp.ext.noRippleClickable
import com.github.itbookapp.presentation.view.annotation.CustomPreview
import com.github.itbookapp.ui.theme.Grey

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ListBookItem(
    image: String?,
    title: String,
    subtitle: String,
    price: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .noRippleClickable(onClick),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideSubcomposition(
            model = image,
            modifier = Modifier.size(50.dp)
        ) {
            when (this.state) {
                RequestState.Loading -> {
                    CircularProgressIndicator()
                }
                RequestState.Failure -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Grey)
                    )
                }
                else -> {
                    Image(painter = this.painter, contentDescription = null)
                }
            }
        }
        Column(
            modifier = Modifier.weight(1.0F),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight(700),
                    color = Color.Black
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = subtitle,
                    modifier = Modifier.weight(1.0F),
                    style = TextStyle(fontSize = 13.sp, color = Color.Black)
                )
                Text(
                    text = price,
                    style = TextStyle(
                        fontSize = 13.sp,
                        color = Color.Black,
                        textAlign = TextAlign.End
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GridBookItem(
    image: String?,
    title: String,
    subtitle: String,
    price: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .noRippleClickable(onClick),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        GlideSubcomposition(
            model = image,
            modifier = Modifier.size(70.dp)
        ) {
            when (this.state) {
                RequestState.Loading -> {
                    CircularProgressIndicator()
                }
                RequestState.Failure -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Grey)
                    )
                }
                else -> {
                    Image(painter = this.painter, contentDescription = null)
                }
            }
        }
        Text(
            text = title,
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight(700),
                color = Color.Black
            )
        )
        Text(
            text = subtitle,
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(fontSize = 13.sp, color = Color.Black)
        )
        Text(
            text = price,
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 13.sp,
                color = Color.Black
            )
        )
    }
}

@CustomPreview
@Composable
private fun BookItemPreview1() {
    ListBookItem(image = null, title = "2222", subtitle = "22222222", price = "$22") {}
}

@CustomPreview
@Composable
private fun BookItemPreview2() {
    GridBookItem(image = null, title = "2222", subtitle = "22222222", price = "$22") {}
}
