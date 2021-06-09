package com.bangkit.shelter.ui.onboarding

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.shelter.MainActivity
import com.bangkit.shelter.R
import com.bangkit.shelter.data.entity.User
import com.bangkit.shelter.databinding.ActivityQuestionBinding
import com.bangkit.shelter.ml.PersonalityCheck
import com.bangkit.shelter.ui.auth.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer

class QuestionActivity : AppCompatActivity() {
    private lateinit var questions: Array<String>
    private lateinit var binding: ActivityQuestionBinding
    private lateinit var firebaseUser: FirebaseUser

    val positiveButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(applicationContext,
        "Here we go", Toast.LENGTH_SHORT).show()
    }
    val negativeButtonClick = { dialog: DialogInterface, which: Int ->
        var intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AlertDialog.Builder(this)
            .setTitle("Warning")
            .setMessage("Before you use this app i hope you can answer some question we give\nAre You Ready?")
            .setPositiveButton("Yes I do", DialogInterface.OnClickListener(function = positiveButtonClick))
            .setNegativeButton("Not yet", DialogInterface.OnClickListener(negativeButtonClick))
            .show()


        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        val db = Firebase.firestore


        val arrayList = ArrayList<Int>()
        questions = resources.getStringArray(R.array.question_EXT)
        with(binding) {
            tvQuestion.text = questions[0]
            var i = 0
            arrayList.clear()
            button.setOnClickListener {
                if (ratingBar.rating.toInt() == 0) {
                    Toast.makeText(
                        this@QuestionActivity,
                        "Don't forget to choose your rating",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (i != questions.size - 1) {
                    val number = ratingBar.rating.toInt()
                    arrayList.add(number)
                    i += 1
                    tvQuestion.text = questions[i]
                    ratingBar.rating = 0F
                } else if (i == questions.size - 1) {
                    val number = ratingBar.rating.toInt()
                    arrayList.add(number)
                    button.text = "Finish"
                    tvQuestion.text = questions[i]
                }

                if (button.text == "Finish") {
                    onBoardingFinished()
                    binding.searchLoading.visibility = View.VISIBLE
                    binding.ratingBar.visibility = View.GONE
                    binding.tvQuestion.visibility = View.GONE
                    binding.button.visibility = View.GONE
                    val databaseReference: DatabaseReference =
                        FirebaseDatabase.getInstance().getReference("Users")
                    var byteBuffer: ByteBuffer = ByteBuffer.allocateDirect(200)
                    for (num in arrayList) {
                        byteBuffer.putInt(num)
                    }

                    val output_personality = run_model(byteBuffer)
                    databaseReference.addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(this@QuestionActivity, error.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (dataSnapShot: DataSnapshot in snapshot.children) {
                                val user = dataSnapShot.getValue(User::class.java)
                                if (user!!.userId.equals(firebaseUser.uid)) {
                                    val user_hash = hashMapOf(
                                        "userId" to user.userId,
                                        "email" to user.email,
                                        "username" to user.username,
                                        "profile_picture" to "",
                                        "personality" to output_personality
                                    )
                                    databaseReference.child(user.userId).setValue(user_hash)
                                        .addOnSuccessListener {
                                            Log.d("Firebase Load","Success")
                                        }
                                        .addOnFailureListener{
                                            Log.d("Firebase Load","Fail")
                                        }

                                    db.collection("User")
                                        .document(user.userId)
                                        .set(user_hash)
                                        .addOnSuccessListener {
                                            val intent = Intent(
                                                this@QuestionActivity,
                                                MainActivity::class.java
                                            )
                                            startActivity(intent)
                                            finish()
                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(
                                                this@QuestionActivity,
                                                "Failed ${e.message}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                }
                            }
                        }

                    })

                }
            }
        }
    }

    private fun onBoardingFinished() {

        val sharedPref = this.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }

    private fun run_model(byteBuffer: ByteBuffer): String {
        val model = PersonalityCheck.newInstance(this@QuestionActivity)

        // Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 50), DataType.FLOAT32)
        inputFeature0.loadBuffer(byteBuffer)

        // Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer.intArray

        // Releases model resources if no longer used.
        model.close()
        return outputFeature0[0].toString() + "," +
                outputFeature0[1].toString() + "," +
                outputFeature0[2].toString() + "," +
                outputFeature0[3].toString() + "," +
                outputFeature0[4].toString()
    }
}