package org.exercise.remitconnectclaude.data.remote

data class CountryExtraResponseItem(
    val flags: Flags,
    val idd: Idd
) {
    data class Flags(
        val alt: String,
        val png: String,
        val svg: String
    )

    data class Idd(
        val root: String,
        val suffixes: List<String>
    )
}