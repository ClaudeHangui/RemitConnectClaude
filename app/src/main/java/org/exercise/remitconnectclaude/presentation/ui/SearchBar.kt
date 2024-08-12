package org.exercise.remitconnectclaude.presentation.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.exercise.remitconnectclaude.R
import org.exercise.remitconnectclaude.presentation.theme.outfitFamily

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MySearchBar(
    modifier: Modifier = Modifier,
    hint: String,
    searchQuery: String,
    onQueryChange: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    OutlinedTextField(
        textStyle = TextStyle(
            color = colorResource(id = R.color.dark_blue),
            fontFamily = outfitFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
        ),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            focusedLabelColor = colorResource(id = R.color.border_gray),
            unfocusedContainerColor = Color.Gray.copy(alpha = 0.1f),
            focusedContainerColor = Color.Gray.copy(alpha = 0.1f),
            cursorColor = colorResource(id = R.color.small_label_light_gray),
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .onFocusChanged {
                isHintDisplayed = !it.isFocused
            }
            .focusRequester(focusRequester),
        value = searchQuery,
        singleLine = true,
        onValueChange = {
            onQueryChange.invoke(it)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),

        keyboardActions = KeyboardActions(
            onDone = {
                focusRequester.freeFocus()
                keyboardController?.hide()
            }
        ),

        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_moneco_search),
                contentDescription = null,
            )
        },
        trailingIcon = {},

        placeholder = {
            Text(
                text = hint,
                style = MaterialTheme.typography.titleSmall,
                color = colorResource(id = R.color.small_label_light_gray),
                fontWeight = FontWeight.Normal,
            )
        },
    )

}