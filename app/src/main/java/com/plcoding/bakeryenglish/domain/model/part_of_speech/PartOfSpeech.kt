package com.plcoding.bakeryenglish.domain.model.part_of_speech

data class PartOfSpeech(
    val noun:List<Noun>?,
    val verb:List<Adverb>?,
    val adj:List<Adjective>?,
    val internerVerb:List<IntransitiveVerb>?,
    val externalVerb:List<TransitiveVerb>?,
    val interjection:List<Interjection>?,
) {
    fun defineSimple():ArrayList<String>{
        val list = ArrayList<String>()
        noun.let {
            if (it != null && it.isNotEmpty()) {
                list.add(it[0].wordOfNoun.toString())
            }
        }
        verb.let {
            if (it != null && it.isNotEmpty()) {
                list.add(it[0].wordOfAdverb.toString())
            }
        }
        adj.let {
            if (it != null && it.isNotEmpty()) {
                list.add(it[0].wordOfAdjective.toString())
            }
        }
        return list
    }
}