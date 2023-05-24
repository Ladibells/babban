package com.example.bgrecruitment

import android.app.Application
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.bgrecruitment.data.User
import com.example.bgrecruitment.data.viewmodel.UserViewModel
import com.example.bgrecruitment.data.viewmodel.UserViewModelFactory
import com.example.bgrecruitment.databinding.FragmentSignUpBinding
import com.example.bgrecruitment.db.UserDatabase
import kotlinx.coroutines.launch


class SignUp : Fragment(R.layout.fragment_sign_up) {

    private lateinit var binding:FragmentSignUpBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var navController: NavController
    private lateinit var db: UserDatabase


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceSt`ate)
//
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSignUpBinding.bind(view)

        db = Room.databaseBuilder(
            requireContext(),
            UserDatabase::class.java, "user_data_database"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()

        val dao = UserDatabase.getInstance(requireContext()).userDao()
        val recDao = UserDatabase.getInstance(requireContext()).recDao()
        val qustionDao = UserDatabase.getInstance(requireContext()).questionDao()
        val userResponseDao = UserDatabase.getInstance(requireContext()).userResponseDao()
        val factory = UserViewModelFactory(dao, recDao, qustionDao, userResponseDao)
        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        binding.btnSignUp.setOnClickListener {
            insertDataToDatabase()
        }

        binding.tvSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_signUp_to_login)
        }

    }

    private fun insertDataToDatabase() {
        //viewModel = UserViewModel(Application())
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()

        lifecycleScope.launch {
            if (!emailAlreadyExists(email)) {
                if(inputCheck(name, email, password, confirmPassword)) {
                    if(password == confirmPassword){

                        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            Toast.makeText(requireContext(), "Invalid email format",
                                Toast.LENGTH_SHORT).show()
                            return@launch
                        }

                        // create user object
                        val user = User(0, name, email, password)
                        // add data to database
                        viewModel.insertUser(user)
                        Toast.makeText(activity, "Successfully added user $email", Toast.LENGTH_SHORT).show()
                        findNavController().navigate((R.id.action_signUp_to_login))
                        clearInput()
                    } else{
                        Toast.makeText(getActivity(), "passwords don't match", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(getActivity(), "please fill out all fields", Toast.LENGTH_SHORT).show()
                    //_errorToast.value = true
                }
            }else {
                Toast.makeText(getActivity(), "email already exists", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun inputCheck(name: String ,email: String, password: String, confirmPassword: String): Boolean {
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(email) && TextUtils.isEmpty(password) && TextUtils.isEmpty(confirmPassword))
    }

    private fun clearInput() {
        binding.etName.setText("")
        binding.etName.setText("")
        binding.etPassword.setText("")
        binding.etConfirmPassword.setText("")
    }

    private fun saveUserData() {
        viewModel.insertUser(
            User(0, binding.etName.text.toString(),binding.etEmail.text.toString(), binding.etPassword.text.toString())
        )
    }

    private fun emailAlreadyExists(email: String): Boolean {
        val user = db.userDao().getUserByEmail(email)
        return user != null
    }
}