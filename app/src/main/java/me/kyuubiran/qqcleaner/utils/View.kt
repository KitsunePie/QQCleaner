package me.kyuubiran.qqcleaner.utils

import android.view.View
import android.view.ViewGroup

/**
 * From Androidx.
 */

/**
 * Returns the left margin if this view's [LayoutParams] is a [ViewGroup.MarginLayoutParams],
 * otherwise 0.
 *
 * @see ViewGroup.MarginLayoutParams
 */
public inline val View.marginLeft: Int
    get() = (layoutParams as? ViewGroup.MarginLayoutParams)?.leftMargin ?: 0

/**
 * Returns the top margin if this view's [LayoutParams] is a [ViewGroup.MarginLayoutParams],
 * otherwise 0.
 *
 * @see ViewGroup.MarginLayoutParams
 */
public inline val View.marginTop: Int
    get() = (layoutParams as? ViewGroup.MarginLayoutParams)?.topMargin ?: 0

/**
 * Returns the right margin if this view's [LayoutParams] is a [ViewGroup.MarginLayoutParams],
 * otherwise 0.
 *
 * @see ViewGroup.MarginLayoutParams
 */
public inline val View.marginRight: Int
    get() = (layoutParams as? ViewGroup.MarginLayoutParams)?.rightMargin ?: 0

/**
 * Returns the bottom margin if this view's [LayoutParams] is a [ViewGroup.MarginLayoutParams],
 * otherwise 0.
 *
 * @see ViewGroup.MarginLayoutParams
 */
public inline val View.marginBottom: Int
    get() = (layoutParams as? ViewGroup.MarginLayoutParams)?.bottomMargin ?: 0
