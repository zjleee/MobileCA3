package com.busmanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.busmanagement.ui.theme.BusManagementTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusManagementTheme {
                ComposeApp()
            }
        }
    }


}

@Composable
fun ComposeApp() {
    AppNavigation()
}
