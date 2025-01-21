package com.lmorda.homework.utils

import android.content.Context
import android.content.Intent
import timber.log.Timber

fun Context.shareText(text: String) {
    try {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        startActivity(
            Intent.createChooser(
                shareIntent,
                "Share via"
            )
        )
    } catch (ex: Exception) {
        Timber.e(ex)
    }
}
