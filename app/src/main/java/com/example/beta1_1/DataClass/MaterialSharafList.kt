package com.example.beta1_1.DataClass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class MaterialSharafList(
    val bab: String ?= "",
    val materialName: String ?= "",
    val orderField: Long = 0L
) : Parcelable
