package com.example.bgrecruitment

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.bgrecruitment.data.Recruitment
import com.example.bgrecruitment.data.viewmodel.UserViewModel
import com.example.bgrecruitment.data.viewmodel.UserViewModelFactory
import com.example.bgrecruitment.databinding.FragmentRecruitmentBinding
import com.example.bgrecruitment.db.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class RecruitmentFragment : Fragment(R.layout.fragment_recruitment) {
    private lateinit var binding: FragmentRecruitmentBinding
    private var datePickerDialog: DatePickerDialog? = null
    private var selectedFileUri: Uri? = null
    private lateinit var viewModel: UserViewModel
    private lateinit var navController: NavController
    private var selectedState = ""
    private var selectedRowId: Long = -1L

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentRecruitmentBinding.bind(view)
        //viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val dao = UserDatabase.getInstance(requireContext()).userDao()
        val recDao = UserDatabase.getInstance(requireContext()).recDao()
        val factory = UserViewModelFactory(dao, recDao)
        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        val sex = resources.getStringArray(R.array.Sex)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.list_item, sex)
        binding.dropDownSex.setAdapter(arrayAdapter)

//        val sex = listOf("Male", "female")
//        val adapterSex = ArrayAdapter(requireContext(), R.layout.list_item, sex)
//        binding.dropDownSex.setAdapter(adapterSex)

        val state = resources.getStringArray(R.array.State)
        val adapterState = ArrayAdapter(requireContext(), R.layout.state_list, state)
        binding.dropDownState.setAdapter(adapterState)

        binding.dropDownState.setOnItemClickListener { parent, view, position, id ->
            selectedState = state[position]
            //selectedState = parent?.selectedItem as String
            Toast.makeText(
                requireContext(),
                "Selected state is: $selectedState",
                Toast.LENGTH_LONG
            ).show()
        }

        val idType = resources.getStringArray(R.array.idType)
        val adapterIDType = ArrayAdapter(requireContext(), R.layout.id_list, idType)
        binding.dropDownIDType.setAdapter(adapterIDType)

        // take year and month and day from calendar
        val calendar = Calendar.getInstance()
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView(calendar)
            }
        }

        binding.etDOB.setOnClickListener {
            openDatePicker()
        }

        binding.btnRegister.setOnClickListener {
            saveRecruitmentData()
            Toast.makeText(activity, "Successfully registered", Toast.LENGTH_SHORT).show()
        }

        // Load the image from a file or some other source
        //val bitmap = BitmapFactory.decodeFile("/path/to/image.jpg")

        // Use a coroutine to compress the bitmap on a background thread
//        launch(Dispatchers.IO) {
//            // Convert the image to a byte array using the type converter
//            val outputStream = ByteArrayOutputStream()
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
//            val byteArray = outputStream.toByteArray()
//
//            // Save the byte array to the Room database using the view model
//            viewModel.saveImage(byteArray)
//        }

        binding.etImage.setOnClickListener {
            openFileChooser()
        }
    }

    private fun openFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            selectedFileUri = data.data
            binding.etImage.setImageURI(selectedFileUri)
        }
    }

