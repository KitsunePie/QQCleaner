package me.kyuubiran.qqcleaner

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.ViewTreeSavedStateRegistryOwner

open class TestActivity : Activity(), LifecycleOwner, ViewModelStoreOwner,
    SavedStateRegistryOwner, OnBackPressedDispatcherOwner {
    // 需要自己实现
    private var lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }


    private fun handleLifecycleEvent(event: Lifecycle.Event) =
        lifecycleRegistry.handleLifecycleEvent(event)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 主要是让 ComponentActivity() 里面生命周期改成这样，应该就好了
        savedStateRegistryController.performRestore(null)
        handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    fun setContent(
        parent: CompositionContext? = null,
        content: @Composable () -> Unit
    ) {
        val existingComposeView = window.decorView
            .findViewById<ViewGroup>(android.R.id.content)
            .getChildAt(0) as? ComposeView

        if (existingComposeView != null) with(existingComposeView) {
            setParentCompositionContext(parent)
            setContent(content)
        } else ComposeView(this).apply {
            // Set content and parent **before** setContentView
            // to have ComposeView create the composition on attach
            setParentCompositionContext(parent)
            setContent(content)
            // Set the view tree owners before setting the content view so that the inflation process
            // and attach listeners will see them already present
            setOwners()
            setContentView(
                this, LayoutParams(
                    MATCH_PARENT,
                    MATCH_PARENT
                )
            )
        }
    }


    /**
     * These owners are not set before AppCompat 1.3+ due to a bug, so we need to set them manually in
     * case developers are using an older version of AppCompat.
     */
    private fun setOwners() {
        val decorView = window.decorView
        if (ViewTreeLifecycleOwner.get(decorView) == null) {
            ViewTreeLifecycleOwner.set(decorView, this)
        }
        if (ViewTreeViewModelStoreOwner.get(decorView) == null) {
            ViewTreeViewModelStoreOwner.set(decorView, this)
        }
        if (ViewTreeSavedStateRegistryOwner.get(decorView) == null) {
            ViewTreeSavedStateRegistryOwner.set(decorView, this)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }


    //ViewModelStore Methods
    private val store = ViewModelStore()

    override fun getViewModelStore(): ViewModelStore = store

    //SaveStateRegestry Methods

    private val savedStateRegistryController = SavedStateRegistryController.create(this)

    override val savedStateRegistry = savedStateRegistryController.savedStateRegistry

    // 这些是返回，没必要管它们
    private val mOnBackPressedDispatcher = OnBackPressedDispatcher {
        // Calling onBackPressed() on an Activity with its state saved can cause an
        // error on devices on API levels before 26. We catch that specific error and
        // throw all others.
        try {
            super.onBackPressed()
        } catch (e: IllegalStateException) {
            if (!TextUtils.equals(
                    e.message,
                    "Can not perform this action after onSaveInstanceState"
                )
            ) {
                throw e
            }
        }
    }
    override fun getOnBackPressedDispatcher() = mOnBackPressedDispatcher
    override fun onBackPressed() {
        mOnBackPressedDispatcher.onBackPressed()
    }
}