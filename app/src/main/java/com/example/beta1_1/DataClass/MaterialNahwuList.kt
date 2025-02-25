package com.example.beta1_1.DataClass

import android.os.Parcelable
import com.google.firebase.firestore.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@Parcelize
@IgnoreExtraProperties
data class MaterialNahwuList(
    val bab: String ?= "",
    val materialName: String ?= "",
    val orderField: Long = 0L,
    val youtube: String ?= ""
) : Parcelable
