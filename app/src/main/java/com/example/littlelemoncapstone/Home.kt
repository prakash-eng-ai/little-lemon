package com.example.littlelemoncapstone

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.littlelemoncapstone.ui.theme.LittleLemonCapstoneTheme

@Composable
fun Home() {

}

@Preview(showBackground = true)
@Composable
fun HomePreviewLight() {
    LittleLemonCapstoneTheme {
        Home()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomePreviewDark() {
    LittleLemonCapstoneTheme {
        Home()
    }
}