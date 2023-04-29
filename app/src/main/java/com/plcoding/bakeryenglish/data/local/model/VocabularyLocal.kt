package com.plcoding.bakeryenglish.data.local.model

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.plcoding.bakeryenglish.domain.model.WordInfor
import com.plcoding.bakeryenglish.domain.model.part_of_speech.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

@Entity
data class VocabularyLocal(
    @PrimaryKey(autoGenerate = true)
    val id:Int?,
    val word:String?,
    val html:String?,
    val description:String?,
    val pronounce:String?
) {
    fun toWord(): WordInfor {
        val doc = Jsoup.parse(html)
        val noun = doc.select("h2:contains(danh từ)").first()
        val verb = doc.select("h2:contains(động từ)").first()
        val adj = doc.select("h2:contains(tính từ)").first()
        val intransitiveVerb = doc.select("h2:contains(nội động từ)").first()
        val transitiveVerb = doc.select("h2:contains(ngoại động từ)").first()
        val interjection = doc.select("h2:contains(thán từ)").first()


        Log.d("AAA",verb?.nextElementSibling().toString())

        Log.d("CCC",intransitiveVerb?.nextElementSibling().toString())

        val listNoun = convertHtmlToObjectReadable(noun).map {
            Noun(it.word,it.listExample)
        }
        val listVerb = convertHtmlToObjectReadable(verb).map {
            Adverb(it.word,it.listExample)
        }
        val listAdj = convertHtmlToObjectReadable(adj).map {
            Adjective(it.word,it.listExample)
        }
        val listIntransitive = convertHtmlToObjectReadable(intransitiveVerb).map {
            IntransitiveVerb(it.word,it.listExample)
        }
        val listTransitive = convertHtmlToObjectReadable(transitiveVerb).map {
            TransitiveVerb(it.word,it.listExample)
        }
        val listInterjection = convertHtmlToObjectReadable(interjection).map {
            Interjection(it.word,it.listExample)
        }


        Log.d("AAA",listNoun.toString())

        val partOfSpeech = PartOfSpeech(
            listNoun ,
            listVerb,
            listAdj,
            listIntransitive,
            listTransitive,
            listInterjection
        )
        return WordInfor(word, pronounce, partOfSpeech);
    }
    // get all define of TypeOfWord, example// number:số, số đếm
    fun convertHtmlToObjectReadable(html: Element?):ArrayList<Transform>
    {
        val text = Jsoup.parse(html?.nextElementSibling().toString()).select("ul").select("li");
        val list = ArrayList<Transform>();
        for (li in text) {
            if( !li.select("ul").isEmpty() or li.children().isEmpty()){
                val word:String = li.ownText().toString()
                val elementofNount = li.select("ul").select("li");
                val listExample = ArrayList<String>();
                for(i in elementofNount){
                    listExample.add(i.text().toString())
                }
                list.add(Transform(word,listExample))
            }
        }
        return list;
    }

    fun toDefineWithTypeOfWord(html: Element?):ArrayList<String>{
        val arrayList:ArrayList<String> = ArrayList()
        val listDefine: List<Unit> = Jsoup.parse(html?.nextElementSibling().toString()).select("ul").select(">li").map {
            if(!(it.ownText().endsWith(":"))){
                arrayList.add(it.ownText().toString())
            }
        }
        return arrayList
    }
}