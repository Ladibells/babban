package com.example.bgrecruitment.repository

import androidx.lifecycle.LiveData
import com.example.bgrecruitment.data.Question
import com.example.bgrecruitment.data.Recruitment
import com.example.bgrecruitment.data.User
import com.example.bgrecruitment.db.QuestionDao
import com.example.bgrecruitment.db.RecDao
import com.example.bgrecruitment.db.UserDao
import com.example.bgrecruitment.db.UserResponseDao

class UserRepository(private val userDao: UserDao, private val recDao: RecDao) {

    val getAllUsers: LiveData<List<User>> = userDao.getAllUsers()
    val getAllRecruitments: LiveData<List<Recruitment>> = recDao.getAllRecruitments()

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun insertRec(recruitment: Recruitment) {
        recDao.insertRec(recruitment)
    }
}

class QuizRepository( private val questionDao: QuestionDao,
                      private val userResponseDao: UserResponseDao
) {
    suspend fun insertQuestions(questions: List<Question>) {
        questionDao.insertQuestions(questions)
    }

    fun getQuestions(): List<Question> {
        // Implement your logic to fetch the questions from a data source
        // and return the list of questions
        return listOf(
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
    }
}
