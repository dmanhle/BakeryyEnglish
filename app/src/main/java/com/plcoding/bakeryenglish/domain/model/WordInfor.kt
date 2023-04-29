package com.plcoding.bakeryenglish.domain.model

import com.plcoding.bakeryenglish.domain.model.part_of_speech.PartOfSpeech


data class WordInfor(
    val word:String?,
    val pronounce:String?,
    val description: PartOfSpeech,
) {
}