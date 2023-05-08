package com.example.bgrecruitment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.snapshots.Snapshot.Companion.observe
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.bgrecruitment.data.viewmodel.UserViewModel
import com.example.bgrecruitment.data.viewmodel.UserViewModelFactory
import com.example.bgrecruitment.databinding.FragmentLoginBinding
import com.example.bgrecruitment.db.UserDatabase


class Login : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController
    private lateinit var viewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)
        //private val sharedPrefFile = "kotlinsharedpreference"

//        val sharedPrefs = context?.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
//        val editor = sharedPrefs?.edit()
//        editor?.putInt("user_id", 9)
//        editor?.apply()
//        sharedPrefs?.edit().putInt("USER_ID", viewModel.users.).apply()
        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmailSignIn.text.toString()
            val password = binding.etLogInPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val dao = UserDatabase.getInstance(requireActivity().application).userDao()
                val recDao = UserDatabase.getInstance(requireContext()).recDao()
                val viewModel = UserViewModelFactory(dao, recDao).create(UserViewModel::class.java)
                viewModel.users.observe(viewLifecycleOwner, Observer { users ->
                    val user = users.find { it.email == email && it.password == password }
                    if (user != null) {
                        findNavController().navigate(R.id.action_login_to_homepageFragment)
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
}