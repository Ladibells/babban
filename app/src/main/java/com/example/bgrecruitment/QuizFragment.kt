package com.example.bgrecruitment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.bgrecruitment.data.Question
import com.example.bgrecruitment.data.viewmodel.QuizViewModel
import com.example.bgrecruitment.data.viewmodel.UserViewModel
import com.example.bgrecruitment.data.viewmodel.UserViewModelFactory
import com.example.bgrecruitment.databinding.FragmentQuizBinding
import com.example.bgrecruitment.db.UserDatabase


class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: QuizViewModel
    private lateinit var recruitmentId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recruitmentId = it.getString("recruitmentId") ?: ""
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dao = UserDatabase.getInstance(requireContext()).userDao()
        val recDao = UserDatabase.getInstance(requireContext()).recDao()
        val qustionDao = UserDatabase.getInstance(requireContext()).questionDao()
        val userResponseDao = UserDatabase.getInstance(requireContext()).userResponseDao()
        val recruitmentDao = UserDatabase.getInstance(requireContext()).recruitmentDao()
        val factory = UserViewModelFactory(dao, recDao, qustionDao, userResponseDao, recruitmentDao)
        viewModel = ViewModelProvider(this, factory).get(QuizViewModel::class.java)

        viewModel.getQuestions().observe(viewLifecycleOwner) { questions ->
            if (questions.isNotEmpty()) {
                val currentQuestion = viewModel.getCurrentQuestion()
                currentQuestion?.let {
                    if (it.options == null) {
                        // Category header question
                        displayCategoryHeader(it.category)
                        displayQuestion(it)
                        // Hide options or any other UI elements related to options
                        binding.optionsRadioGroup.visibility = View.GONE
                    } else {
                        // Regular question
                        displayCategoryHeader("")
                        displayQuestion(it)
                        displayOptions(it.options)
                        // Display options or any other UI elements related to options
                        binding.optionsRadioGroup.visibility = View.VISIBLE
                    }
                }
            } else {
                Toast.makeText(requireContext(), "No question available", Toast.LENGTH_LONG).show()
            }
        }

        binding.nextButton.setOnClickListener {
            val selectedOption = getSelectedOption()
            selectedOption?.let {
                viewModel.submitAnswer(it)
            }

            viewModel.displayNextQuestion()

            val currentQuestion = viewModel.getCurrentQuestion()
            currentQuestion?.let {
                displayCategoryHeader("")
                displayQuestion(it)
                displayOptions(it.options)
            } ?: run {
                showQuizResult()
            }
        }
    }

    private fun displayCategoryHeader(category: String) {
        binding.categoryHeaderTextView.text = "Category: $category"
    }

    private fun displayQuestion(question: Question) {
        binding.questionTextView.text = question.question
    }

    private fun displayOptions(options: List<String>) {
        binding.optionsRadioGroup.removeAllViews()
        for (option in options) {
            val radioButton = RadioButton(requireContext())
            radioButton.text = option
            binding.optionsRadioGroup.addView(radioButton)
        }
    }

    private fun getSelectedOption(): String? {
        val checkedRadioButtonId = binding.optionsRadioGroup.checkedRadioButtonId
        val radioButton = binding.optionsRadioGroup.findViewById<RadioButton>(checkedRadioButtonId)
        return radioButton?.text?.toString()
    }

    private fun showQuizResult() {
        val userAnswers = viewModel.getAllUserAnswers()
        val score = viewModel.getScore()

        val categoryHeadersMap: MutableMap<String, MutableList<Question>> = mutableMapOf()
        val answeredQuestionsMap: MutableMap<String, MutableList<Pair<Question, String>>> = mutableMapOf()

        for ((question, userAnswer) in userAnswers) {
            if (question.options.isEmpty()) {
                // Category header
                val category = question.category
                if (!categoryHeadersMap.containsKey(category)) {
                    categoryHeadersMap[category] = mutableListOf()
                }
                categoryHeadersMap[category]?.add(question)
            } else {
                // Answered question
                val category = question.category
                if (!answeredQuestionsMap.containsKey(category)) {
                    answeredQuestionsMap[category] = mutableListOf()
                }
                answeredQuestionsMap[category]?.add(question to userAnswer)
            }
        }

        val resultMessage = StringBuilder()
        resultMessage.append("Test completed!\n\n")

        // Exclude the category headers from the question count
        val totalQuestions = userAnswers.size - categoryHeadersMap.values.flatten().size
        resultMessage.append("Total Questions: $totalQuestions\n")
        resultMessage.append("Score: $score\n")

        for ((category, headers) in categoryHeadersMap) {
            resultMessage.append("\nCategory: $category\n\n")
            val questionsWithoutHeaders = answeredQuestionsMap[category]?.filterNot { it.first.options.isEmpty() }

            for ((question, userAnswer) in questionsWithoutHeaders ?: continue) {
                val isCorrect = userAnswer == question.answer

                resultMessage.append("Question: ${question.question}\n")
                resultMessage.append("Correct Answer: ${question.answer}\n")
                resultMessage.append("Your Answer: $userAnswer ")
                if (!isCorrect) {
                    resultMessage.append(" (Incorrect)")
                }
                resultMessage.append("\n\n")
            }
        }

        binding.optionsRadioGroup.visibility = View.GONE
        binding.nextButton.visibility = View.GONE
        binding.questionTextView.visibility = View.GONE
        binding.resultTextView.text = resultMessage.toString()

        viewModel.saveUserResponses() // Save the user responses to the database
    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


