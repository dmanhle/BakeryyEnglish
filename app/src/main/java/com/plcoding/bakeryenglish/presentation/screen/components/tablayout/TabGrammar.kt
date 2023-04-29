package com.plcoding.bakeryenglish.presentation.screen.components.tablayout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Speaker
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.bakeryenglish.presentation.viewmodel.DictonaryViewModel

@Composable
fun TabGrammar(  ) {
    val viewModel: DictonaryViewModel = hiltViewModel();
    val listVocabularyLocal = viewModel.stateSearchTab.value.listVocabulary;
    val audio = viewModel.stateSearchTab.value.audio;
    LazyColumn(){
        item {
            if(!listVocabularyLocal.isNullOrEmpty()){
                val myWordInfor = listVocabularyLocal[0].toWord();
                Box{
                    Row(modifier = Modifier.align(Alignment.BottomStart)) {
                        if(audio.UkAudio != ""){
                            IconButton(onClick = { viewModel.playAudio(audio.UkAudio.toString()) }) {
                                Icon(imageVector = Icons.Default.Speaker, contentDescription = "")
                            }
                        }
                        if(audio.UsAudio != ""){
                            IconButton(onClick = {  viewModel.playAudio(audio.UsAudio.toString()) }) {
                                Icon(imageVector = Icons.Default.Speaker, contentDescription = "")
                            }
                        }
                    }
                }
                // ------------------ Word And Pronouce-----------------
                Text(text = myWordInfor.word.toString(), fontSize = 20.sp, color = Color.Red)
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "/${myWordInfor.pronounce.toString()}/", fontSize = 20.sp, color = Color.Black, fontStyle = FontStyle.Italic )
                Spacer(modifier = Modifier.height(5.dp))
                // ------------------ Word And Pronouce-----------------

                // ------------------ List Define -------------
                myWordInfor.description.noun?.let {
                    if(it.isNotEmpty()){
                        Text(text = "Danh từ",style = TextStyle(fontWeight = FontWeight.Bold), fontSize = 18.sp)
                    }
                    it.map {
                        Column {
                            Row (verticalAlignment = Alignment.CenterVertically){
                                Icon(Icons.Filled.ArrowForward, contentDescription = "", modifier = Modifier.size(15.dp))
                                    Text(text = it.word.toString(), fontSize = 17.sp)
                            }
                            it.listExample?.map {
                                Row {
                                    Spacer(modifier = Modifier.width(20.dp))
                                    Text(text = "- $it", fontSize = 16.sp)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
//                ----------------------------------------------------------
                myWordInfor.description.verb?.let {
                    if(it.isNotEmpty()){
                        Text(text = "Động từ",style = TextStyle(fontWeight = FontWeight.Bold), fontSize = 18.sp)
                    }
                    it.map {
                        Column {
                            Row (verticalAlignment = Alignment.CenterVertically){
                                Icon(Icons.Filled.ArrowForward, contentDescription = "", modifier = Modifier.size(15.dp))
                                Text(text = it.word.toString(), fontSize = 17.sp)
                            }
                            it.listExample?.map {
                                Row {
                                    Spacer(modifier = Modifier.width(20.dp))
                                    Text(text = "- $it", fontSize = 16.sp)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
                myWordInfor.description.adj?.let {
                    if(it.isNotEmpty()){
                        Text(text = "Tính từ",style = TextStyle(fontWeight = FontWeight.Bold), fontSize = 18.sp)
                    }
                    it.map {
                        Column {
                            Row (verticalAlignment = Alignment.CenterVertically){
                                Icon(Icons.Filled.ArrowForward, contentDescription = "", modifier = Modifier.size(15.dp))
                                Text(text = it.word.toString(), fontSize = 17.sp)
                            }
                            it.listExample?.map {
                                Row {
                                    Spacer(modifier = Modifier.width(20.dp))
                                    Text(text = "- $it", fontSize = 16.sp)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
                myWordInfor.description.internerVerb?.let {
                    if(it.isNotEmpty()){
                        Text(text = "Nội động từ",style = TextStyle(fontWeight = FontWeight.Bold), fontSize = 18.sp)
                    }
                    it.map {
                        Column {
                            Row (verticalAlignment = Alignment.CenterVertically){
                                Icon(Icons.Filled.ArrowForward, contentDescription = "", modifier = Modifier.size(15.dp))
                                Text(text = it.word.toString(), fontSize = 17.sp)
                            }
                            it.listExample?.map {
                                Row {
                                    Spacer(modifier = Modifier.width(20.dp))
                                    Text(text = "- $it", fontSize = 16.sp)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
                myWordInfor.description.interjection?.let {
                    if(it.isNotEmpty()){
                        Text(text = "Thán từ",style = TextStyle(fontWeight = FontWeight.Bold), fontSize = 18.sp)
                    }
                    it.map {
                        Column {
                            Row (verticalAlignment = Alignment.CenterVertically){
                                Icon(Icons.Filled.ArrowForward, contentDescription = "", modifier = Modifier.size(15.dp))
                                Text(text = it.word.toString(), fontSize = 17.sp)
                            }
                            it.listExample?.map {
                                Row {
                                    Spacer(modifier = Modifier.width(20.dp))
                                    Text(text = "- $it", fontSize = 16.sp)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }
}