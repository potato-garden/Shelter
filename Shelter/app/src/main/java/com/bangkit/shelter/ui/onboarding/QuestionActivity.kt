package com.bangkit.shelter.ui.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.bangkit.shelter.R
import com.bangkit.shelter.data.entity.Answer
import com.bangkit.shelter.databinding.ActivityQuestionBinding

class QuestionActivity : AppCompatActivity() {
    private lateinit var questions: Array<String>
    private lateinit var activityQuestionBinding: ActivityQuestionBinding
    private lateinit var viewModel: AnswerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityQuestionBinding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(activityQuestionBinding.root)

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory(application))
            .get(AnswerViewModel::class.java)


        questions = resources.getStringArray(R.array.question_EXT)

        with(activityQuestionBinding){
            tvQuestion.text = questions[0]
            var i = 1

            button.setOnClickListener{
                if (i!=questions.size-1){
                    val number = ratingBar.rating
                    viewModel.insertAnswer(Answer(i, number.toInt()))
                    Log.d("test", number.toString())
                    i+=1
                    tvQuestion.text = questions[i]
                }else if(i == questions.size-1){
                    button.text = "Finish"
                    tvQuestion.text = questions[i]
                }
            }
        }
    }
}