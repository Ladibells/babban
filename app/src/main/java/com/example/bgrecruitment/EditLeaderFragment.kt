package com.example.bgrecruitment

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.bgrecruitment.data.viewmodel.RecruitmentViewModel
import com.example.bgrecruitment.databinding.FragmentEditLeaderBinding
import com.example.bgrecruitment.data.Recruitment
import com.example.bgrecruitment.data.viewmodel.UserViewModel
import com.example.bgrecruitment.data.viewmodel.UserViewModelFactory
import com.example.bgrecruitment.db.UserDatabase
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class EditLeaderFragment : Fragment() {

    private var _binding: FragmentEditLeaderBinding ?= null
    private val binding get() = _binding!!
    private lateinit var viewModel: RecruitmentViewModel
    private var datePickerDialog: DatePickerDialog? = null
    private var selectedFileUri: Uri? = null
    private lateinit var userViewModel: UserViewModel
    private lateinit var navController: NavController
    private var selectedState = ""
    private var selectedRowId: Long = -1L

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditLeaderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dao = UserDatabase.getInstance(requireContext()).userDao()
        val recDao = UserDatabase.getInstance(requireContext()).recDao()
        val qustionDao = UserDatabase.getInstance(requireContext()).questionDao()
        val userResponseDao = UserDatabase.getInstance(requireContext()).userResponseDao()
        val recruitmentDao = UserDatabase.getInstance(requireContext()).recruitmentDao()
        val factory = UserViewModelFactory(dao, recDao, qustionDao, userResponseDao, recruitmentDao)
        userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
        viewModel = ViewModelProvider(this, factory).get(RecruitmentViewModel::class.java)


        val sex = listOf("Male", "female")
        val adapterSex = ArrayAdapter(requireContext(), R.layout.list_item, sex)
        binding.dropDownSex.setAdapter(adapterSex)

        val idType = resources.getStringArray(R.array.idType)
        val adapterIDType = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, idType)
        binding.dropDownIDType.setAdapter(adapterIDType)

        binding.etDOB.setOnClickListener {
            openDatePicker()
        }

        binding.etImage.setOnClickListener {
            openFileChooser()
        }

        binding.btnRegister.setOnClickListener {
            updateRecruitmentData()
        }

        lifecycleScope.launch {
            val adapterState = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, resources.getStringArray(R.array.State))
            //val adapterState = ArrayAdapter(requireContext(), R.layout.list_item, state)
            binding.dropDownState.setAdapter(adapterState)
            binding.dropDownState.setValidator(object : AutoCompleteTextView.Validator{
                override fun isValid(text: CharSequence?): Boolean {
                    return adapterState.getPosition(text.toString()) != AdapterView.INVALID_POSITION
                }

                override fun fixText(invalidText: CharSequence?): CharSequence {
                    Toast.makeText(requireContext(), "State not found!", Toast.LENGTH_SHORT).show()
                    return ""
                }

            })

            //val lga = binding.etLGA

            binding.dropDownState.setOnItemClickListener { parent, view, position, id ->
                selectedState = adapterState.getItem(position).toString()
                binding.etLGA.setText("") // Clear the city dropdown before setting the new adapter
                val lgaAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, getLGA(selectedState))
                binding.etLGA.setAdapter(lgaAdapter)
                //selectedState = parent?.selectedItem as String
                Toast.makeText(
                    requireContext(),
                    "Selected state is: $selectedState",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        val recruitment: Recruitment? = arguments?.getParcelable("recruitment")

        // Prepopulate EditText fields with leader details
        binding.apply {
            etName.setText(recruitment?.Name)
            etNo.setText(recruitment?.PhoneNumber)
            dropDownSex.setText(recruitment?.Sex)
            etDOB.setText(recruitment?.DOB)
            etBVN.setText(recruitment?.BVN)
            etNIN.setText(recruitment?.NIN)
            dropDownState.setText(recruitment?.State)
            etLGA.setText(recruitment?.LGA)
            etHub.setText(recruitment?.Hub)
            etID.setText(recruitment?.GovID)
            dropDownIDType.setText(recruitment?.IdType)

            Glide.with(requireContext())
                .load(recruitment?.IdImage) // Assuming IdImage is the image URL or path
                .into(binding.etImage)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getLGA(state: String): Array<String> {
        lifecycleScope.launch {

        }
        return when (state) {
            getString(R.string.Abia) -> resources.getStringArray(R.array.Abia)
            getString(R.string.Adamawa) -> resources.getStringArray(R.array.Adamawa)
            getString(R.string.Akwa) -> resources.getStringArray(R.array.Akwa_Ibom)
            getString(R.string.Anambra) -> resources.getStringArray(R.array.Anambra)
            getString(R.string.Bauchi) -> resources.getStringArray(R.array.Bauchi)
            getString(R.string.Bayelsa) -> resources.getStringArray(R.array.Bayelsa)
            getString(R.string.Benue) -> resources.getStringArray(R.array.Benue)
            getString(R.string.Borno) -> resources.getStringArray(R.array.Borno)
            getString(R.string.Cross_River) -> resources.getStringArray(R.array.Cross_River)
            getString(R.string.Delta) -> resources.getStringArray(R.array.Delta)
            getString(R.string.Ebonyi) -> resources.getStringArray(R.array.Ebonyi)
            getString(R.string.Edo) -> resources.getStringArray(R.array.Edo)
            getString(R.string.Ekiti) -> resources.getStringArray(R.array.Ekiti)
            getString(R.string.Enugu) -> resources.getStringArray(R.array.Enugu)
            getString(R.string.Gombe) -> resources.getStringArray(R.array.Gombe)
            getString(R.string.Imo) -> resources.getStringArray(R.array.Imo)
            getString(R.string.Jigawa) -> resources.getStringArray(R.array.Jigawa)
            getString(R.string.Kaduna) -> resources.getStringArray(R.array.Kaduna)
            getString(R.string.Kano) -> resources.getStringArray(R.array.Kano)
            getString(R.string.Katsina) -> resources.getStringArray(R.array.Katsina)
            getString(R.string.Kebbi) -> resources.getStringArray(R.array.Kebbi)
            getString(R.string.Kogi) -> resources.getStringArray(R.array.Kogi)
            getString(R.string.Kwara) -> resources.getStringArray(R.array.Kwara)
            getString(R.string.Lagos) -> resources.getStringArray(R.array.Lagos)
            getString(R.string.Nasarawa) -> resources.getStringArray(R.array.Nasarawa)
            getString(R.string.Niger) -> resources.getStringArray(R.array.Niger)
            getString(R.string.Ogun) -> resources.getStringArray(R.array.Ogun)
            getString(R.string.Ondo) -> resources.getStringArray(R.array.Ondo)
            getString(R.string.Osun) -> resources.getStringArray(R.array.Osun)
            getString(R.string.Oyo) -> resources.getStringArray(R.array.Oyo)
            getString(R.string.Plateau) -> resources.getStringArray(R.array.Plateau)
            getString(R.string.Rivers) -> resources.getStringArray(R.array.Rivers)
            getString(R.string.Sokoto) -> resources.getStringArray(R.array.Sokoto)
            getString(R.string.Taraba) -> resources.getStringArray(R.array.Taraba)
            getString(R.string.Yobe) -> resources.getStringArray(R.array.Yobe)
            getString(R.string.Zamfara) -> resources.getStringArray(R.array.Zamfara)
            else -> emptyArray()
        }
    }

    private fun openFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, EditLeaderFragment.PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == EditLeaderFragment.PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            selectedFileUri = data?.data
            selectedFileUri?.let {
                binding.etImage.setImageURI(it)
            }
        }
    }

    private fun updateDateInView(calendar: Calendar) {
        val format = "dd-mm-yyyy"
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        binding.etDOB.setText(sdf.format(calendar.time))
    }

    private fun openDatePicker() {
        val calendar = Calendar.getInstance()
        val day = calendar[Calendar.DAY_OF_MONTH]
        val month = calendar[Calendar.MONTH]
        val year = calendar[Calendar.YEAR]

        datePickerDialog = DatePickerDialog(
            requireContext(),
            { View, year, monthOfYear, dayOfMonth ->
                val month = monthOfYear + 1
                var strMonth = "" + month
                var strDayOfMonth = "" + dayOfMonth
                if (month < 10) {
                    strMonth = "0$strMonth"
                }
                if (dayOfMonth < 10) {
                    strDayOfMonth = "0$strDayOfMonth"
                }
                val date = "$strDayOfMonth-$strMonth-$year"
                binding.etDOB!!.setText(date)
            }, year, month, day
        )
        datePickerDialog!!.setTitle("Select Date")
        datePickerDialog!!.show()
    }


