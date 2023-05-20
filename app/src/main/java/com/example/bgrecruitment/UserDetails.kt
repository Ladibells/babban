package com.example.bgrecruitment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bgrecruitment.adapter.UserAdapter
import com.example.bgrecruitment.data.User
import com.example.bgrecruitment.data.viewmodel.UserViewModel
import com.example.bgrecruitment.databinding.FragmentUserDetailsBinding


class UserDetails : Fragment(R.layout.fragment_user_details) {

    private lateinit var viewModel: UserViewModel
    private lateinit var binding: FragmentUserDetailsBinding
    private lateinit var adapter: UserAdapter
    private lateinit var recyclerView: RecyclerView

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_user_details, container, false)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentUserDetailsBinding.bind(view)
        recyclerView = binding.rvDetails


//        binding.apply {
//            rvDetails.hasFixedSize()
//            rvDetails.setLayoutManager(LinearLayoutManager(requireContext()))
//            rvDetails.itemAnimator = DefaultItemAnimator()
//        }


        // Recyclerview

        adapter = UserAdapter()
        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(activity)

        // UserViewModel
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
//        viewModel.users.observe(viewLifecycleOwner, Observer { user ->
//            adapter.setData(user)
//        })
    }
}