//    private fun showFileChooser() {
//        val intent = Intent(Intent.ACTION_GET_CONTENT)
//        intent.type = "*/*"
//        intent.addCategory(Intent.CATEGORY_OPENABLE)
//        try {
//            startActivityForResult(Intent.createChooser(intent, "Select a file"), 100)
//        } catch (exception: Exception) {
//            Toast.makeText(requireContext(), "Please install a file manager", Toast.LENGTH_SHORT)
//                .show()
//        }
//    }
//
//    @Deprecated("Deprecated in Java")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
//            val uri: Uri? = data?.data
//            val path: String = uri?.path.toString()
//            val file = File(path)
//
//            val text = "Path: $path File name: ${file.name}".trimIndent()
//            //val bitmap = BitmapFactory.decodeFile(text)
//            //val text = "${file.name}".trimIndent()
//            val editableText = Editable.Factory.getInstance().newEditable(text)
//            binding.etImage.setText(editableText) // no error
//
//        }
//
//        super.onActivityResult(requestCode, resultCode, data)
//    }

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

    private fun saveRecruitmentData() {
        binding.apply {
            val recruitment = Recruitment(
                    0,
                    etName.text.toString(),
                    etNo.text.toString(),
                    dropDownSex.text,
                    etDOB.text,
                    etBVN.text.toString(),
                    etNIN.text.toString(),
                    dropDownState.text,
                    etLGA.text.toString(),
                    etHub.text.toString(),
                    etID.text.toString(),
                    dropDownIDType.text,
                    selectedFileUri.toString()
                )
//            val sharedPreferences =
//                requireActivity().getSharedPreferences(
//                    "my_preferences",
//                    Context.MODE_PRIVATE
//                )
//            viewModel.recruitment?.userId =
//                sharedPreferences.getString("USER_ID", "").toString()
//            mUserViewModel.registerTgl()
//            Toast.makeText(
//                context,
//                "User Successfully Registered",
//                Toast.LENGTH_SHORT
//            ).show()
//            mUserViewModel.tgl = null
            selectedFileUri?.let {
                lifecycleScope.launch(Dispatchers.IO) {
                    viewModel.insertRec(
                        recruitment,
                        requireContext(), // pass the context of the fragment
                        it // pass the selected image Uri
                    )
                }

            }
        }

        clearInput()
    }

//   private fun insertDataToDatabase( ) {
//
//        val name = binding.etName.text.toString()
//        val phoneNumber = binding.etNo.text.toString()
//        val sex = binding.dropDownSex.text
//        val dob = binding.etDOB.text
//        val bvn = binding.etBVN.text.toString()
//        val nin = binding.etNIN.text.toString()
//        val state = binding.dropDownState.text
//        val lga = binding.etLGA.text.toString()
//        val hub = binding.etHub.text.toString()
//        val id = binding.etID.text.toString()
//        val idType = binding.dropDownIDType.text
//        val image = binding.etImage.text
//
//        //val bitmap = BitmapFactory.decodeFile("/path/to/image.jpg")
//
//        if (name.isNullOrEmpty() && phoneNumber.isNullOrEmpty() && sex.isNullOrEmpty() && dob.isNullOrEmpty()
//            && bvn.isNullOrEmpty() && nin.isNullOrEmpty() && state.isNullOrEmpty() && lga.isNullOrEmpty()
//            && hub.isNullOrEmpty() && id.isNullOrEmpty() && idType.isNullOrEmpty() && image.isNullOrEmpty()
//        ) {
//            Toast.makeText(getActivity(), "please fill out all fields", Toast.LENGTH_SHORT).show()
//        } else {
//
//
//                // create user object
//                val recruitment = Recruitment(
//                    0,
//                    name,
//                    phoneNumber,
//                    sex,
//                    dob,
//                    bvn,
//                    nin,
//                    state,
//                    lga,
//                    hub,
//                    id,
//                    idType
//                )
//                // add data to database
//                viewModel.insertRec(recruitment)
//
//
//            Toast.makeText(activity, "Successfully added user $name", Toast.LENGTH_SHORT).show()
//            findNavController().navigate((R.id.action_signUp_to_login))
//        }
//    }

//    private fun inputCheck(name: String, phoneNumber: Int, sex: Editable, dob: Editable, bvn: Int, nin: String, state: Editable, lga: Int
//    hub: String, id: String, idType: Editable, image: Editable
//    ): Boolean {
//        return !(TextUtils.isEmpty(name) && phoneNumber )
//        //return !(TextUtils.isEmpty(name) && binding.etNo.isEmpty() && TextUtils.isEmpty(sex) && TextUtils.isEmpty(confirmPassword))
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