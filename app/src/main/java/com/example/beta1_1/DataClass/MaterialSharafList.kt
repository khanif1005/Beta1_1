package com.example.beta1_1.DataClass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class MaterialSharafList(
    var bab: String ?= "",
    var materialName: String ?= "",
    var orderField: Long = 0L
) : Parcelable
