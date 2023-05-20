package com.example.bgrecruitment.data.viewmodel



import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bgrecruitment.data.Question
import com.example.bgrecruitment.data.Recruitment
import com.example.bgrecruitment.data.User
import com.example.bgrecruitment.db.RecDao
import com.example.bgrecruitment.db.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*

class UserViewModel(private val dao: UserDao, private val recDao: RecDao): ViewModel() {

    val users = dao.getAllUsers()
    val recruitments = recDao.getAllRecruitments()

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
                inputStream.use {input ->
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

    fun deleteRec(recruitment: Recruitment) = viewModelScope.launch {
        recDao.deleteRec(recruitment)
    }

}

//class QuizViewModel1 : ViewModel() {
//    private val questionList: MutableLiveData<List<Question>> = MutableLiveData()
//    private val userAnswers: MutableLiveData<List<Answer>> = MutableLiveData()
//    private var currentQuestionIndex: Int = 0
//
//    init {
//        generateRandomQuestions()
//    }
//
//    fun getQuestionList(): LiveData<List<Question>> {
//        return questionList
//    }
//
//    fun getUserAnswers(): LiveData<List<Answer>> {
//        return userAnswers
//    }
//
//    fun getCurrentQuestionIndex(): Int {
//        return currentQuestionIndex
//    }
//
//    fun displayNextQuestion() {
//        if (currentQuestionIndex < questionList.value?.size?.minus(1) ?: 0) {
//            currentQuestionIndex++
//        }
//    }
//
//    fun submitAnswer(selectedOptionId: Int) {
//        val currentQuestion = questionList.value?.get(currentQuestionIndex)
//        val selectedOption = currentQuestion?.options?.find { it.id == selectedOptionId }
//        val answer = Answer(currentQuestion, selectedOption)
//
//        val currentAnswers = userAnswers.value?.toMutableList() ?: mutableListOf()
//        currentAnswers.add(answer)
//
//        userAnswers.value = currentAnswers
//    }
//
//    private fun generateRandomQuestions() {
//        // Generate and set a list of random questions
//        // You can use the questions from the Category A, B, and C here
//        // For simplicity, let's assume you have a list of pre-defined questions
//
//        val questions = mutableListOf<Question>()
//
//        // Add your Category A, B, and C questions to the list
//
//        questionList.value = questions
//    }
//}


class QuizViewModel : ViewModel() {
    private val questions: List<Question> = listOf(
        // Add all the questions from different categories here
        // Make sure you have a total of at least 15 questions (5 from each category)

        Question(
            category = "A",
            question = "Musa can make three payments on his bicycle. The bicycle cost him ₦3000. " +
                    "The first installment will be half of the cost of the bicycle. " +
                    "The second installment will be half of the balance. " +
                    "How much will Musa need to pay on his last installment?",
            options = listOf("₦750", "₦1000", "₦150", "₦250", "₦2500"),
            answer = "₦750"
        ),
        Question(
            category = "A",
            question = "A mechanic workshop charges a customer ₦850 to repair his bike. " +
                    "The bike parts cost ₦50 and the remainder was for labour. " +
                    "If the cost of labour is ₦100 per hour, how many hours of labour " +
                    "did it take the mechanic workshop to repair the bike?",
            options = listOf("1", "7", "8", "4", "10"),
            answer = "8"
        ),
        Question(
            category = "A",
            question = "Suppose you owe ₦40 to each of four friends, ₦10 to " +
                    "each of three friends, and ₦100 to your parents. " +
                    "If two friends each owe you ₦70, what is your net debt?",
            options = listOf("₦140", "₦220", "₦150", "₦80", "₦180"),
            answer = "₦150"
        ),
        Question(
            category = "A",
            question = "If Adamu issues 50 prepaid cards in 30 minutes, " +
                    "how many cards can he issue in 3 hours?",
            options = listOf("400", "20", "600", "150", "300"),
            answer = "300"
        ),
        Question(
            category = "A",
            question = "Mary, Bilikisu, and Amina need to prepare 500 plates of food. " +
                    "If Mary prepares 150 plates of food; Amina prepares 200 plates of food; " +
                    "how many plates of food should Bilikisu prepare?",
            options = listOf("300", "50", "350", "150", "500"),
            answer = "500"
        ),
        Question(
            category = "A",
            question = "Musa buys 6 sugarcanes for ₦100 each. He sells all 6 for a total " +
                    "of ₦1,000. How much total profit did he make?",
            options = listOf("₦600", "₦900", "₦300", "₦400", "₦6000"),
            answer = "₦6000"
        ),
        Question(
            category = "B",
            question = "What is the next number in the sequence: 1, 2, 3, 5, 8, 13, _?",
            options = listOf("21", "24", "34", "47", "52"),
            answer = "21"
        ),
        Question(
            category = "B",
            question = "What is the sum of 10 and 15?",
            options = listOf("25", "35", "45", "55", "65"),
            answer = "25"
        ),
        Question(
            category = "B",
            question = "What is the product of 5 and 7?",
            options = listOf("55", "65", "35", "45", "50"),
            answer = "35"
        ),
        Question(
            category = "B",
            question = "What's the difference between 20 and 10?",
            options = listOf("45", "23", "21", "10", "5"),
            answer = "10"
        ),
        Question(
            category = "B",
            question = "What is the quotient of 20 divided by 5?",
            options = listOf("9", "5", "4", "3", "6"),
            answer = "4"
        ),
        Question(
            category = "B",
            question = "What is the area of a square with a side length of 5cm?",
            options = listOf("75cm", "40cm", "25cm", "34cm", "90cm"),
            answer = "25cm"
        ),
        Question(
            category = "C",
            question = "Which of the following is an example of a traditional farming method?",
            options = listOf("Hydrophonics", "Aquaphonics", "Organic farming", "Vertical farming", "Conventional farming"),
            answer = "Conventional farming"
        ),
        Question(
            category = "C",
            question = "What is the primary purpose of using irrigation in agriculture?",
            options = listOf("Pest control", "Soil aeration", "Weed suppression",
                "Fertilizer application", "Water supply to crops"),
            answer = "Water supply to crops"
        ),
        Question(
            category = "C",
            question = "Which of the following is a common cereal crop.",
            options = listOf("Tomato", "Potato", "Wheat", "Carrot", "Lettuce"),
            answer = "Wheat"
        ),
        Question(
            category = "C",
            question = "What is the process of removing weeds from a cultivated field called?",
            options = listOf("Pollination", "Fertilization", "Germination", "Irrigation", "Weed control"),
            answer = "Weed control"
        ),
        Question(
            category = "C",
            question = "Which farming practice involves the cultivation of fruits and " +
                    "vegetables without the use of synthetic fertilizers and pesticides?\t",
            options = listOf("Organic farming", "Intensive farming", "Subsistence farming",
                "Commercial farming", "Precision farming"),
            answer = "Organic farming"
        ),
        Question(
            category = "C",
            question = "What is the purpose of crop rotation in farming?",
            options = listOf("Maximize water usage", "Improve soil fertility", "Increase crop diversity",
                "Control pest infestation", "Accelerate crop growth"),
            answer = "Improve soil fertility"
        )


    )

