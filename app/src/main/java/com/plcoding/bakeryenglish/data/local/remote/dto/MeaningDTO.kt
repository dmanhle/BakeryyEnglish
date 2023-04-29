package com.plcoding.bakeryenglish.data.local.remote.dto

data class MeaningDTO(
    val partOfSpeech:String,
    val definitions :List<DefinitionsDTO>,
    val synonyms:List<String>,
    val antonyms:List<String>
){
}