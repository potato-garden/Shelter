package com.bangkit.shelter.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Answer")
data class Answer(
    @PrimaryKey
    var no : Int,

    @ColumnInfo(name="rating")
    var rating: Int
): Parcelable