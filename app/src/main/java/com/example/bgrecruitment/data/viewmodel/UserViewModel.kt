package com.example.bgrecruitment.data.viewmodel


import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.bgrecruitment.data.Question
import com.example.bgrecruitment.data.Recruitment
import com.example.bgrecruitment.data.User
import com.example.bgrecruitment.data.UserResponse
import com.example.bgrecruitment.db.RecDao
import com.example.bgrecruitment.db.RecruitmentDao
import com.example.bgrecruitment.db.UserDao
import com.example.bgrecruitment.db.UserResponseDao
import com.example.bgrecruitment.repository.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.*

class UserViewModel(private val dao: UserDao, private val recDao: RecDao) : ViewModel() {

    val users = dao.getAllUsers()
    val recruitments: LiveData<List<Recruitment>> = recDao.getAllRecruitments()

    //User
    fun insertUser(user: User) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            dao.insertUser(user)
        }
    }

    suspend fun isEmailExists(email: String): Boolean {
        return withContext(Dispatchers.IO) {
            dao.isEmailExists(email)
        }
    }

    fun getUserByEmail(email: String): User? {
        return dao.getUserByEmail(email)
    }

    fun updateUser(user: User) = viewModelScope.launch {
        dao.updateUser(user)
    }

    fun deleteUser(user: User) = viewModelScope.launch {
        dao.deleteUser(user)
    }

    //Recruitment
    suspend fun insertRec(recruitment: Recruitment, context: Context, imageUri: Uri): Long {
        var rowId = -1L
        //First, save the image to internal storage
        val imageFile = File(context.filesDir, UUID.randomUUID().toString())
        val inputStream = context.contentResolver.openInputStream(imageUri)
        inputStream.use { input ->
            imageFile.outputStream().use { output ->
                input?.copyTo(output)
            }
        }

        // Then, update the officer identification object with the image path
        recruitment.IdImage = imageFile.absolutePath
        rowId = recDao.insertRec(recruitment)

        return rowId
    }


    fun updateRec(recruitment: Recruitment) = viewModelScope.launch {
        recDao.updateRec(recruitment)
    }

    fun updateRecWithImage(recruitment: Recruitment, context: Context, imageUri: Uri) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                // First, save the image to internal storage
                val imageFile = File(context.filesDir, UUID.randomUUID().toString())
                val inputStream = context.contentResolver.openInputStream(imageUri)
                inputStream.use { input ->
                    imageFile.outputStream().use { output ->
                        input?.copyTo(output)
                    }
                }

                // Then, update the officer identification object with the image path
                recruitment.IdImage = imageFile.absolutePath
                recDao.updateRec(recruitment)
            }
        }
    }

    fun deleteRec(recruitment: Recruitment) = viewModelScope.launch {
        recDao.deleteRec(recruitment)
    }

    // Update getAllRecruitments() method to return LiveData
    fun getAllRecruitments(): LiveData<List<Recruitment>> {
        return recruitments
    }

    fun editRecruitment(recruitment: Recruitment) {
        // Implement the logic to update the recruitment item in the database
        // You can use the recDao to update the recruitment item
        viewModelScope.launch {
            recDao.updateRec(recruitment)
        }
    }

    fun getScheduledRecruitments(): LiveData<List<Recruitment>> {
        return recDao.getScheduledRecruitments()
    }


}


