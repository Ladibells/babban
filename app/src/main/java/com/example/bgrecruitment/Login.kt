package com.example.bgrecruitment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.bgrecruitment.data.viewmodel.UserViewModel
import com.example.bgrecruitment.data.viewmodel.UserViewModelFactory
import com.example.bgrecruitment.databinding.FragmentLoginBinding
import com.example.bgrecruitment.db.UserDatabase


class Login : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController
    private lateinit var viewModel: UserViewModel
    private lateinit var db: UserDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
//        return inflater.inflate(R.layout.fragment_login, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = Room.databaseBuilder(
            requireContext(),
            UserDatabase::class.java, "user_data_database"
        ).build()

        //private val sharedPrefFile = "kotlinsharedpreference"

//        val sharedPrefs = context?.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
//        // Retrieve the user id from the Room database
//        val userId = db.userDao().findById(id) ?: -1
//
////        val user = UserDatabase.UserDao()
//        val editor = sharedPrefs?.edit()
//        editor?.putInt("user_id", userId)
//        editor?.apply()
//        sharedPrefs?.edit().putInt("USER_ID", viewModel.users.).apply()
        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmailSignIn.text.toString()
            val password = binding.etLogInPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val dao = UserDatabase.getInstance(requireActivity().application).userDao()
                val recDao = UserDatabase.getInstance(requireContext()).recDao()
                val qustionDao = UserDatabase.getInstance(requireContext()).questionDao()
                val userResponseDao = UserDatabase.getInstance(requireContext()).userResponseDao()
                val viewModel = UserViewModelFactory(dao, recDao,qustionDao, userResponseDao).create(UserViewModel::class.java)
                viewModel.users.observe(viewLifecycleOwner, Observer { users ->
                    val user = users.find { it.email == email && it.password == password }
                    if (user != null) {
                        findNavController().navigate(R.id.action_login_to_homepageFragment)
                        clearInput()
                    } else {
                        Toast.makeText(activity, "Invalid email or password", Toast.LENGTH_LONG).show()
                    }
                })
            } else {
                Toast.makeText(activity, "Invalid email or password", Toast.LENGTH_LONG).show()
            }

        }

        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_signUp)
        }
    }

    private fun clearInput() {
        binding.etEmailSignIn.setText("")
        binding.etLogInPassword.setText("")

    }



}