    private var currentQuestionIndex: Int = 0
    private val userAnswers: MutableMap<Int, String> = mutableMapOf()

    private val randomQuestions: List<Question>
        get() {
            val categoryQuestionsMap: MutableMap<String, MutableList<Question>> = mutableMapOf()

            // Group questions by category
            for (question in questions) {
                val category = question.category
                val categoryQuestions = categoryQuestionsMap.getOrPut(category) { mutableListOf() }
                categoryQuestions.add(question)
            }

            // Select 5 random questions from each category
            val randomQuestions: MutableList<Question> = mutableListOf()
            for (categoryQuestions in categoryQuestionsMap.values) {
                randomQuestions.addAll(categoryQuestions.shuffled().take(5))
            }

            return randomQuestions.shuffled()
        }

    fun getCurrentQuestion(): Question? {
        return if (currentQuestionIndex < randomQuestions.size) {
            randomQuestions[currentQuestionIndex]
        } else {
            null
        }
    }

    fun submitAnswer(answer: String) {
        userAnswers[currentQuestionIndex] = answer
    }

    fun displayNextQuestion() {
        currentQuestionIndex++
    }

    fun getScore(): Int {
        var score = 0

        for (questionIndex in userAnswers.keys) {
            val userAnswer = userAnswers[questionIndex]
            val question = randomQuestions[questionIndex]

            if (userAnswer == question.answer) {
                score++
            }
        }

        return score
    }

    fun getAllUserAnswers(): Map<Question, String> {
        val userAnswersMap: MutableMap<Question, String> = mutableMapOf()

        for (questionIndex in userAnswers.keys) {
            val userAnswer = userAnswers[questionIndex]
            val question = randomQuestions[questionIndex]
            userAnswersMap[question] = userAnswer ?: ""
        }

        return userAnswersMap
    }
}
