package me.kyuubiran.qqcleaner.ui.scene

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import me.kyuubiran.qqcleaner.QQCleanerData
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.composable.TopBar
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerShapes
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.DescribeTextStyle
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.NameTextStyle
import me.kyuubiran.qqcleaner.ui.util.dp2px

@Composable
fun DeveloperScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colors.pageBackgroundColor)
            .padding(top = QQCleanerData.statusBarHeight)
    ) {
        TopBar(
            click = {
                navController.popBackStack()
            },
            stringResource(id = R.string.title_dev)
        )
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(24.dp)
        )
        CardGroup(72.dp) {
            DevItem(
                name = stringResource(id = R.string.dev_org_name),
                text = stringResource(id = R.string.dev_org_desc),
                imageUri = "https://avatars.githubusercontent.com/u/80496274?s=200&v=4",
                uri = "https://github.com/KitsunePie",
                navController = navController
            )
        }
        CardTitle(text = stringResource(id = R.string.title_dev))
        CardGroup(360.dp) {
            DevItem(
                name = stringResource(id = R.string.dev_KyuubiRan_name),
                text = stringResource(id = R.string.dev_KyuubiRan_desc),
                imageUri = "https://avatars.githubusercontent.com/u/45789471?v=4",
                uri = "https://github.com/KyuubiRan",
                navController = navController
            )
            DevItem(
                name = stringResource(id = R.string.dev_Ketal_name),
                text = stringResource(id = R.string.dev_Ketal_desc),
                imageUri = "https://avatars.githubusercontent.com/u/41381927?v=4",
                uri = "https://github.com/keta1",
                navController = navController
            )
            DevItem(
                name = stringResource(id = R.string.dev_NextAlone_name),
                text = stringResource(id = R.string.dev_NextAlone_desc),
                imageUri = "https://avatars.githubusercontent.com/u/12210746?v=4",
                uri = "https://github.com/NextAlone",
                navController = navController
            )
            DevItem(
                name = stringResource(id = R.string.dev_Agoines_name),
                text = stringResource(id = R.string.dev_Agoines_desc),
                imageUri = "https://avatars.githubusercontent.com/u/50230626?s=96&v=4",
                uri = "https://github.com/Agoines",
                navController = navController
            )
            DevItem(
                name = stringResource(id = R.string.dev_MaiTungTM_name),
                text = stringResource(id = R.string.dev_MaiTungTM_desc),
                imageUri = "https://avatars.githubusercontent.com/u/49202599?v=4",
                uri = "https://github.com/Lagrio",
                navController = navController
            )
        }
    }
}

@Composable
fun DevItem(
    navController: NavController,
    name: String,
    text: String,
    imageUri: String,
    uri: String
) {
    Row(
        Modifier
            .height(72.dp)
            .fillMaxWidth()
            .clip(QQCleanerShapes.cardGroupBackground)
            .clickable {
                navController.context.startActivity(Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse(uri)
                }
                )
            }
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val context = LocalContext.current
        Image(
            modifier = Modifier
                .padding(end = 16.dp)
                .size(40.dp),
            painter = rememberImagePainter(
                data = imageUri,
                builder = {
                    transformations(RoundedCornersTransformation(10.dp2px(context)))
                }
            ),
            contentDescription = ""
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(text = name, style = NameTextStyle, color = colors.secondTextColor)
            Text(text = text, style = DescribeTextStyle, color = colors.thirdTextColor)
        }
        ForwardIcon(id = R.string.item_about)
    }
}