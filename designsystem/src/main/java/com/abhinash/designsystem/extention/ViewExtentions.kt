package com.abhinash.designsystem.extention

import android.view.View

fun View.setVisibilityFromBoolean(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}