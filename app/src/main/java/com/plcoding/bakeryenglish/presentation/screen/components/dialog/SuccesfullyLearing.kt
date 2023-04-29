package com.plcoding.bakeryenglish.presentation.screen.components.dialog

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.plcoding.bakeryenglish.R
import com.plcoding.bakeryenglish.core.RouteScreen

@Composable
fun SuccesfullyLearing(navController: NavController){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FullScreenDialog(navController = navController)
    }
}
@Composable
fun FullScreenDialog(
    primaryColor: Color = Color(0xFF35898F),
    context: Context = LocalContext.current.applicationContext,
    navController: NavController
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // medal icon
            Icon(
                painter = painterResource(id = R.drawable.award_6683771),
                contentDescription = "Medal icon",
                tint = primaryColor,
                modifier = Modifier.size(size = 150.dp)
            )

            Text(
                text = "Congratulations!",
                fontSize = 22.sp,
                modifier = Modifier.padding(top = 26.dp),
            )

            Text(
                text = "You have won a medal.",
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 20.dp),
            )

            Button(
                onClick = {
                    navController.popBackStack(
                        route = RouteScreen.Home.route,
                        inclusive = true
                    )
                    navController.navigate(RouteScreen.Home.route)
                },
                modifier = Modifier.padding(top = 20.dp),
                shape = RoundedCornerShape(percent = 25)
            ) {
                Text(
                    text = "Quay lại",
                    fontSize = 18.sp
                )
            }
        }
    }
}