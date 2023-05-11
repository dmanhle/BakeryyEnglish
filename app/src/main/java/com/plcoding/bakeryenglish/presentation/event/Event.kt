package com.plcoding.bakeryenglish.presentation.event

import com.plcoding.bakeryenglish.domain.model.Lesson
import com.plcoding.bakeryenglish.domain.model.WordOfLesson

sealed class Event {
   // Process Search
   class Search(val textInput:String):Event()
   class SelectedTab(val index:Int):Event()
   // - ProcessLesson
   class CreateLesson(val lesson: String):Event()
   class SaveVocabularyLesson() : Event()
   class EditVocabularyLesson(val lesson: Lesson):Event()

   // Handle HomeViewModel
   class LoadLesson():Event()
   class ClickToIntroduceLesson:Event()

   // Handle LearningLesson
   class RandomOptionOfAnswer(val indexAnswer:Int):Event()
   class ReLearningFlashCard(val list:ArrayList<WordOfLesson>):Event()
   // Handle IntroduceLesson
   class DeleteLesson(val id:Int) : Event()

   // Handle FlashCard Event

   class SkipQuetions(val wordOfLesson: WordOfLesson):Event()
}