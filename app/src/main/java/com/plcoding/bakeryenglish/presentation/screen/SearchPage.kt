package com.plcoding.bakeryenglish.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.bakeryenglish.presentation.event.Event
import com.plcoding.bakeryenglish.presentation.screen.components.tablayout.TabDefine
import com.plcoding.bakeryenglish.presentation.screen.components.tablayout.TabGrammar
import com.plcoding.bakeryenglish.presentation.screen.components.tablayout.TabSynonymous
import com.plcoding.bakeryenglish.presentation.viewmodel.DictonaryViewModel

@Preview
@Composable
fun SearchPage() {
    val viewModel:DictonaryViewModel = hiltViewModel();
    Box(
        modifier = Modifier.background(Color.White)
    ){
        Column(modifier = Modifier.padding(5.dp)) {
            searchLayout(viewmodel = viewModel)
            Spacer(modifier = Modifier.height(20.dp))
            TabLayout(viewmodel = viewModel)
        }
    }
}

@Composable
fun searchLayout(viewmodel: DictonaryViewModel){
    TextField(
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Gray,
            disabledBorderColor = Color.Gray
        ),
        shape = RoundedCornerShape(5.dp),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = ""
            )
        },
        modifier = Modifier
            .padding(end = 5.dp)
            .height(50.dp)
            .fillMaxWidth()
            .border(1.dp, Color.Gray),
        value = viewmodel.searchQuery.value.toString(),
        onValueChange = { viewmodel.onEvent(Event.Search(it)) },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text,
            autoCorrect = true
        ),
        keyboardActions = KeyboardActions(
            onDone = { /* Dismiss the keyboard */ }
        )
    )
}

@Composable
fun TabLayout(viewmodel: DictonaryViewModel) {
    val tabs = listOf("Định Nghĩa", "Ngữ Pháp", "Từ Đồng Nghĩa")
    var selectedTabIndex = viewmodel.indexTab.value;
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
        ) {
            Column(modifier = Modifier.align(Alignment.BottomEnd)) {
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    backgroundColor = Color.White,
                ) {
                    Tab(
                        selected = selectedTabIndex == 0,
                        onClick = { viewmodel.onEvent(Event.SelectedTab(0)) },
                        text = { Text(tabs[0]) }
                    )
                    Tab(
                        selected = selectedTabIndex == 1,
                        onClick = { viewmodel.onEvent(Event.SelectedTab(1))},
                        text = { Text(tabs[1]) }
                    )
                    Tab(
                        selected = selectedTabIndex == 2,
                        onClick = { viewmodel.onEvent(Event.SelectedTab(2)) },
                        text = { Text(tabs[2]) }
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .weight(0.8f).fillMaxWidth()
        ) {
            TabContent(selectedTabIndex = selectedTabIndex)
        }
    }
}

@Composable
fun TabContent(selectedTabIndex: Int) {
    when (selectedTabIndex) {
        0 -> {
            TabDefine()
        }
        1 -> {
            TabGrammar()
        }
        2 -> {
            TabSynonymous()
        }
    }
}
