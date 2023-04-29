package com.plcoding.bakeryenglish.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.bakeryenglish.R

@Composable
fun MyProfilePage() {
    Column() {
        Card(modifier = Modifier.padding(top = 500.dp)) {
            Row() {
                Image(painter = painterResource(id = R.drawable.icons8_google), contentDescription ="" )
                Text(text = "Sign in with google")
            }
        }
    }
}