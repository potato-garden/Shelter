package com.bangkit.shelter.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bangkit.shelter.data.entity.Answer

@Database(entities = [Answer::class], version = 1, exportSchema = false)
abstract class ShelterDatabase: RoomDatabase() {
    abstract fun shelterDao(): ShelterDao

    companion object {
        var INSTANCE : ShelterDatabase? =null
        fun getDatabase(context: Context):ShelterDatabase?{
            if (INSTANCE == null){
                synchronized(ShelterDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, ShelterDatabase::class.java, "user_database").build()
                }
            }
            return INSTANCE
        }
    }
}