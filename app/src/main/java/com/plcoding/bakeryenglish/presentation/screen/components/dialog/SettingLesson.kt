package com.plcoding.bakeryenglish.presentation.screen.components.dialog

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.bakeryenglish.R

@Composable
fun SettingLesson(){
    Surface(
        color = Color.Gray.copy(0.1f),
        modifier = Modifier.fillMaxSize()
    ) {
        Row() {
            val image = painterResource(id = R.drawable.baseline_delete_24)
            Icon(painter = image, contentDescription = "")
            Text(
                text = "Xóa học phần",
                Modifier.size(20.dp),
                fontWeight = FontWeight.Bold,
                color = Color.Gray.copy(0.2f)
            )
        }
    }
}