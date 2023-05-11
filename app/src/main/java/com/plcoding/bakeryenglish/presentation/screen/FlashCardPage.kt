package com.plcoding.bakeryenglish.presentation.screen.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Undo
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.plcoding.bakeryenglish.domain.model.WordOfLesson
import com.plcoding.bakeryenglish.presentation.screen.MyLoadingIndicator
import com.plcoding.bakeryenglish.presentation.screen.components.dialog.AlertDialogDemo
import com.plcoding.bakeryenglish.presentation.screen.components.dialog.onDialogCelebrate
import com.plcoding.bakeryenglish.presentation.viewmodel.FlashCardViewModel
import kotlinx.coroutines.launch


@SuppressLint("UnrememberedMutableState", "SuspiciousIndentation")
@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun FlashCardPage(navController: NavController) {


    val flashCardViewModel: FlashCardViewModel = hiltViewModel()
    val lesson = flashCardViewModel.lesson.value

    var pagerState = flashCardViewModel.pagerState.value

    val pageCurrent = remember { mutableStateOf(0) }

    var currentProgress = remember {
        mutableStateOf(0.1f)
    }
    val coroutineScope = rememberCoroutineScope()

    // đánh dấu là chưa có một flashcard nào chưa thuộc
    var isSkipFlashCard = remember {
        mutableStateOf(false)
    }
    var isLastFlashCard = remember {
        mutableStateOf(false)
    }

    // lưu lại danh sách danh sách từ vựng chưa học
    var myListUnfinished = ArrayList<WordOfLesson>()

    Scaffold(
        floatingActionButton = {
        },
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "${pageCurrent.value} / ${lesson.listVocabularyLesson!!.size}",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(end = 30.dp)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Navigation icon")
                    }
                },
                backgroundColor = Color.Transparent,
                actions ={
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = "description")
                    }
                },
                modifier = Modifier.background(Color.Transparent),
                elevation = 0.0001.dp
            )
        },
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.Transparent)
    ){
        Column (modifier = Modifier.padding(it)){
            // hiển thị thanh giá trị progress
            MyLoadingIndicator(currentProgress.value)

            // hiển thị viewpager
            HorizontalPager(
                state = pagerState,
                count = lesson.listVocabularyLesson!!.size,
            ) { page ->
                pageCurrent.value = pagerState.currentPage+1
                // thiết lập giá trị hiển thị cho thanh progress = trang hiển tại chia cho tổng số trang
                currentProgress.value = (currentPage+1).toFloat() / lesson.listVocabularyLesson!!.size.toFloat()
                TwoPartsScreen(
                    lesson.listVocabularyLesson!![page],
                    onComplete = {
                        coroutineScope.launch {
                            //chuyển trang
                            if(pagerState.currentPage < pagerState.pageCount-1){
                                pagerState.scrollToPage(pagerState.currentPage+1)
                            }else {
                                isLastFlashCard.value = true
                            }
                        }
                    },
                    onSkip = {
                        coroutineScope.launch {
                            isSkipFlashCard.value = true;
                            // chuyển trang
                            if(pagerState.currentPage < pagerState.pageCount-1){
                                pagerState.scrollToPage(pagerState.currentPage+1)
                            }else{
                                isLastFlashCard.value = true
                            }
                        }
                        myListUnfinished.add(lesson.listVocabularyLesson!![page])
                        // lưu lại các từ chưa thuộc để lát nữa lưu vào prefence
                    }
                )
                // code check đến cuối trang
                if(isLastFlashCard.value && isSkipFlashCard.value){
                    // hiện đã tới trang cuối && và có một flashcard cần học lại
                    AlertDialogDemo(navController,myListUnfinished)

                }else if(isLastFlashCard.value && !isSkipFlashCard.value){
                    onDialogCelebrate(navController = navController)
                }
            }
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}
// chia màn hình ra theo tỉ lệ 8 / 2
@Composable
fun TwoPartsScreen(wordOfLesson: WordOfLesson,onComplete: () -> Unit,onSkip: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .weight(0.8f)
                .fillMaxWidth()
                .background(Color.White)
        ) {
            // firstview - hiển thị các thẻ
            Card(
                elevation = 4.dp,
                shape = RoundedCornerShape(15.dp),
                backgroundColor = Color.White,
                modifier = Modifier.padding(start = 30.dp, end = 30.dp, top = 30.dp, bottom = 30.dp)
            ) {
                var isFrontVisible by remember { mutableStateOf(true) }
                val rotationX by animateFloatAsState(if (isFrontVisible) 0f else 180f)
                val alpha by animateFloatAsState(if (isFrontVisible) 1f else 0f)

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .clickable {
                            isFrontVisible = !isFrontVisible
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .alpha(alpha)
                            .rotate(rotationX)
                            .background(Color.White)
                    ) {
                        Text(
                            text = wordOfLesson.word,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .alpha(1 - alpha)
                            .rotate(rotationX - 180)
                            .background(Color.White)
                    ) {
                        Text(
                            text = wordOfLesson.meaning,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .weight(0.17f)
                .fillMaxWidth()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                CircleWithUncomplete {
                    onSkip()
                }
                Spacer(modifier = Modifier.width(200.dp))
                CircleWithCheck{
                    onComplete()
                }
            }
        }
    }
}

@Composable
fun CircleWithCheck(onComplete: () ->Unit) {
    Box(
        modifier = Modifier
            .size(52.dp)
            .background(Color.Gray.copy(0.1f), CircleShape)
    ) {
        IconButton(
            onClick = {
                onComplete()
            },
            modifier = Modifier.align(Alignment.Center)
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color.Black
            )
        }
    }
}
@Composable
fun CircleWithUncomplete(onSkip: () ->Unit) {
    Box(
        modifier = Modifier
            .size(52.dp)
            .background(Color.Gray.copy(0.1f), CircleShape)
    ) {
        IconButton(
            onClick = {
                onSkip()
            },
            modifier = Modifier.align(Alignment.Center)
        ) {
            Icon(
                imageVector = Icons.Default.Undo,
                contentDescription = null,
                tint = Color.Black
            )
        }
    }
}