class QuizViewModel(
    private val repository: QuizRepository,
    private val userResponseDao: UserResponseDao
) : ViewModel() {
    private var questionList: MutableList<Question>? = null
    private var currentQuestionIndex = 0
    private val userAnswers: MutableMap<Int, String> = mutableMapOf()
//    private var questionCategories: List<QuestionCategory>? = null


//    fun getQuestions(): LiveData<List<Question>> {
//        if (questionList == null) {
//            val allQuestions = repository.getQuestions()
//            val shuffledQuestions = allQuestions.shuffled()
//            questionList = mutableListOf()
//
//            val categoryQuestions: MutableMap<String, MutableList<Question>> = mutableMapOf()
//
//            // Categorize the questions by category
//            shuffledQuestions.forEach { question ->
//                if (!categoryQuestions.containsKey(question.category)) {
//                    categoryQuestions[question.category] = mutableListOf()
//                }
//                categoryQuestions[question.category]?.add(question)
//            }
//
//            // Retrieve 5 random questions from each category and add the category header
//            categoryQuestions.forEach { (category, questions) ->
//                questionList!!.add(
//                    Question(
//                        category = category,
//                        question = "CATEGORY: $category",
//                        answer = "",
//                        options = emptyList()
//                    )
//                ) // Pass an empty list for options
//                questionList!!.addAll(questions.take(5))
//            }
//
//        }
//        return MutableLiveData(questionList)
//
//    }


//    fun getQuestions(): LiveData<List<Question>> {
//        if (questionList == null) {
//            val allQuestions = repository.getQuestions()
//
//            // Filter questions by categories
//            val categoryAQuestions = allQuestions.filter { it.category == "A" }
//            val categoryBQuestions = allQuestions.filter { it.category == "B" }
//            val categoryCQuestions = allQuestions.filter { it.category == "C" }
//
//            // Randomize questions within each category
//            val randomizedCategoryAQuestions = categoryAQuestions.shuffled()
//            val randomizedCategoryBQuestions = categoryBQuestions.shuffled()
//            val randomizedCategoryCQuestions = categoryCQuestions.shuffled()
//
//            questionList = mutableListOf()
//
//            // Add category header and questions in order
//            questionList!!.add(
//                Question(
//                    category = "A",
//                    question = "CATEGORY: A",
//                    answer = "",
//                    options = emptyList()
//                )
//            )
//            questionList!!.addAll(randomizedCategoryAQuestions)
//
//            questionList!!.add(
//                Question(
//                    category = "B",
//                    question = "CATEGORY: B",
//                    answer = "",
//                    options = emptyList()
//                )
//            )
//            questionList!!.addAll(randomizedCategoryBQuestions)
//
//            questionList!!.add(
//                Question(
//                    category = "C",
//                    question = "CATEGORY: C",
//                    answer = "",
//                    options = emptyList()
//                )
//            )
//            questionList!!.addAll(randomizedCategoryCQuestions)
//
//            // Insert the generated questions into the database
//            viewModelScope.launch {
//                withContext(Dispatchers.IO) {
//                    repository.insertQuestions(questionList!!)
//                }
//            }
//        }
//        return MutableLiveData(questionList)
//    }


    fun getQuestions(): LiveData<List<Question>> {
        if (questionList == null) {
            val allQuestions = repository.getQuestions()

            // Filter questions by categories
            val categoryAQuestions = allQuestions.filter { it.category == "A" }.shuffled().take(5)
            val categoryBQuestions = allQuestions.filter { it.category == "B" }.shuffled().take(5)
            val categoryCQuestions = allQuestions.filter { it.category == "C" }.shuffled().take(5)

            questionList = mutableListOf()

            // Add category header and questions in order
            questionList!!.add(
                Question(
                    category = "A",
                    question = "CATEGORY A",
                    answer = "",
                    options = emptyList()
                )
            )
            questionList!!.addAll(categoryAQuestions)

            questionList!!.add(
                Question(
                    category = "B",
                    question = "CATEGORY B",
                    answer = "",
                    options = emptyList()
                )
            )
            questionList!!.addAll(categoryBQuestions)

            questionList!!.add(
                Question(
                    category = "C",
                    question = "CATEGORY C",
                    answer = "",
                    options = emptyList()
                )
            )
            questionList!!.addAll(categoryCQuestions)

            // Insert the generated questions into the database
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    repository.insertQuestions(questionList!!)
                }
            }
        }
        return MutableLiveData(questionList)
    }



    fun getCurrentQuestion(): Question? {
        return questionList?.getOrNull(currentQuestionIndex)
    }

    fun submitAnswer(answer: String): String {
        userAnswers[currentQuestionIndex] = answer

        // Calculate the quiz result message
        val resultMessage = StringBuilder()
        // ... build the resultMessage based on correct and incorrect answers

        return resultMessage.toString()
    }

    fun displayNextQuestion() {
        currentQuestionIndex++
    }

    fun getScore(): Int {
        var score = 0
        for ((index, question) in questionList?.withIndex() ?: emptyList<Question>().withIndex()) {
            val userAnswer = userAnswers[index]
            if (userAnswer == question.answer) {
                score++
            }
        }
        return score
    }

    fun getAllUserAnswers(): Map<Question, String> {
        val userAnswersMap: MutableMap<Question, String> = mutableMapOf()
        for ((index, question) in questionList?.withIndex() ?: emptyList<Question>().withIndex()) {
            val userAnswer = userAnswers[index]
            userAnswersMap[question] = userAnswer ?: ""
        }
        return userAnswersMap
    }

    fun saveUserResponses() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val userResponses = mutableListOf<UserResponse>()

                for ((index, question) in questionList?.withIndex() ?: emptyList<Question>().withIndex()) {
                    val userAnswer = userAnswers[index] ?: ""
                    val isCorrect = userAnswer == question.answer

                    val userResponse = UserResponse(
                        questionId = question.id,
                        response = userAnswer
                    )

                    userResponses.add(userResponse)
                }

                userResponseDao.saveUserResponses(userResponses)
            }
        }
    }


}


class RecruitmentViewModel(
    private val recruitmentDao: RecruitmentDao
) : ViewModel() {

    val recruitments: LiveData<List<Recruitment>> = recruitmentDao.getAllRecruitments()

    // Function to insert a new recruitment
    fun insertRecruitment(recruitment: Recruitment) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            recruitmentDao.insertRecruitment(recruitment)
        }
    }

    // Function to insert a new recruitment with image


    // Function to update a recruitment
    fun updateRecruitment(recruitment: Recruitment) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            recruitmentDao.updateRecruitment(recruitment)
        }
    }


    // Function to delete a recruitment
    fun deleteRecruitment(recruitment: Recruitment) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            recruitmentDao.deleteRecruitment(recruitment)
        }
    }

    fun getAllRecruitments(): LiveData<List<Recruitment>> {
        return recruitmentDao.getAllRecruitments()
    }
}

