package com.example.bgrecruitment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bgrecruitment.adapter.TestAdapter
import com.example.bgrecruitment.data.Question
import com.example.bgrecruitment.data.viewmodel.QuizViewModel
import com.example.bgrecruitment.databinding.FragmentTestBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class TestFragment : Fragment() {

    private lateinit var binding: FragmentTestBinding
    private lateinit var viewModel: QuizViewModel
    private lateinit var adapter: TestAdapter

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_test, container, false)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        viewModel = ViewModelProvider(requireActivity()).get(QuizViewModel::class.java)

        // Set up RecyclerView
        //val adapter = TestAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Observe questions from the ViewModel
        viewModel.getQuestions().observe(viewLifecycleOwner) { questions ->
            adapter.submitList(questions)
        }

//        val questionTextView: TextView = view.findViewById(R.id.questionTextView)
//        val optionsRadioGroup: RadioGroup = view.findViewById(R.id.optionsRadioGroup)
//        val nextButton: Button = view.findViewById(R.id.nextButton)
//
//        val quizViewModel = ViewModelProvider(requireActivity()).get(QuizViewModel::class.java)
//        val questionList = quizViewModel.getQuestionList().value
//        val currentQuestionIndex = quizViewModel.getCurrentQuestionIndex()
//
//        if (questionList != null && currentQuestionIndex < questionList.size) {
//            val currentQuestion = questionList[currentQuestionIndex]
//
//            questionTextView.text = currentQuestion.question
//
//            // Create and add radio buttons for each option
//            for (option in currentQuestion.options) {
//                val radioButton = RadioButton(requireContext())
//                radioButton.text = option.text
//                radioButton.id = option.id
//                optionsRadioGroup.addView(radioButton)
//            }
//
//            nextButton.setOnClickListener {
//                val selectedOptionId = optionsRadioGroup.checkedRadioButtonId
//                if (selectedOptionId != -1) {
//                    quizViewModel.submitAnswer(selectedOptionId)
//                    optionsRadioGroup.clearCheck()
//
//                    if (currentQuestionIndex < questionList.size - 1) {
//                        quizViewModel.displayNextQuestion()
//                        onViewCreated(view, savedInstanceState) // Re-invoke onViewCreated for the next question
//                    } else {
//                        // All questions answered, show the score
//                        val userAnswers = quizViewModel.getUserAnswers().value
//                        val score = calculateScore(userAnswers, questionList)
//
//                        // Display the score or perform any other action
//                        Toast.makeText(requireContext(), "Your score: $score", Toast.LENGTH_SHORT).show()
//                    }
//                } else {
//                    // No option selected, display an error message or perform any other action
//                    Toast.makeText(requireContext(), "Please select an option", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//
//    private fun calculateScore(userAnswers: List<Answer>?, questionList: List<Question>?): Int {
//        var score = 0
//
//        if (userAnswers != null && questionList != null) {
//            for (i in userAnswers.indices) {
//                if (userAnswers[i].selectedOption?.id == questionList[i].answer.id) {
//                    score++
//                }
//            }
//        }
//
//        return score
    }


}