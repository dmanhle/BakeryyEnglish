package com.plcoding.bakeryenglish.data.local.remote.dto

import android.util.Log

data class WordInforDTO(
    val word:String,
    val phonetic:String,
    val phonetics:List<PhoneticsDTO>,
    val meaning:List<MeaningDTO>
) {
    fun toAudio():TypeOfAudio {
       val list = phonetics.map {it->
           it.audio
       }
       var audio = TypeOfAudio("","");
       if(list.size == 2){
           audio = TypeOfAudio(list[0],list[1])
       }else if(list.size == 1){
           audio = audio.copy(UsAudio = list[0])
       }
        Log.d("AAA",audio.toString())
       return audio
    }
}