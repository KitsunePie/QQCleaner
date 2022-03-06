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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.composable.TopBar
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerShapes
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.DescribeTextStyle
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.NameTextStyle

@Composable
fun DeveloperScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colors.pageBackgroundColor)
            .statusBarsPadding()
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
                id = R.drawable.kitsune_pie,
                uri = "https://github.com/KitsunePie"
            )
        }
        CardTitle(text = stringResource(id = R.string.title_dev))
        CardGroup(360.dp) {
            DevItem(
                name = stringResource(id = R.string.dev_KyuubiRan_name),
                text = stringResource(id = R.string.dev_KyuubiRan_desc),
                id = R.drawable.kyuubi_ran,
                uri = "https://github.com/KyuubiRan"
            )
            DevItem(
                name = stringResource(id = R.string.dev_Ketal_name),
                text = stringResource(id = R.string.dev_Ketal_desc),
                id = R.drawable.ketal,
                uri = "https://github.com/keta1"
            )
            DevItem(
                name = stringResource(id = R.string.dev_NextAlone_name),
                text = stringResource(id = R.string.dev_NextAlone_desc),
                id = R.drawable.next_alone,
                uri = "https://github.com/NextAlone"
            )
            DevItem(
                name = stringResource(id = R.string.dev_Agoines_name),
                text = stringResource(id = R.string.dev_Agoines_desc),
                id = R.drawable.agoines,
                uri = "https://github.com/Agoines"
            )
            DevItem(
                name = stringResource(id = R.string.dev_MaiTungTM_name),
                text = stringResource(id = R.string.dev_MaiTungTM_desc),
                id = R.drawable.mai_tung_tm,
                uri = "https://github.com/Lagrio"
            )
        }
    }
}

@Composable
fun DevItem(
    name: String,
    text: String,
    id: Int,
    uri: String
) {
    val context = LocalContext.current
    Row(
        Modifier
            .height(72.dp)
            .fillMaxWidth()
            .clip(QQCleanerShapes.cardGroupBackground)
            .clickable {
                context.startActivity(Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse(uri)
                }
                )
            }
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .padding(end = 16.dp)
                .size(40.dp),
            painter = painterResource(id = id),
            contentDescription = ""
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(text = name, style = NameTextStyle, color = colors.secondTextColor)
            Text(text = text, style = DescribeTextStyle, color = colors.thirdTextColor)
        }
        ForwardIcon(id = R.string.item_about)
    }
}