//    private fun updateRecruitmentData() {
//        binding.apply {
//            val name = etName.text.toString().trim()
//            val phoneNumber = etNo.text.toString().trim()
//            val sex = dropDownSex.text
//            val dob = etDOB.text.toString()
//            val bvn = etBVN.text.toString().trim()
//            val nin = etNIN.text.toString().trim()
//            val state = dropDownState.text.toString().trim()
//            val lga = etLGA.text.toString().trim()
//            val hub = etHub.text.toString().trim()
//            val idNo = etID.text.toString().trim()
//            val idType = dropDownIDType.text
//            val image = selectedFileUri.toString()
//
//            when {
//                name.isEmpty() -> showSnackbar("Please enter your name")
//                phoneNumber.length < 10 -> showSnackbar("Phone number must be 10 digits")
//                sex.isEmpty() -> showSnackbar("Please select your sex")
//                dob.isEmpty() -> showSnackbar("You didn't pick a date")
//                nin.length < 10 -> showSnackbar("NIN must be 10 digits")
//                bvn.length < 10 -> showSnackbar("BVN must be 10 digits")
//                state.isEmpty() -> showSnackbar("You didn't select a state")
//                lga.isEmpty() -> showSnackbar("Please enter your LGA")
//                hub.isEmpty() -> showSnackbar("Please enter hub")
//                idNo.length < 10 -> showSnackbar("ID must be 10 digits")
//                idNo.length > 10 -> showSnackbar("ID cannot be more than 10 digits")
//                idType.isEmpty() -> showSnackbar("Please choose a type of ID")
//                image == null -> showSnackbar("You didn't select an image")
//                else -> {
//                    val recruitment = Recruitment(0, name, phoneNumber, sex, dob, bvn, nin, state, lga, hub, idNo, idType, image, false, false)
//                    selectedFileUri?.let {
//                        lifecycleScope.launch(Dispatchers.IO) {
//                            userViewModel.updateRecWithImage(
//                                recruitment,
//                                requireContext(),
//                                it
//                            )
//                        }
//                    }
//                    showSnackbar("Successfully Updated")
//                    clearInput()
//                    findNavController().navigate(R.id.action_editLeaderFragment_to_recruitedLeadersFragment)
//                }
//            }
//        }
//    }



    private fun updateRecruitmentData() {
        val name = binding.etName.text.toString().trim()
        val phoneNumber = binding.etNo.text.toString().trim()
        val sex = binding.dropDownSex.text
        val dob = binding.etDOB.text.toString().trim()
        val bvn = binding.etBVN.text.toString().trim()
        val nin = binding.etNIN.text.toString().trim()
        val state = binding.dropDownState.text.toString().trim()
        val lga = binding.etLGA.text.toString().trim()
        val hub = binding.etHub.text.toString().trim()
        val idNo = binding.etID.text.toString().trim()
        val idType = binding.dropDownIDType.text
        val image = selectedFileUri.toString()

        val errorMessage = validateInputFields(name, phoneNumber, sex, dob, bvn, nin, state, lga, hub, idNo, idType, image)
        if (errorMessage != null) {
            showSnackbar(errorMessage)
            return
        }

        val recruitment = Recruitment(0, name, phoneNumber, sex, dob, bvn, nin, state, lga, hub, idNo, idType, image, false, false)
        selectedFileUri?.let {
            lifecycleScope.launch(Dispatchers.IO) {
                userViewModel.updateRecWithImage(recruitment, requireContext(), it)
            }
        }

        showSnackbar("Successfully Updated")
        clearInput()
        findNavController().navigate(R.id.action_editLeaderFragment_to_recruitedLeadersFragment)
    }

    private fun validateInputFields(
        name: String,
        phoneNumber: String,
        sex: Editable,
        dob: String,
        bvn: String,
        nin: String,
        state: String,
        lga: String,
        hub: String,
        idNo: String,
        idType: Editable,
        image: String
    ): String? {
        return when {
            name.isEmpty() -> "Please enter your name"
            phoneNumber.length < 10 -> "Phone number must be 10 digits"
            sex.isEmpty() -> "Please select your sex"
            dob.isEmpty() -> "You didn't pick a date"
            nin.length < 10 -> "NIN must be 10 digits"
            bvn.length < 10 -> "BVN must be 10 digits"
            state.isEmpty() -> "You didn't select a state"
            lga.isEmpty() -> "Please enter your LGA"
            hub.isEmpty() -> "Please enter hub"
            idNo.length < 10 -> "ID must be 10 digits"
            idNo.length > 10 -> "ID cannot be more than 10 digits"
            idType.isEmpty() -> "Please choose a type of ID"
            image == null -> "You didn't select an image"
            else -> null
        }
    }






    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }




