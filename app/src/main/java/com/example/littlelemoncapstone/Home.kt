package com.example.littlelemoncapstone

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.room.Room
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Home(navController: NavController) {
    val context = LocalContext.current
    val database by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java,"databse").fallbackToDestructiveMigration().build()
    }

    (context as MainActivity).lifecycleScope.launch(Dispatchers.IO) {
        if (database.menuItemDao().isEmpty()) {
            val menuItemsNetwork = fetchMenu()
            saveMenuToDatabase(menuItemsNetwork, database)
        }
    }

    val databaseMenuItem by database.menuItemDao().getAll().observeAsState(emptyList())

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Spacer(modifier = Modifier.height(1.dp))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .width(150.dp),
            )

            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "",
                modifier = Modifier
                    .width(40.dp)
                    .align(CenterVertically)
                    .clickable { navController.navigate(Profile.route) }
            )
        }

        UpperPanel(databaseMenuItem)
    }
}

private suspend fun fetchMenu(): List<MenuItemNetwork> {
    val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }
    return httpClient
        .get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
        .body<MenuNetwork>()
        .menu
}

private fun saveMenuToDatabase(menuItemsNetwork: List<MenuItemNetwork>, database: AppDatabase) {
    val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
    database.menuItemDao().insertAll(*menuItemsRoom.toTypedArray())
}

@Composable
fun UpperPanel(databaseMenuItem: List<MenuItemRoom>) {
    var searchPhrase by remember {
        mutableStateOf("")
    }
    var menuItems = databaseMenuItem
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.secondary)
            .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.title),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary
        )
        Text(
            text = stringResource(id = R.string.location),
            fontSize = 24.sp
        )
        Row(
            modifier = Modifier
                .padding(top = 18.dp)
        ) {
            Text(
                text = stringResource(id = R.string.description),
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(bottom = 28.dp)
                    .fillMaxWidth(0.6f)
            )
            Image(
                painter = painterResource(id = R.drawable.upperpanelimage),
                contentDescription = "Upper Panel Image",
                modifier = Modifier.clip(RoundedCornerShape(20.dp))
            )
        }
        OutlinedTextField(
            value = searchPhrase,
            onValueChange = { searchPhrase = it },
            label = { Text("Enter search phrase") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 50.dp, end = 50.dp)
        )

        if (searchPhrase.isNotEmpty()) menuItems = menuItems.filter { it.title.contains(searchPhrase, ignoreCase = true) }
        MenuItemsList(items = menuItems)
    }
}

@Composable
private fun MenuItemsList(items: List<MenuItemRoom>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 20.dp)
    ) {
        items(
            items = items,
            itemContent = { menuItem ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(menuItem.title)
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp),
                        textAlign = TextAlign.Right,
                        text = "%.2f".format(menuItem.price)
                    )
                }
            }
        )
    }
}

/*
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
}*/
