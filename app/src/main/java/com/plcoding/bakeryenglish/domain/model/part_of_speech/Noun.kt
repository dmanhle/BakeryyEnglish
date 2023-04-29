package com.plcoding.bakeryenglish.domain.model.part_of_speech

data class Noun(
    val wordOfNoun:String?,
    val listExampleOfNoun:ArrayList<String>?
):Transform(wordOfNoun,listExampleOfNoun) {
}