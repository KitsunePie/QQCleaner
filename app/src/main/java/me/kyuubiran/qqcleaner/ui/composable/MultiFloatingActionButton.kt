package me.kyuubiran.qqcleaner.ui.composable

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.kyuubiran.qqcleaner.ui.theme.ColorPrimary


/**
 * 折叠/展开多个FloatingActionButton菜单
 */
@Composable
fun MultiFloatingActionButton(
    modifier: Modifier = Modifier,
    @DrawableRes srcIcon: Int,
    srcIconColor: Color = Color.White,
    fabBackgroundColor: Color = ColorPrimary,
    showLabels: Boolean = true,
    items: List<MultiFabItem>,
) {
    //当前菜单默认状态处于：Collapsed
    val currentState = remember { mutableStateOf(MultiFabState.Collapsed) }
    //创建过渡对象，用于管理多个动画值，并且根据状态变化运行这些值
    val transition = updateTransition(targetState = currentState, label = "")
    //用于+号按钮的旋转动画
    val rotateAnim: Float by transition.animateFloat(
        transitionSpec = {
            if (targetState.value == MultiFabState.Expanded) {
                spring(stiffness = Spring.StiffnessLow)
            } else {
                spring(stiffness = Spring.StiffnessMedium)
            }
        }, label = ""
    ) { state ->
        //根据state来设置最终的角度
        if (state.value == MultiFabState.Collapsed) 0F else -45F
    }
    //透明度动画
    val alphaAnim: Float by transition.animateFloat(transitionSpec = {
        tween(durationMillis = 200)
    }, label = "") { state ->
        if (state.value == MultiFabState.Expanded) 1F else 0F
    }
    //记录每个Item的收缩动画的Transition
    val shrinkListAnim: MutableList<Float> = mutableListOf()
    items.forEachIndexed { index, _ ->
        //循环生成Transition
        val shrinkAnim by transition.animateFloat(targetValueByState = { state ->
            when (state.value) {
                MultiFabState.Collapsed -> 5F
                //根据位置，递增每个item的位置高度
                MultiFabState.Expanded -> (index + 1) * 60F + if (index == 0) 5F else 0F
            }
        }, label = "", transitionSpec = {
            if (targetState.value == MultiFabState.Expanded) {
                //dampingRatio属性删除等于默认1F，没有回弹效果
                spring(stiffness = Spring.StiffnessLow, dampingRatio = 0.58F)
            } else {
                spring(stiffness = Spring.StiffnessMedium)
            }
        })
        //添加到收缩列表中
        shrinkListAnim.add(index, shrinkAnim)
    }
    Box(modifier = modifier, contentAlignment = Alignment.BottomEnd) {
        //创建多个Item,Fab按钮
        items.forEachIndexed { index, item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(
                        //从收缩列表中获取
                        bottom = shrinkListAnim[index].dp,
                        top = 5.dp,
                        end = 30.dp
                    )
                    .alpha(animateFloatAsState(alphaAnim).value)
            ) {
                if (showLabels) {
                    Text(
                        item.label,
                        color = item.labelTextColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            //.clip(Shapes.medium)
                            .alpha(animateFloatAsState(alphaAnim).value)
                            .background(color = item.labelBackgroundColor)
                            .padding(start = 6.dp, end = 6.dp, top = 4.dp, bottom = 4.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
                FloatingActionButton(
                    backgroundColor = if (item.fabBackgroundColor == Color.Unspecified) MaterialTheme.colors.primary else item.fabBackgroundColor,
                    modifier = Modifier.size(46.dp),
                    onClick = {
                        //更新状态 => 折叠菜单
                        currentState.value = MultiFabState.Collapsed
                        item.onClick?.invoke()
                    },
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 2.dp,
                        pressedElevation = 4.dp
                    )
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = item.icon,
                        tint = item.srcIconColor,
                        contentDescription = item.label
                    )
                }
            }
        }
        //"+"号，切换按钮
        FloatingActionButton(
            modifier = Modifier.padding(0.dp, end = 25.dp),
            backgroundColor = if (fabBackgroundColor == Color.Unspecified) MaterialTheme.colors.primary else fabBackgroundColor,
            onClick = {
                //更新状态执行：收缩动画
                currentState.value =
                    if (currentState.value == MultiFabState.Collapsed) MultiFabState.Expanded else MultiFabState.Collapsed
            }) {
            Icon(
                painter = painterResource(id = srcIcon),
                modifier = Modifier.rotate(rotateAnim),
                tint = srcIconColor,
                contentDescription = null
            )
        }
    }
}

/**
 * FloatingActionButton填充的数据
 */
class MultiFabItem(
    val icon: Painter,
    val label: String,
    val srcIconColor: Color = Color.White,
    val labelTextColor: Color = Color.White,
    val labelBackgroundColor: Color = Color.Black.copy(alpha = 0.6F),
    val fabBackgroundColor: Color = ColorPrimary,
    val onClick: (() -> Unit)? = null
)

/**
 * 定义FloatingActionButton状态
 */
enum class MultiFabState {
    Collapsed,
    Expanded
}