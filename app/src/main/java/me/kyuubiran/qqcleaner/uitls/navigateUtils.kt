package me.kyuubiran.qqcleaner.uitls

import androidx.annotation.IdRes
import androidx.navigation.fragment.NavHostFragment
import me.kyuubiran.qqcleaner.page.BaseFragment


fun BaseFragment.navigatePage(@IdRes resId: Int){
    val navController = NavHostFragment.findNavController(this)
    navController.navigate(resId)
}