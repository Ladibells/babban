package com.example.bgrecruitment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bgrecruitment.databinding.FragmentRecruitmentBinding
import com.example.bgrecruitment.databinding.FragmentRvRecruitmentBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class RvRecruitment : Fragment() {

    private lateinit var binding: FragmentRvRecruitmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentRvRecruitmentBinding.bind(view)
    }
}