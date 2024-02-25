package com.github.itbookapp.presentation.view.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.github.itbookapp.R
import com.github.itbookapp.ext.noRippleClickable
import com.github.itbookapp.presentation.view.annotation.CustomPreview
import com.github.itbookapp.ui.theme.DodgerBlue
import com.github.itbookapp.ui.theme.DodgerBlue100
import com.github.itbookapp.ui.theme.Grey
import com.github.itbookapp.ui.theme.Grey50
import kotlinx.coroutines.delay

@Composable
fun CommonTextField(
    paddingValues: PaddingValues = PaddingValues(0.dp),
    inputText: String,
    hint: String,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
    focusRequester: FocusRequester = remember { FocusRequester() },
    keyboardActions: KeyboardActions = KeyboardActions(),
    requestFocusOnStart: Boolean = true,
    doAfterTextChanged: (text: String) -> Unit
) {
    val innerMargin = 16.dp

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(paddingValues = paddingValues)
    ) {
        var focused by remember { mutableStateOf(false) }

        val (bg, textField, clsBtn) = createRefs()

        Box(
            modifier = Modifier
                .constrainAs(bg) {
                    linkTo(start = parent.start, end = parent.end)
                    linkTo(top = parent.top, bottom = parent.bottom)

                    height = Dimension.value(50.dp)
                    width = Dimension.fillToConstraints
                }
                .background(
                    color = if (focused) DodgerBlue100 else Grey50,
                    shape = RoundedCornerShape(5.dp)
                )
                .border(
                    width = 1.dp,
                    color = if (focused) DodgerBlue else Color.Transparent,
                    shape = RoundedCornerShape(5.dp)
                )
        )
        var textFieldValueState by remember {
            mutableStateOf(
                TextFieldValue(
                    text = inputText.toString(),
                    selection = TextRange(inputText.length)
                )
            )
        }
        if (textFieldValueState.text != inputText.toString()) {
            textFieldValueState = TextFieldValue(
                text = inputText.toString(),
                selection = TextRange(inputText.length)
            )
        }
        // input text field
        BasicTextField(
            value = textFieldValueState,
            onValueChange = { textValue ->
                textFieldValueState = textValue
                doAfterTextChanged(textValue.text)
            },
            maxLines = 1,
            modifier = Modifier
                .constrainAs(textField) {
                    linkTo(
                        start = bg.start,
                        end = if (inputText.isNotEmpty()) clsBtn.start else parent.end,
                        startMargin = innerMargin,
                        endMargin = if (inputText.isNotEmpty()) 0.dp else innerMargin
                    )
                    linkTo(
                        top = bg.top,
                        bottom = bg.bottom,
                        topMargin = 14.dp,
                        bottomMargin = 14.dp
                    )

                    width = Dimension.fillToConstraints
                }
                .focusRequester(focusRequester)
                .onFocusChanged {
                    focused = it.isFocused
                    if (focused) {
                        textFieldValueState.copy(selection = TextRange(inputText.length))
                    }
                },
            enabled = enabled,
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight(400)
            ),
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            singleLine = true,
            cursorBrush = SolidColor(Grey),
            decorationBox = { innerTextField ->
                if (inputText.isEmpty())
                    Text(
                        text = hint,
                        maxLines = 1,
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontWeight = FontWeight(400)
                        )
                    )
                innerTextField()
            }
        )
        LaunchedEffect(key1 = Unit) {
            if (requestFocusOnStart) {
                delay(100L)
                focusRequester.requestFocus()
            }
        }
        // clear button
        if (enabled) {
            AnimatedVisibility(
                visible = inputText.isNotEmpty(),
                modifier = Modifier
                    .constrainAs(clsBtn) {
                        end.linkTo(bg.end, innerMargin)
                        top.linkTo(bg.top)
                        bottom.linkTo(bg.bottom)
                    },
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_20_delete_word),
                    contentDescription = "",
                    modifier = Modifier
                        .noRippleClickable {
                            textFieldValueState =
                                textFieldValueState.copy(text = "", selection = TextRange.Zero)
                            doAfterTextChanged("")
                        }
                )
            }
        }
    }
}

@CustomPreview
@Composable
private fun CommonTextFieldPreview() {
    var input by remember { mutableStateOf("ale") }
    CommonTextField(
        inputText = input,
        hint = "검색어를 입력하세요."
    ) {
        input = it
    }
}
