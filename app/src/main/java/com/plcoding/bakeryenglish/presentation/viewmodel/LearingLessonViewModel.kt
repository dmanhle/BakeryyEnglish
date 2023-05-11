package com.plcoding.bakeryenglish.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.plcoding.bakeryenglish.data.local.database.DictonaryDao
import com.plcoding.bakeryenglish.domain.model.Lesson
import com.plcoding.bakeryenglish.presentation.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LearingLessonViewModel @Inject constructor(
    private val dao: DictonaryDao,
    savedStateHandle: SavedStateHandle
):ViewModel() {
    private var _lesson = mutableStateOf(Lesson(-1, "", emptyList()))
    val lesson: State<Lesson> = _lesson

    private var _listRamdomOptionOfAnswer = mutableStateOf(ArrayList<String>());
    val listRamdomOptionOfAnswer: State<List<String>> = _listRamdomOptionOfAnswer;

    var showDiaLogSusscess = mutableStateOf(false)

    @OptIn(ExperimentalPagerApi::class)
    val pagerState = mutableStateOf(PagerState(0))

    init {
        savedStateHandle.get<Int>("lessonID_learning").let {
            if (it != -1 && it != null) {
                viewModelScope.launch {
                    _lesson.value = dao.getLessonByID(it!!)
                }
            }
        }
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.RandomOptionOfAnswer -> {
                val listVocabularyLesson = lesson.value.listVocabularyLesson;
                val list = listVocabularyLesson!!.toMutableList();

                _listRamdomOptionOfAnswer.value.clear()
                _listRamdomOptionOfAnswer.value.add(
                    list!![event.indexAnswer].meaning // add answer into option-answer
                )
                list.removeAt(event.indexAnswer)

                var i = 0
                while (i < 2) {
                    val random = (list.indices).random()
                    _listRamdomOptionOfAnswer.value.add(
                       list[random].meaning// add answer into option-answer
                    )
                    list.removeAt(random)
                    i++;
                }
                _listRamdomOptionOfAnswer.value.shuffle()
            }
        }
    }
}
