package com.example.beta1_1.DataClass

import android.os.Parcelable
import com.google.firebase.firestore.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@Parcelize
@IgnoreExtraProperties
data class NahwuQuestions(
    val question_id: String = "",
    val question_text: String = "",
    val options: List<Option> = listOf(),
    val correct_answer: Int = 0
) : Parcelable

@Parcelize
@IgnoreExtraProperties
data class Option(
    val id: Int = 0,
    val text: String = ""
) : Parcelable


@IgnoreExtraProperties
data class Quiz(
    val questions: List<NahwuQuestions> = listOf()
)