//    private fun updateRecruitmentData() {
//        binding.apply {
//
//            val name = etName.text.toString().trim()
//            val phoneNumber = etNo.text.toString().trim()
//            val sex = dropDownSex.text
//            val dob = etDOB.text.toString()
//            val bvn = etBVN.text.toString().trim()
//            val nin = etNIN.text.toString().trim()
//            val state = dropDownState.text.toString().trim()
//            val lga = etLGA.text.toString().trim()
//            val hub = etHub.text.toString().trim()
//            val idNo = etID.text.toString().trim()
//            val idType = dropDownIDType.text
//            val image = selectedFileUri.toString()
//
//            when {
//                name.isEmpty() -> Toast.makeText(activity, "Please enter your name", Toast.LENGTH_SHORT).show()
//                phoneNumber.length < 10 -> Toast.makeText(activity, "Phone number must be 10 digits", Toast.LENGTH_SHORT).show()
//                sex.isEmpty() -> Toast.makeText(activity, "Please select your sex", Toast.LENGTH_SHORT).show()
//                dob.isEmpty() -> Toast.makeText(activity, "you didn't pick a date", Toast.LENGTH_SHORT).show();
//                nin.length < 10 -> Toast.makeText(activity, "NIN must be 10 digits", Toast.LENGTH_SHORT).show()
//                bvn.length < 10 -> Toast.makeText(activity, "BVN must be 10 digits", Toast.LENGTH_SHORT).show()
//                state.isEmpty() -> Toast.makeText(activity, "you didn't select a state", Toast.LENGTH_SHORT).show()
//                lga.isEmpty() -> Toast.makeText(activity, "Please enter your LGA", Toast.LENGTH_SHORT).show()
//                hub.isEmpty() -> Toast.makeText(activity, "Please enter hub", Toast.LENGTH_SHORT).show()
//                idNo.length < 10 -> Toast.makeText(activity, "ID must be 10 digits", Toast.LENGTH_SHORT).show()
//                idNo.length > 10 -> Toast.makeText(activity, "ID can not be more than 10 digits", Toast.LENGTH_SHORT).show()
//                idType.isEmpty() -> Toast.makeText(activity, "Please choose a type of ID", Toast.LENGTH_SHORT).show()
//                image == null -> Toast.makeText(activity, "You didn't select image", Toast.LENGTH_SHORT).show()
//                else -> {
//                    val recruitment = Recruitment(0, name, phoneNumber, sex, dob, bvn, nin, state, lga, hub, idNo, idType, image, false, false)
//                    selectedFileUri?.let {
//                        lifecycleScope.launch(Dispatchers.IO) {
//                            userViewModel.updateRecWithImage(
//                                recruitment,
//                                requireContext(), // pass the context of the fragment
//                                it // pass the selected image Uri
//                            )
//                        }
//
//                    }
//                    Toast.makeText(activity, "Successfully registered", Toast.LENGTH_SHORT).show()
//                    clearInput()
////                    findNavController().navigate(R.id.)
//                }
//            }
//
//
//        }
//
//    }

    private fun clearInput() {
        binding.apply {
            etName.setText("")
            etNo.setText("")
            dropDownSex.setText("")
            etDOB.setText("")
            etBVN.setText("")
            etNIN.setText("")
            dropDownState.setText("")
            etLGA.setText("")
            etHub.setText("")
            etID.setText("")
            dropDownIDType.setText("")
            etImage.setImageURI(null)
        }
    }
}