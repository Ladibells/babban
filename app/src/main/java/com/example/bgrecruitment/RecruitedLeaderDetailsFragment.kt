package com.example.bgrecruitment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.bgrecruitment.data.Recruitment
import com.example.bgrecruitment.databinding.FragmentRecruitedLeaderDetailsBinding


class RecruitedLeaderDetailsFragment : Fragment() {
    private var _binding: FragmentRecruitedLeaderDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecruitedLeaderDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the recruitment details from arguments
        val args: RecruitedLeaderDetailsFragmentArgs by navArgs()
        val recruitment: Recruitment = args.recruitment

        // Display the recruitment details in the appropriate views
        binding.fullNameTextView.text = recruitment.Name
        binding.phoneNumberTextView.text = recruitment.PhoneNumber
        binding.sexTextView.text = recruitment.Sex
        binding.dateOfBirthTV.text = recruitment.DOB
        binding.stateTextView.text = recruitment.State
        binding.bvnTV.text = recruitment.BVN
        binding.ninTextView.text = recruitment.NIN
        binding.lgaTextView.text = recruitment.LGA
        binding.hubTextView.text = recruitment.Hub
        binding.govIDTextView.text = recruitment.GovID
        binding.govIdTypeTextView.text = recruitment.IdType

        // Load and set the image using Glide or Picasso
        Glide.with(requireContext())
            .load(recruitment.IdImage) // Assuming IdImage is the image URL or path
            .into(binding.profileImageView)

        // Set other TextViews and ImageView with corresponding recruitment properties

        binding.editBtn.setOnClickListener {
//            val action = RecruitedLeaderDetailsFragmentDirections.actionRecruitedLeaderDetailsFragmentToEditLeaderFragment(recruitment)
//            findNavController().navigate(action)

            val bundle = Bundle().apply {
                putParcelable("recruitment", recruitment)
            }
            findNavController().navigate(R.id.action_recruitedLeaderDetailsFragment_to_editLeaderFragment, bundle)


        }

    }

}