package com.github.itbookapp.presentation.view.detail

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.github.itbookapp.data.model.Books
import com.github.itbookapp.data.model.onError
import com.github.itbookapp.data.model.onSuccess
import com.github.itbookapp.ext.SetEvents
import com.github.itbookapp.ext.noRippleClickable
import com.github.itbookapp.presentation.viewmodel.BookViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BookDetailScreen(
    isbn13: String,
    bookViewModel: BookViewModel = hiltViewModel()
) {

    var books: Books? by remember { mutableStateOf(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(model = books?.image, contentDescription = null)
        TextItem(title = "Title", content = books?.title)
        TextItem(title = "Subtitle", content = books?.subtitle)
        TextItem(title = "Description", content = books?.desc)
        TextItem(title = "Authors", content = books?.authors)
        TextItem(title = "Publisher", content = books?.publisher)
        TextItem(title = "Rating", content = books?.rating)
        TextItem(title = "Pages", content = books?.pages)
        TextItem(title = "Price", content = books?.price)
        TextItem(title = "Year", content = books?.year)
        TextItem(title = "Url", content = books?.url, isUrl = true)
        TextItem(title = "ISBN10", content = books?.isbn10)
        TextItem(title = "ISBN13", content = books?.isbn13)
        PdfItem(pdf = books?.pdf)
    }

    val context = LocalContext.current
    LaunchedEffect(key1 = Unit, block = {
        launch {
            bookViewModel.eventFlow.collectLatest {
                if (it is BookViewModel.BookEvent.GetBooks) {
                    it.result.onSuccess { response ->
                        books = response
                    }.onError {
                        Toast.makeText(context, "책 정보를 불러오는데 실패하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    })
    LocalLifecycleOwner.current.SetEvents(listener = { event ->
        if (event == Lifecycle.Event.ON_START) {
            bookViewModel.getBooks(isbn13)
        }
    })
}

@Composable
private fun TextItem(title: String?, content: String?, isUrl: Boolean = false) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = title ?: "",
            style = TextStyle(
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight(550)
            )
        )
        val context = LocalContext.current
        Text(
            text = buildAnnotatedString {
                if (isUrl) {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Blue,
                            fontStyle = FontStyle.Italic,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append(content ?: "")
                    }
                } else {
                    append(content ?: "")
                }
            },
            modifier = Modifier
                .weight(1.0F)
                .noRippleClickable {
                    if (isUrl) {
                        context.startActivity(
                            Intent(Intent.ACTION_VIEW, Uri.parse(content))
                        )
                    }
                },
            style = TextStyle(
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight(400)
            )
        )
    }
}

@Composable
private fun PdfItem(pdf: Map<String, String>?) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "PDF",
            style = TextStyle(
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight(550)
            )
        )
        pdf?.entries?.forEach {
            TextItem(title = it.key, content = it.value, isUrl = true)
        }
    }
}
