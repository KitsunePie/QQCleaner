package me.kyuubiran.qqcleaner.uitls

import android.animation.Animator


interface AnimatorListener : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {
        }

        override fun onAnimationEnd(animation: Animator){

        }

        override fun onAnimationCancel(animation: Animator) {
        }

        override fun onAnimationRepeat(animation: Animator) {

        }
}