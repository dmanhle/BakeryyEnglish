package com.plcoding.bakeryenglish.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.bakeryenglish.data.local.database.DictonaryDao
import com.plcoding.bakeryenglish.domain.model.Lesson
import com.plcoding.bakeryenglish.domain.model.WordOfLesson
import com.plcoding.bakeryenglish.presentation.event.Event
import com.plcoding.bakeryenglish.presentation.screen.components.ComponentInsertVocabulary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateLessonViewModel @Inject constructor(
    private val dictonaryDao: DictonaryDao
):ViewModel() {
   private var _textFieldCreateLesson = mutableStateOf("");
   val textFieldCreateLesson: State<String> = _textFieldCreateLesson;


   var _listWordOfLesson = mutableStateOf(ArrayList<WordOfLesson>());
   var listWordOfLesson:State<List<WordOfLesson>> = _listWordOfLesson

   var listComposable = mutableStateListOf<@Composable() () -> WordOfLesson>(
      { ComponentInsertVocabulary() },{ ComponentInsertVocabulary()}
   )


   fun onEvent(event: Event){
      when(event){
         is Event.CreateLesson -> {
            _textFieldCreateLesson.value = event.lesson;
         }
         is Event.SaveVocabularyLesson ->{
            viewModelScope.launch {
               if (textFieldCreateLesson.value.isNotEmpty() && listWordOfLesson.value.isNotEmpty()){
                  dictonaryDao.insertLesson(Lesson(null,textFieldCreateLesson.value,listWordOfLesson.value))
               }
            }
         }
      }
   }
}