package me.kyuubiran.qqcleaner.ui.view

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties

@Composable
fun Dialog(
    @StringRes title: Int,
    @StringRes text: Int,
    @StringRes confirmBtnTitle: Int? = null,
    showable: MutableState<Boolean> = remember { mutableStateOf(true) },
    confirm: ((MutableState<Boolean>) -> Unit)? = null,
    @StringRes dismissBtnString: Int? = null,
    dismiss: ((MutableState<Boolean>) -> Unit)? = null,
    dismissRequest: ((MutableState<Boolean>) -> Unit)? = null,
    clickToDismiss: Boolean = true,
    properties: DialogProperties = DialogProperties()
) {
    Dialog(
        title = stringResource(id = title),
        content = {
            Surface(modifier = Modifier.wrapContentSize()) {
                Text(text = stringResource(id = text), fontSize = 16.sp)
            }
        },
        showable = showable,
        confirmBtnTitle = if (confirmBtnTitle != null) stringResource(id = confirmBtnTitle) else null,
        confirm = confirm,
        dismissBtnString = if (dismissBtnString != null) stringResource(id = dismissBtnString) else null,
        dismiss = dismiss,
        dismissRequest = dismissRequest,
        clickToDismiss = clickToDismiss,
        properties = properties
    )
}

@Composable
fun Dialog(
    title: String,
    text: String,
    @StringRes confirmBtnTitle: Int? = null,
    showable: MutableState<Boolean> = remember { mutableStateOf(true) },
    confirm: ((MutableState<Boolean>) -> Unit)? = null,
    @StringRes dismissBtnString: Int? = null,
    dismiss: ((MutableState<Boolean>) -> Unit)? = null,
    dismissRequest: ((MutableState<Boolean>) -> Unit)? = null,
    clickToDismiss: Boolean = true,
    properties: DialogProperties = DialogProperties()
) {
    Dialog(
        title = title,
        content = { Text(text = text) },
        showable = showable,
        confirmBtnTitle = if (confirmBtnTitle != null) stringResource(id = confirmBtnTitle) else null,
        confirm = confirm,
        dismissBtnString = if (dismissBtnString != null) stringResource(id = dismissBtnString) else null,
        dismiss = dismiss,
        dismissRequest = dismissRequest,
        clickToDismiss = clickToDismiss,
        properties = properties
    )
}

@Composable
fun Dialog(
    title: String,
    content: @Composable (() -> Unit)? = null,
    showable: MutableState<Boolean> = remember { mutableStateOf(true) },
    confirmBtnTitle: String? = null,
    confirm: ((MutableState<Boolean>) -> Unit)? = null,
    dismissBtnString: String? = null,
    dismiss: ((MutableState<Boolean>) -> Unit)? = null,
    dismissRequest: ((MutableState<Boolean>) -> Unit)? = null,
    clickToDismiss: Boolean = true,
    properties: DialogProperties = DialogProperties()
) {
    if (showable.value) {
        AlertDialog(
            modifier = Modifier.wrapContentHeight(),
            onDismissRequest = {
                dismissRequest?.invoke(showable)
            },
            title = {
                Surface {
                    Text(
                        text = title,
                        fontSize = 22.sp
                    )
                }
            },
            text = { content?.invoke() },
            confirmButton = {
                confirmBtnTitle?.let {
                    Button(
                        onClick = {
                            confirm?.invoke(showable)
                            if (clickToDismiss) showable.value = false
                        }) {
                        Text(text = it)
                    }
                }
            },
            dismissButton = {
                dismissBtnString?.let {
                    Button(
                        onClick = {
                            dismiss?.invoke(showable)
                            if (clickToDismiss) showable.value = false
                        }) {
                        Text(text = it)
                    }
                }
            }, properties = properties
        )
    }
}
