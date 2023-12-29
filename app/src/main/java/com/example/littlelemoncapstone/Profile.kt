package com.example.littlelemoncapstone

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.littlelemoncapstone.ui.theme.LittleLemonCapstoneTheme

@Composable
fun Profile(navController: NavController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(AppUtils.PREF_USER, Context.MODE_PRIVATE)
    var firstName = sharedPreferences.getString(AppUtils.PREF_KEY_FIRST_NANE, null)!!
    var lastName = sharedPreferences.getString(AppUtils.PREF_KEY_LAST_NAME, null)!!
    var emailAddress = sharedPreferences.getString(AppUtils.PREF_KEY_EMAIL_ADDRESS, null)!!

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "",
            modifier = Modifier
                .padding(20.dp)
                .width(200.dp)
                .align(Alignment.CenterHorizontally)
        )
        Surface(
            color = MaterialTheme.colors.secondary,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Let's get to know you",
                fontSize = 18.sp,
                modifier = Modifier.padding(20.dp),
                textAlign = TextAlign.Center
            )
        }
        Text(
            text = "Personal information",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(10.dp, top = 20.dp, bottom = 20.dp)
        )
        OutlinedTextField(
            value = firstName,
            enabled = false,
            onValueChange = { firstName = it },
            label = { Text(text = "user's first name") },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = lastName,
            enabled = false,
            onValueChange = { lastName = it },
            label = { Text(text = "user's last name") },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = emailAddress,
            enabled = false,
            onValueChange = { emailAddress = it },
            label = { Text(text = "user's email address") },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                navController.navigate(Home.route)
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, bottom = 20.dp),
            shape = RoundedCornerShape(10.dp),
        ) {
            Text(
                text = "Log out",
                fontSize = 16.sp,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun ProfilePreviewLight() {
    LittleLemonCapstoneTheme {
        Profile()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProfilePreviewDark() {
    LittleLemonCapstoneTheme {
        Profile()
    }
}*/
