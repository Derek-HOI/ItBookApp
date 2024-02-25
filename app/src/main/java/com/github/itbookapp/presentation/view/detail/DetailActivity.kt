package com.github.itbookapp.presentation.view.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.github.itbookapp.data.model.extraIsbn13
import com.github.itbookapp.ui.theme.ItBookAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : ComponentActivity() {

    private val _isbn13 by lazy { intent.getStringExtra(extraIsbn13) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (_isbn13 == null) {
            Toast.makeText(this, "정보를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }

        setContent {
            ItBookAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BookDetailScreen(isbn13 = _isbn13!!)
                }
            }
        }
    }
}
