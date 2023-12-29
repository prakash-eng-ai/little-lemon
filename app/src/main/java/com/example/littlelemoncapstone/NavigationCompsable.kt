package com.example.littlelemoncapstone

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.littlelemoncapstone.ui.theme.LittleLemonCapstoneTheme

@Composable
fun MyNavigation() {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(AppUtils.PREF_USER, Context.MODE_PRIVATE)
    val firstName = sharedPreferences.getString(AppUtils.PREF_KEY_FIRST_NANE, null)
    val mStartDestination = if (firstName != null) Home.route else Onboarding.route

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = mStartDestination
    ) {
        composable(Home.route) {
            Home()
        }
        composable(Profile.route) {
            Profile(navController)
        }
        composable(Onboarding.route) {
            Onboarding(navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyNavigationPreview() {
    LittleLemonCapstoneTheme {
        MyNavigation()
    }
}