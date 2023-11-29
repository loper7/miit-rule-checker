package com.loper7.simple.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.loper7.simple.ui.theme.themeColor

@Composable
fun TopAppBar(content : @Composable () -> Unit) {

    val systemUiController = rememberSystemUiController()
    LaunchedEffect(key1 = Unit) {
        systemUiController.setStatusBarColor(Color.Transparent)
    }

    //标题栏高度
    val appBarHeight = 52.dp

    //状态栏高度
    val statusBarHeightDp = LocalDensity.current.run {
        WindowInsets.statusBars.getTop(this).toDp()
    }

    Row(modifier = Modifier
        .background(themeColor)
        .fillMaxWidth()
        .height(appBarHeight + statusBarHeightDp)
        .padding(top = statusBarHeightDp) ,
        horizontalArrangement = Arrangement.Start ,
        verticalAlignment = Alignment.CenterVertically) {
        content()
    }
}

@Preview
@Composable
fun TopAppBarPreview() {
    TopAppBar {
        Text(text = "标题" ,
            modifier = Modifier.padding(horizontal = 16.dp) ,
            color = Color.White ,
            fontSize = 20.sp ,
            fontWeight = FontWeight.W600)
    }
}