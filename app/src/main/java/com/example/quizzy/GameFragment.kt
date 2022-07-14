package com.example.quizzy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.quizzy.databinding.FragmentGameBinding


class GameFragment : Fragment() {


    //data class created for purpose of dataBinding.
    data class Question(var text:String,
                     var answers:List<String>)

    private val questions:MutableList<Question> =mutableListOf(
        Question(text ="what is my name ?",
                answers = listOf("mohammed shahbaz","jackie chan","mike Tyson","None of these")) ,
        Question(text ="what is my age ?",
                answers = listOf("19","25","67","None of these"))


    //TODO  adding more items to the list.




    )

    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private var questionIndex=0
    private val numQuestions=Math.min((questions.size+1)/2,3)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //here we are inflating the layout for this game fragment
        val binding=DataBindingUtil.inflate<FragmentGameBinding>(inflater,R.layout.fragment_game,container,false)

        //now we will suffle the questions by using randdomizeQuestion() function
        randomizeQuestions()

        //bind this fragment class to the layout
        binding.game=this
        // Set the onClickListener for the submitButton
        binding.submitButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId
            // Do nothing if nothing is checked (id == -1)
            if (-1 != checkedId) {
                var answerIndex = 0
                when (checkedId) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                    R.id.thirdAnswerRadioButton -> answerIndex = 2
                    R.id.fourthAnswerRadioButton -> answerIndex = 3
                }
                // The first answer in the original question is always the correct one, so if our
                // answer matches, we have the correct answer.
                if (answers[answerIndex] == currentQuestion.answers[0]) {
                    questionIndex++
                    // Advance to the next question
                    if (questionIndex < numQuestions) {
                        currentQuestion = questions[questionIndex]
                        setQuestion()
                        binding.invalidateAll()
                    } else {
                        // We've won!  Navigate to the gameWonFragment.
                    }
                } else {
                    // Game over! A wrong answer sends us to the gameOverFragment.
                }
            }
        }
        return binding.root
    }
    // randomize the questions and set the first question
    private fun randomizeQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }
    // Sets the question and randomizes the answers.  This only changes the data, not the UI.
    // Calling invalidateAll on the FragmentGameBinding updates the data.
    private fun setQuestion() {
        currentQuestion = questions[questionIndex]
        // randomize the answers into a copy of the array
        answers = currentQuestion.answers.toMutableList()
        // and shuffle them
        answers.shuffle()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.my_title,questionIndex + 1, numQuestions)
    }


}