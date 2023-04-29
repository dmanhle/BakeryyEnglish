package com.plcoding.bakeryenglish.data.local.remote.dto

data class DefinitionsDTO(
    val definition:String,
    val synonyms:List<String>,
    val antonyms:List<String>
) {
}