package me.kyuubiran.qqcleaner.ui.scene

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.kyuubiran.qqcleaner.QQCleanerData
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.composable.Fab
import me.kyuubiran.qqcleaner.ui.composable.TopBar
import me.kyuubiran.qqcleaner.ui.composable.dialog.FileAddDialog
import me.kyuubiran.qqcleaner.ui.composable.dialog.FileDialog
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerShapes
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.FileTextStyle
import me.kyuubiran.qqcleaner.ui.util.Shared

/**
 * 这里是修改类别的 UI
 */
@Composable
fun SortFixScreen(navController: NavController) {
    var fileDialogShow by remember { mutableStateOf(false) }
    if (fileDialogShow) {
        FileDialog {
            fileDialogShow = false
        }
    }

    var fileAddDialogShow by remember { mutableStateOf(false) }
    if (fileAddDialogShow) {
        FileAddDialog {
            fileDialogShow = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colors.pageBackgroundColor)
            .statusBarsPadding()
    ) {
        Column {
            TopBar(
                backClick = {
                    navController.popBackStack()
                },
                iconClick = {

                },
                titleText = Shared.currentEditCleanData.title,
                id = R.drawable.ic_save
            )

            if (Shared.currentEditCleanPathData.pathList.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .padding(horizontal = 24.dp)
                        .background(
                            color = colors.appBarsAndItemBackgroundColor,
                            shape = QQCleanerShapes.cardGroupBackground
                        )
                ) {
                    items(Shared.currentEditCleanPathData.pathList.size) { item ->
                        FileItem(
                            text = "这个是第${item}个，啦啦啦啦啦啦",
                            onClick = {
                                fileDialogShow = true
                            }
                        )
                    }
                }
            } else {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter =
                        if (QQCleanerData.isDark)
                            painterResource(id = R.drawable.ic_list_empty_dark)
                        else
                            painterResource(id = R.drawable.ic_list_empty),
                        contentDescription = stringResource(
                            id = R.string.list_empty
                        ), modifier = Modifier.size(96.dp)
                    )
                    Text(
                        text = stringResource(R.string.sort_empty_tip),
                        style = QQCleanerTypes.EmptyTipStyle,
                        color = colors.thirdTextColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 24.dp)
                    )
                }
            }
        }
        Fab(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            text = stringResource(R.string.sort_fab_text),
            onClick = {
                fileAddDialogShow = true
            }
        )
    }
}

@Composable
fun FileItem(
    onClick: () -> Unit,
    text: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 56.dp)
            .clip(shape = QQCleanerShapes.cardGroupBackground)
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_file),
            tint = colors.secondTextColor,
            contentDescription = stringResource(R.string.sort_icon_tip)
        )
        Text(
            text = text,
            style = FileTextStyle,
            color = colors.secondTextColor,
            modifier = Modifier.padding(start = 16.dp)
        )

    }
}