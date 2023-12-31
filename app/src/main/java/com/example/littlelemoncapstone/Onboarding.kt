package com.example.littlelemoncapstone

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Onboarding(navController: NavController) {
    val context = LocalContext.current
    var firstName by remember {
        mutableStateOf("")
    }
    var lastName by remember {
        mutableStateOf("")
    }
    var emailAddress by remember {
        mutableStateOf("")
    }

    val focusManager = LocalFocusManager.current

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
                .align(CenterHorizontally)
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
            onValueChange = { firstName = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions ( onDone = {
                focusManager.clearFocus(true)
            } ),
            label = { Text(text = "user's first name") },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions ( onDone = {
                focusManager.clearFocus(true)
            } ),
            label = { Text(text = "user's last name") },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = emailAddress,
            onValueChange = { emailAddress = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions ( onDone = {
                focusManager.clearFocus(true)
            } ),
            label = { Text(text = "user's email address") },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                if (firstName.isNotBlank() && lastName.isNotBlank() && emailAddress.isNotBlank()) {
                    context.getSharedPreferences(AppUtils.PREF_USER, Context.MODE_PRIVATE)
                        .edit()
                        .putString(AppUtils.PREF_KEY_FIRST_NANE, firstName)
                        .putString(AppUtils.PREF_KEY_LAST_NAME, lastName)
                        .putString(AppUtils.PREF_KEY_EMAIL_ADDRESS, emailAddress)
                        .commit()
                    navController.navigate(Home.route)
                }
                else Toast.makeText(
                    context,
                    "Registration unsuccessful. Please enter all data.",
                    Toast.LENGTH_SHORT
                ).show()
            },
            modifier = Modifier
                .align(CenterHorizontally)
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, bottom = 20.dp),
            shape = RoundedCornerShape(10.dp),
        ) {
            Text(
                text = "Register",
                fontSize = 16.sp,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}


/*
@Preview(showBackground = true)
@Composable
fun OnboardingPreviewLight() {
    LittleLemonCapstoneTheme {
        Onboarding()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun OnboardingPreviewDark() {
    LittleLemonCapstoneTheme {
        Surface {
            Onboarding()
        }
    }
}*/
