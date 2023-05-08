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
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.bgrecruitment.data.User
import com.example.bgrecruitment.data.viewmodel.UserViewModel
import com.example.bgrecruitment.data.viewmodel.UserViewModelFactory
import com.example.bgrecruitment.databinding.FragmentSignUpBinding
import com.example.bgrecruitment.db.UserDatabase


class SignUp : Fragment(R.layout.fragment_sign_up) {

    private lateinit var binding:FragmentSignUpBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var navController: NavController


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceSt`ate)
//
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSignUpBinding.bind(view)

        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()

        val dao = UserDatabase.getInstance(requireContext()).userDao()
        val recDao = UserDatabase.getInstance(requireContext()).recDao()
        val factory = UserViewModelFactory(dao, recDao)
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
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()

        if(inputCheck(name, email, password, confirmPassword)) {
            if(password == confirmPassword){

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(requireContext(), "Invalid email format",
                        Toast.LENGTH_SHORT).show()
                    return
                }

                // create user object
                val user = User(0, name, email, password)
                // add data to database
                viewModel.insertUser(user)
                Toast.makeText(activity, "Successfully added user $email", Toast.LENGTH_SHORT).show()
                findNavController().navigate((R.id.action_signUp_to_login))
            } else{
                Toast.makeText(getActivity(), "passwords don't match", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(getActivity(), "please fill out all fields", Toast.LENGTH_SHORT).show()
            //_errorToast.value = true
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
}