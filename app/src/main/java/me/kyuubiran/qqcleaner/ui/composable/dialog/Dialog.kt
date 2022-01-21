package me.kyuubiran.qqcleaner.ui.composable.dialog

import android.content.Context
import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.ViewRootForInspector
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.ViewTreeViewModelStoreOwner
import androidx.savedstate.ViewTreeSavedStateRegistryOwner

class DialogProperties(
    val dismissOnBackPress: Boolean = true
)

@Composable
fun Dialog(
    onRemoveViewRequest: () -> Unit,
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    isDismiss: Boolean,
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    val density = LocalDensity.current
    val composition = rememberCompositionContext()
    val currentContent by rememberUpdatedState(content)
    val dialog = remember(view, density) {
        DialogWrapper(
            onDismissRequest,
            properties,
            view
        ).apply {
            setContent(composition) {
                DialogLayout {
                    currentContent()
                }
            }
        }
    }
    if (isDismiss) {
        dialog.removeView()
        onRemoveViewRequest()
    }

    DisposableEffect(dialog) {
        dialog.show()
        onDispose {
            dialog.dismiss()
            dialog.disposeComposition()
            dialog.removeView()
        }

    }
}

@Suppress("ViewConstructor")
private class DialogDecorLayout(
    context: Context,
) : AbstractComposeView(context) {

    private var content: @Composable () -> Unit by mutableStateOf({})

    override var shouldCreateCompositionOnAttachedToWindow = false
        private set

    fun setContent(parent: CompositionContext, content: @Composable () -> Unit) {
        setParentCompositionContext(parent)
        this.content = content
        shouldCreateCompositionOnAttachedToWindow = true
        createComposition()
    }

    @Composable
    override fun Content() {
        content()
    }
}

private class DialogWrapper(
    private var onDismissRequest: () -> Unit,
    properties: DialogProperties,
    composeView: View,
) : BaseDialog(composeView.context), ViewRootForInspector {

    private val dialogLayout: DialogDecorLayout = DialogDecorLayout(composeView.context)

    override val subCompositionView: AbstractComposeView get() = dialogLayout

    init {
        setContentView(dialogLayout)
        ViewTreeLifecycleOwner.set(dialogLayout, ViewTreeLifecycleOwner.get(composeView))
        ViewTreeViewModelStoreOwner.set(dialogLayout, ViewTreeViewModelStoreOwner.get(composeView))
        ViewTreeSavedStateRegistryOwner.set(
            dialogLayout,
            ViewTreeSavedStateRegistryOwner.get(composeView)
        )
        setDismissOnBackPress(properties.dismissOnBackPress)
    }

    fun setContent(parentComposition: CompositionContext, children: @Composable () -> Unit) {
        dialogLayout.setContent(parentComposition, children)
    }

    fun disposeComposition() {
        dialogLayout.disposeComposition()
    }

    override fun dismiss() {
        onDismissRequest()
    }
}

@Composable
private fun DialogLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        content()
    }
}