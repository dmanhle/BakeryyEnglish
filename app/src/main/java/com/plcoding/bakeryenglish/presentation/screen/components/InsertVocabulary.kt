package com.plcoding.bakeryenglish.presentation.screen.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.bakeryenglish.domain.model.WordOfLesson
import com.plcoding.bakeryenglish.presentation.event.Event
import com.plcoding.bakeryenglish.presentation.viewmodel.DictonaryViewModel


@Composable
fun ComponentInsertVocabulary():WordOfLesson{
    val dictonaryViewModel:DictonaryViewModel = hiltViewModel()
    val list = dictonaryViewModel.stateSearchTab.value.listVocabulary;
    val input1 = remember { mutableStateOf("") }
    val input2 = remember { mutableStateOf("") }


    var wordOfLesson = remember {
        WordOfLesson("","");
    }

    var listMeaning =  remember { mutableStateOf(ArrayList<String>()) }

    Card(
        elevation = 2.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(2.dp),
    ) {
        Column(
            Modifier.background(Color.White)
        ) {
            TextField(
                value = input1.value,
                onValueChange = {
                    input1.value = it
                    wordOfLesson.word = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                    .height(50.dp)
                ,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    unfocusedIndicatorColor = Color.Gray,
                    disabledIndicatorColor = Color.Gray
                ),
                trailingIcon = {
                    if (input1.value.isNotEmpty()) {
                        IconButton(
                            onClick = { input1.value = "" }, Modifier.size(13.dp)
                        ) {
                            Icon(Icons.Filled.Clear, contentDescription = "Clear")
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text,
                    autoCorrect = true,
                )
            )
            Text(
                text = "Thuật ngữ",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                color = Color(0xFF008080)
            )
            Spacer(
                modifier = Modifier
                    .height(5.dp)
                    .background(Color.White),
            )
            TextField(
                value = input2.value,
                onValueChange = {
                    input2.value = it
                    wordOfLesson.meaning = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(start = 10.dp, end = 10.dp)
                    .onFocusChanged { it ->
                        if (it.isFocused) {
                            listMeaning = listMeaning
                        }
                    },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    unfocusedIndicatorColor = Color.Gray,
                    disabledIndicatorColor = Color.Gray
                ),
                trailingIcon = {
                    if (input2.value.isNotEmpty()) {
                        IconButton(onClick = { input2.value = "" }, Modifier.size(13.dp)) {
                            Icon(
                                Icons.Filled.Clear,
                                contentDescription = "Clear",
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text,
                    autoCorrect = true
                ),
            )
            listMeaning.value.forEach{
                Text(text = it.toString())
            }
            Text(
                text = "Định nghĩa",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                color = Color(0xFF008080)
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
    return wordOfLesson
}
