package com.plcoding.bakeryenglish.data.local.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.plcoding.bakeryenglish.domain.model.WordInfor
import com.plcoding.bakeryenglish.domain.model.WordOfLesson

class Converter {
    @TypeConverter
    fun fromVocabluaryJson(json: String): List<WordInfor> {
        val listType = object : TypeToken<List<WordInfor>>() {}.type
        return Gson().fromJson(json, listType)
    }

    @TypeConverter
    fun toVocabularyJson(list: List<WordInfor>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
//    @TypeConverter
//    fun fromJson(json: String): ArrayList<String> {
//        val listType = object : TypeToken<List<String>>() {}.type
//        return Gson().fromJson(json, listType)
//    }
//
//    @TypeConverter
//    fun toJson(list: ArrayList<String>): String {
//        val gson = Gson()
//        return gson.toJson(list)
//    }

    @TypeConverter
    fun fromJson_WordOfLesson(json: String): List<WordOfLesson> {
        val listType = object : TypeToken<List<WordOfLesson>>() {}.type
        return Gson().fromJson(json, listType)
    }

    @TypeConverter
    fun toJson_WordOfLesson(list: List<WordOfLesson>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}