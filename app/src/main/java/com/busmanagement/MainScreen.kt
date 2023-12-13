@file:OptIn(ExperimentalMaterial3Api::class)

package com.busmanagement

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import coil.compose.rememberAsyncImagePainter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "screen1") {
        composable("screen1") {
            Screen1(navController)
        }
        composable(
            "screen2/{from}/{to}",
            arguments = listOf(
                navArgument("from") { type = NavType.StringType },
                navArgument("to") { type = NavType.StringType }
            )
        ) { entry ->
            Screen2(
                from = entry.arguments?.getString("from") ?: "",
                to = entry.arguments?.getString("to") ?: "",
                navController = navController
            )
        }
        composable("screen3/{busNumber}") { entry ->
            Screen3(
                busNumber = entry.arguments?.getString("busNumber") ?: "",
                navController = navController
            )
        }
        composable("screen4") {
            Screen4(navController)
        }
    }
}

@Composable
fun Screen1(navController: NavController) {
    var from by remember { mutableStateOf("") }
    var to by remember { mutableStateOf("") }
    LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {LogoBox(navController)

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Dropdown(
                label = "From",
                items = listOf("Dundalk", "Drogheda", "Dublin"),
                selectedItem = from,
                onValueChange = { from = it }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Dropdown(
                label = "To",
                items = listOf("Dundalk", "Drogheda", "Dublin"),
                selectedItem = to,
                onValueChange = { to = it }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (from.isNotEmpty() && to.isNotEmpty()) {
                        navController.navigate("screen2/$from/$to")
                    } else {
                        // Handle error case
                        // Show a snackbar, toast, or any other error indication
                    }
                },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = "#c2002f".toColor(), contentColor = Color.White
                )
            ) {
                Text(text = "Check")
            }


        }
    }
}

@Composable
fun Screen2(from: String, to: String, navController: NavController) {
    val buses = listOf("Bus 1") // Replace with your actual bus data
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        LogoBox(navController)
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Spacer(modifier = Modifier.width(15.dp))
            Text(text = "Buses Availability", style = MaterialTheme.typography.headlineSmall)
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(buses.size) { bus ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                        .clickable { },
                ) {
                    Row(modifier = Modifier.padding(10.dp)) {
                        Spacer(modifier = Modifier.width(5.dp))
                        Column(
                            modifier = Modifier.clickable {
                                navController.navigate("screen3/$bus")
                            }
                        ) {
                            Text(
                                text = "$from to $to",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = "23 mins to go",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
        Button(
            onClick = {
                navController.navigate("screen4")
            },
            colors = ButtonDefaults.textButtonColors(
                containerColor = "#c2002f".toColor(), contentColor = Color.White
            )
        ) {
            Text(text = "Retrieve data")
        }
    }
}

@Composable
fun Screen3(busNumber: String, navController: NavController) {
    val seatData = List(6) { index ->
        SeatData(index, Random.nextBoolean()) // Occupied seat
    }
    val seatData1 = List(6) { index ->
        SeatData(index, Random.nextBoolean()) // Occupied seat
    }
    val seatData2 = List(6) { index ->
        SeatData(index, Random.nextBoolean()) // Occupied seat
    }

    Column(
        modifier = Modifier
            .fillMaxSize(), horizontalAlignment = Alignment.Start
        ,
    ) {
        LogoBox(navController)
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = "Seat Availability of Bus number " + (busNumber + 1),
                style = MaterialTheme.typography.headlineSmall
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        SeatItem(seatData2[1])
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            LazyHorizontalGrid(
                rows = GridCells.Fixed(
                    6
                )
            ) {
                items(seatData) { seat ->
                    SeatItem(seat)
                }
            }
            Spacer(
                modifier = Modifier.width(
                    30.dp
                )
            )
            LazyHorizontalGrid(
                rows = GridCells.Fixed(
                    6
                )
            ) {
                items(seatData1) { seat ->
                    SeatItem(seat)
                }
            }
            LazyHorizontalGrid(
                rows = GridCells.Fixed(
                    6
                )
            ) {
                items(seatData2) { seat ->
                    SeatItem(seat)
                }
            }
        }
    }

}

@Composable
fun Screen4(navController: NavController) {
    var seat by remember { mutableStateOf<SeatData?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LogoBox(navController)

        if (isLoading) {
            // Show loading indicator
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        } else if (isError) {
            // Show error message
            Text("Error occurred while fetching data.")
        } else if (seat != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text("Seat Number: ${seat?.seatNumber}", modifier = Modifier.weight(1f))
                Text("Seat Occupied: ${seat?.isOccupied}", modifier = Modifier.weight(1f))
            }
        } else {
            // Make Retrofit API call
            Button(
                onClick = {
                    isLoading = true
                    val call = ApiClient.apiService.getSeatByNumber()

                    call.enqueue(object : Callback<SeatData> {
                        override fun onResponse(call: Call<SeatData>, response: Response<SeatData>) {
                            isLoading = false
                            if (response.isSuccessful) {
                                seat = response.body()
                            } else {
                                isError = true
                                Log.e("API_RESPONSE", "Unsuccessful response: ${response.code()}")
                                Log.e("API_RESPONSE", "Error message: ${response.message()}")
                            }
                        }

                        override fun onFailure(call: Call<SeatData>, t: Throwable) {
                            Log.e("onFailure", "Failure: ${t.message}")
                            isLoading = false
                            isError = true
                        }
                    })
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = "#c2002f".toColor(), contentColor = Color.White
                )
            ){
                Text("Fetch Post")
            }
        }
    }
}

@Composable
fun SeatItem(seat: SeatData) {
    if (seat.isOccupied) Icons.Default.Close else Icons.Default.Person
    val seatModifier = if (seat.isOccupied) R.drawable.seat_icon else R.drawable.seat_icon_gray

    Card(
        modifier = Modifier
            .size(80.dp)
            .padding(15.dp)
            .clickable { },
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = seatModifier), // Replace with your seat icon
                contentDescription = null,
                modifier = Modifier
            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = (seat.seatNumber + 1).toString(), textAlign = TextAlign.Center)
        }
    }


}

fun String.toColor() = Color(android.graphics.Color.parseColor(this))

@Composable
fun <T> Dropdown(
    label: String,
    items: List<T>,
    selectedItem: T,
    onValueChange: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    LocalDensity.current.density

    Box {
        OutlinedTextField(
            value = selectedItem.toString(),
            onValueChange = {},
            label = { Text(text = label) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            },
            readOnly = true
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .background(MaterialTheme.colorScheme.background)
        ) {
            items.forEach { item ->
                DropdownMenuItem({ Text(text = item.toString()) }, onClick = {
                    onValueChange(item)
                    expanded = false
                })
            }
        }
    }

}

@Composable
fun LogoBox(navController: NavController) {
    AnimatedVisibility(
        visible = true, // Change this to control visibility as needed
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(align = CenterVertically)
                .background("#c2002f".toColor())
                .padding(horizontal = 5.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = {
                    navController.popBackStack("screen1", inclusive = false)
                },
                modifier = Modifier.padding(5.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = R.drawable.express), // Replace with your expressway logo
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}


