package com.plcoding.bakeryenglish.presentation.state

import com.plcoding.bakeryenglish.data.local.model.VocabularyLocal
import com.plcoding.bakeryenglish.data.local.remote.dto.TypeOfAudio

data class SearchState(
    var listVocabulary:List<VocabularyLocal> = emptyList(),
    val isIndicatorLoading:Boolean = false, // when search, we need update listVocabluary and display into screen, indicator will is called
    val message:String = "",
    val audio:TypeOfAudio = TypeOfAudio("","")
) {
}