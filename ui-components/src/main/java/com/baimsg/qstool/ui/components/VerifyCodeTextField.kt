package com.baimsg.qstool.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.baimsg.qstool.ui.theme.QstoolComposeThem

/**
 * Create by Baimsg on 2023/4/6
 *
 **/
@Composable
fun VerifyCodeTextField(
    modifier: Modifier,
    value: String,
    hint: String,
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    TextField(
        modifier = modifier,
        placeholder = {
            Text(text = hint, color = QstoolComposeThem.colors.textSecondary)
        },
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
        ),
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            textColor = QstoolComposeThem.colors.textPrimary,
            cursorColor = QstoolComposeThem.colors.iconCurrent,
            focusedIndicatorColor = QstoolComposeThem.colors.iconCurrent,
            trailingIconColor = QstoolComposeThem.colors.textPrimary.copy(0.5f),
            backgroundColor = QstoolComposeThem.colors.iconCurrent.copy(alpha = 0.1f)
        )
    )
}