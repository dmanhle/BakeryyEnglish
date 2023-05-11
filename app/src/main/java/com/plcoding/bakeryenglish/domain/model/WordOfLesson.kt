package com.plcoding.bakeryenglish.domain.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class WordOfLesson(
    var word:String,
    var meaning: String
): Serializable {
}