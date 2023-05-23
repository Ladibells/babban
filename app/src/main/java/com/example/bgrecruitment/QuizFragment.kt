package com.example.bgrecruitment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.bgrecruitment.data.Question
import com.example.bgrecruitment.data.viewmodel.QuizViewModel
import com.example.bgrecruitment.databinding.FragmentQuizBinding


class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: QuizViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//       // binding = FragmentQuizBinding.bind(view)
//
//        val questionTextView = binding.questionTextView
//        val optionsRadioGroup = binding.optionsRadioGroup
//        val nextButton = binding.nextButton
//
//        // Initialize the ViewModel
//        viewModel = ViewModelProvider(this).get(QuizViewModel::class.java)
//
//        // Observe the current question LiveData
//        viewModel.currentQuestion.observe(viewLifecycleOwner) { question ->
//            questionTextView.text = question.question
//            optionsRadioGroup.removeAllViews()
//
//            // Create radio buttons for each option
//            for (option in question.options) {
//                val radioButton = RadioButton(requireContext())
//                radioButton.text = option
//                optionsRadioGroup.addView(radioButton)
//            }
//        }
//
//        // Set a click listener for the next button
//        nextButton.setOnClickListener {
//            val checkedRadioButtonId = optionsRadioGroup.checkedRadioButtonId
//            val selectedOptionIndex = optionsRadioGroup.indexOfChild(
//                optionsRadioGroup.findViewById(checkedRadioButtonId)
//            )
//
//            val currentQuestion = viewModel.currentQuestion.value
//            if (currentQuestion != null && selectedOptionIndex != -1) {
//                val selectedOption = currentQuestion.options[selectedOptionIndex]
//                viewModel.submitAnswer(selectedOption)
//            }
//
//            viewModel.displayNextQuestion()
//            optionsRadioGroup.clearCheck()
//        }
//
//
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//        binding.questionTextView.text = quizViewModel.getCurrentQuestion()?.question
//
//        binding.nextButton.setOnClickListener {
//            quizViewModel.displayNextQuestion()
//            binding.questionTextView.text = quizViewModel.getCurrentQuestion()?.question
//        }

//        binding.submitButton.setOnClickListener {
//            val answer = // Get the user's selected answer
//                quizViewModel.submitAnswer(answer)
//
//            // Optionally, move to the next question after submitting the answer
//            quizViewModel.displayNextQuestion()
//            questionTextView.text = quizViewModel.getCurrentQuestion()?.question
//        }


//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val binding = FragmentQuizBinding.bind(view)
//
//        val viewModel: QuizViewModel by viewModels()
//        val adapter = OptionsAdapter()
//
//        viewModel.getQuestions().observe(viewLifecycleOwner) { questions ->
//            if (questions.isNotEmpty()) {
//                val currentQuestion = viewModel.getCurrentQuestion()
//                currentQuestion?.let {
//                    displayQuestion(binding, it)
//                    adapter.submitList(it.options)
//                }
//            }
//        }
//
//        binding.optionsRecyclerView.adapter = adapter
//
//        binding.nextButton.setOnClickListener {
//            val selectedOption = adapter.getSelectedOption()
//            selectedOption?.let {
//                viewModel.submitAnswer(it)
//            }
//
//            val currentQuestion = viewModel.getCurrentQuestion()
//            currentQuestion?.let {
//                displayQuestion(binding, it)
//                adapter.submitList(it.options)
//            } ?: showQuizResult()
//        }
//    }

//    private fun displayQuestion(binding: FragmentQuizBinding, question: Question) {
//        binding.questionTextView.text = question.question
//    }
//
//    private fun showQuizResult() {
//        // Show the quiz result or perform any other actions
//    }


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
        // Show the quiz result or perform any other actions
    }



}