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
import com.example.bgrecruitment.data.Question
import com.example.bgrecruitment.data.viewmodel.QuizViewModel
import com.example.bgrecruitment.databinding.FragmentQuizBinding


class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: QuizViewModel



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentQuizBinding.bind(view)

        val viewModel: QuizViewModel by viewModels()

        viewModel.getQuestions().observe(viewLifecycleOwner) { questions ->
            if (questions.isNotEmpty()) {
                val currentQuestion = viewModel.getCurrentQuestion()
                currentQuestion?.let {
                    displayQuestion(binding, it)
                    displayOptions(binding, it.options)
                }
            }
        }

        binding.nextButton.setOnClickListener {
            val selectedOption = getSelectedOption(binding)
            selectedOption?.let {
                viewModel.submitAnswer(it)
            }

            val currentQuestion = viewModel.getCurrentQuestion()
            currentQuestion?.let {
                displayQuestion(binding, it)
                displayOptions(binding, it.options)
            } ?: showQuizResult()
        }
    }

    private fun displayQuestion(binding: FragmentQuizBinding, question: Question) {
        binding.questionTextView.text = question.question
    }

    private fun displayOptions(binding: FragmentQuizBinding, options: List<String>) {
        binding.optionsRadioGroup.removeAllViews()
        for (option in options) {
            val radioButton = RadioButton(requireContext())
            radioButton.text = option
            binding.optionsRadioGroup.addView(radioButton)
        }
    }

    private fun getSelectedOption(binding: FragmentQuizBinding): String? {
        val checkedRadioButtonId = binding.optionsRadioGroup.checkedRadioButtonId
        val radioButton = binding.optionsRadioGroup.findViewById<RadioButton>(checkedRadioButtonId)
        return radioButton?.text?.toString()
    }

    private fun showQuizResult() {
        val userAnswers = viewModel.getAllUserAnswers()
        val totalQuestions = userAnswers.size

        val incorrectAnswers = userAnswers.filter { (question, userAnswer) ->
            userAnswer != question.answer
        }

        val resultMessage = StringBuilder()
        resultMessage.append("Quiz completed!\n")
        resultMessage.append("Total Questions: $totalQuestions\n")
        resultMessage.append("Incorrect Answers:\n")

        for ((question, userAnswer) in incorrectAnswers) {
            resultMessage.append("\nQuestion: ${question.question}")
            resultMessage.append("\nYour Answer: $userAnswer")
            resultMessage.append("\nCorrect Answer: ${question.answer}\n")
        }

        Toast.makeText(requireContext(), resultMessage.toString(), Toast.LENGTH_LONG).show()
    }





}