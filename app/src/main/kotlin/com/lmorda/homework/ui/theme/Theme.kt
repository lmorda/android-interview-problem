package com.lmorda.homework.ui.theme

import android.app.Activity
import android.view.View
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.core.view.WindowCompat
import com.lmorda.homework.R

private val LightColorScheme = lightColorScheme(
    primary = BrandLight,
    background = White,
    onPrimary = Black,
    onBackground = Grey220,
)

private val DarkColorScheme = darkColorScheme(
    primary = BrandDark,
    background = BrandDarkest,
    onPrimary = White,
    onBackground = Grey20,
)

@Composable
fun HomeworkTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )

    val view = LocalView.current

    SideEffect {
        view.updateStatusBarColors(
            isAppearanceLightStatusBars = !darkTheme,
            isAppearanceLightNavigationBars = !darkTheme,
        )
    }
}

fun View.updateStatusBarColors(
    isAppearanceLightStatusBars: Boolean,
    isAppearanceLightNavigationBars: Boolean,
    statusBarColor: Color = when {
        isAppearanceLightStatusBars -> White
        else -> BrandDark
    },
    navigationBarColor: Color = when {
        isAppearanceLightNavigationBars -> White
        else -> BrandDark
    },
) {
    if (!isInEditMode) {
        val window = (context as Activity).window
        window.statusBarColor = statusBarColor.toArgb()
        WindowCompat.getInsetsController(window, this).isAppearanceLightStatusBars =
            isAppearanceLightStatusBars
        window.navigationBarColor = navigationBarColor.toArgb()
        WindowCompat.getInsetsController(window, this).isAppearanceLightNavigationBars =
            isAppearanceLightNavigationBars
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topAppBarColors() = TopAppBarColors(
    containerColor = MaterialTheme.colorScheme.background,
    titleContentColor = MaterialTheme.colorScheme.background,
    actionIconContentColor = MaterialTheme.colorScheme.background,
    navigationIconContentColor = MaterialTheme.colorScheme.background,
    scrolledContainerColor = MaterialTheme.colorScheme.background,
)

val smallSize: Dp
    @Composable get() =
        dimensionResource(id = R.dimen.small)

val mediumSize: Dp
    @Composable get() =
        dimensionResource(id = R.dimen.medium)

val defaultSize: Dp
    @Composable get() =
        dimensionResource(id = R.dimen.standard)

val mediumLargeSize: Dp
    @Composable get() =
        dimensionResource(id = R.dimen.medium_large)

val largeSize: Dp
    @Composable get() =
        dimensionResource(id = R.dimen.large)

val xLargeSize: Dp
    @Composable get() =
        dimensionResource(id = R.dimen.xlarge)
