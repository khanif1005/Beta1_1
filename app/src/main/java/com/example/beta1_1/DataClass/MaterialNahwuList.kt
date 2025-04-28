package com.example.beta1_1.DataClass

import android.os.Parcelable
import com.google.errorprone.annotations.Keep
import com.google.firebase.firestore.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@Parcelize
@IgnoreExtraProperties
@Keep
data class MaterialNahwuList(
    val bab: String = "",
    val materialName: String = "",
    val orderField: Long = 0L,
    val youtube: String = "",
    val quiz_id: String = "",
    val document_id: String = "",
    val materi: String = "",
    val isExam: Boolean
) : Parcelable {
    constructor() : this("", "", 0L, "", "", "", "", false)
}
