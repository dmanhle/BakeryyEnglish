package com.plcoding.bakeryenglish.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Undo
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.plcoding.bakeryenglish.presentation.swipelib.rememberSwipeableCardState
import com.plcoding.bakeryenglish.presentation.swipelib.swipableCard
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.plcoding.bakeryenglish.domain.model.WordOfLesson
import com.plcoding.bakeryenglish.presentation.screen.components.dialog.AlertDialogDemo
import com.plcoding.bakeryenglish.presentation.screen.components.dialog.onDialogCelebrate
import com.plcoding.bakeryenglish.presentation.swipelib.Direction
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
    val state = rememberSwipeableCardState()
    var isShowMeaning = remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .weight(0.8f)
                .background(Color.White)
                .fillMaxWidth()
                .swipableCard(
                    state = state,
                    onSwiped = { direction ->
                        println("The card was swiped to $direction")
                    },
                    onSwipeCancel = {
                        println("The swiping was cancelled")
                    },
                    listOf(Direction.Up, Direction.Down),
                )
                .clickable {
                    isShowMeaning.value = true;
                }
        ) {
            Card(
               elevation = 4.dp,
               shape = RoundedCornerShape(15.dp),
               backgroundColor = Color.White,
               modifier = Modifier
                   .padding(start = 30.dp, end = 30.dp, top = 30.dp, bottom = 30.dp)
                   .fillMaxSize()
            ) {
                Column(
                    modifier =  Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.weight(0.1f),
                        Center
                    ) {
                        Text(
                            text = wordOfLesson.word.toString(),
                            modifier = Modifier
                        )
                    }
                    Divider()
                    Box(
                        modifier = Modifier.weight(0.9f),
                        Center
                    ) {
                        if(isShowMeaning.value){
                            Text(
                                text = wordOfLesson.word.toString(),
                            )
                        }
                    }
                }
            }
            LaunchedEffect(state.swipedDirection){
                if(state.swipedDirection!=null && state.swipedDirection == Direction.Right) {
                    onComplete()
                    state.reset()
                    println("The card was swiped to SSSS ${state.swipedDirection!!}")
                }
                if(state.swipedDirection!=null && state.swipedDirection == Direction.Left) {
                    onSkip()
                    state.reset()
                    println("The card was swiped to SSSS ${state.swipedDirection!!}")
                }
            }
        }
    }
}



