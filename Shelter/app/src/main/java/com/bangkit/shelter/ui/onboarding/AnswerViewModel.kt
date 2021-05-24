package com.bangkit.shelter.ui.onboarding

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.bangkit.shelter.data.entity.Answer
import com.bangkit.shelter.data.room.ShelterDao
import com.bangkit.shelter.data.room.ShelterDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnswerViewModel(application: Application) : AndroidViewModel(application) {
    private var shelterDao: ShelterDao?
    private var shelterDatabase: ShelterDatabase?

    init {
        shelterDatabase = ShelterDatabase.getDatabase(application)
        shelterDao = shelterDatabase?.shelterDao()
    }

    fun insertAnswer(answer: Answer) {
        CoroutineScope(Dispatchers.IO).launch {
            shelterDao?.insertAnswer(answer)
        }

    }


}