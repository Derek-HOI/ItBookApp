package com.github.itbookapp.presentation.view.annotation

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Light Mode",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = 0xFF0A0A0A
)
annotation class CustomPreview

@Preview(
    name = "Light Mode",
    showBackground = true,
    backgroundColor = 0xFF0E1215
)
@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
annotation class CustomDialogPreview

@Preview(name = "Full Preview", showSystemUi = true)
annotation class PreviewWithSystemUi
