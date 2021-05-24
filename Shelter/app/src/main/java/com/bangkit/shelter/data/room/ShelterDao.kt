package com.bangkit.shelter.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import com.bangkit.shelter.data.entity.Answer


@Dao
interface ShelterDao{
    @Insert(onConflict = IGNORE)
    fun insertAnswer(answer: Answer)
}