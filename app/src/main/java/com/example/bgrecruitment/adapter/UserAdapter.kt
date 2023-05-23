package com.example.bgrecruitment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bgrecruitment.R
import com.example.bgrecruitment.data.Recruitment
import com.example.bgrecruitment.data.User
import com.example.bgrecruitment.databinding.RvRecruitmentLayoutBinding
import com.example.bgrecruitment.databinding.UserDetailsListBinding

class UserAdapter: RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    private var userList = emptyList<User>()

//    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//
//    }

    class MyViewHolder(val binding: UserDetailsListBinding): RecyclerView.ViewHolder(binding.root)

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.MyViewHolder {
//        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_details_list, parent, false))
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.MyViewHolder {
        return MyViewHolder(UserDetailsListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: UserAdapter.MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.binding.idNo.text = currentItem.id.toString()
        holder.binding.name.text = currentItem.name.toString()
        holder.binding.mail.text = currentItem.email.toString()
        holder.binding.password.text = currentItem.password.toString()

    }

    override fun getItemCount(): Int {
       return userList.size
    }


    fun setData(newUserList: List<User>) {
        val diffUtil = UserDiffUtil(userList, newUserList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        userList = newUserList
        diffResults.dispatchUpdatesTo(this)
    }
}

class RecruitmentAdapter(

): RecyclerView.Adapter<RecruitmentAdapter.RecruitmentViewHolder>() {

     private var recList = emptyList<Recruitment>()

    class RecruitmentViewHolder(val binding: RvRecruitmentLayoutBinding): RecyclerView.ViewHolder(binding.root)

//    private val differCallback = object : DiffUtil.ItemCallback<User>() {
//        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
//            return oldItem == newItem
//        }
//
//    }

//    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecruitmentAdapter.RecruitmentViewHolder {
        return RecruitmentAdapter.RecruitmentViewHolder(
            RvRecruitmentLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )
    }

    override fun getItemCount(): Int {
        return recList.size
    }

    override fun onBindViewHolder(holder: RecruitmentViewHolder, position: Int) {
        holder.binding.root.setOnClickListener {

        }
        val currentItem = recList[position]
        holder.binding.idNo.text = currentItem.id.toString()
        holder.binding.fname.text = currentItem.Name.toString()
        holder.binding.idNo.text = currentItem.PhoneNumber.toString()
        holder.binding.DOB.text = currentItem.DOB.toString()
        holder.binding.sex.text = currentItem.Sex.toString()
        holder.binding.bvn.text = currentItem.BVN.toString()
        holder.binding.nin.text = currentItem.NIN.toString()
        holder.binding.state.text = currentItem.State.toString()
        holder.binding.LGA.text = currentItem.LGA.toString()
        holder.binding.hub.text = currentItem.Hub.toString()
        holder.binding.govID.text = currentItem.GovID.toString()
        holder.binding.idType.text = currentItem.IdType.toString()
    }

    fun setData(recruitment: List<Recruitment>) {
        this.recList = recruitment
        notifyDataSetChanged()
    }
//    interface OnCardClick{
//        fun cardClick()
//    }
}


//class RecruitmentAdapter(
//    private val recruitmentList: List<Recruitment>,
//    private val listener: OnItemClickListener
//) : RecyclerView.Adapter<RecruitmentAdapter.RecruitmentViewHolder>() {
//
//    interface OnItemClickListener {
//        fun onItemClick(position: Int)
//    }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecruitmentViewHolder {
//        val binding = RvRecruitmentLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return RecruitmentViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: RecruitmentViewHolder, position: Int) {
//        val currentRecruitment = recruitmentList[position]
//        holder.bind(currentRecruitment)
//    }
//
//    override fun getItemCount(): Int {
//        return recruitmentList.size
//    }
//
//    inner class RecruitmentViewHolder(private val binding: RvRecruitmentLayoutBinding) :
//        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
//
//        init {
//            itemView.setOnClickListener(this)
//        }
//
//        fun bind(recruitment: Recruitment) {
//            binding.fname.text = "Full Name: ${recruitment.Name}"
//            binding.idNo.text = "Phone Number: ${recruitment.PhoneNumber}"
//            binding.sex.text = "Sex: ${recruitment.Sex}"
//            binding.DOB.text = "Date of Birth: ${recruitment.DOB}"
//            binding.bvn.text = "BVN: ${recruitment.BVN}"
//            binding.nin.text = "NIN: ${recruitment.NIN}"
//            binding.state.text = "State: ${recruitment.State}"
//            binding.LGA.text = "LGA: ${recruitment.LGA}"
//            binding.hub.text = "Hub: ${recruitment.Hub}"
//            binding.govID.text = "ID: ${recruitment.GovID}"
//            binding.idType.text = "ID Type: ${recruitment.IdType}"
//        }
//
//        override fun onClick(v: View?) {
//            val position = adapterPosition
//            if (position != RecyclerView.NO_POSITION) {
//                listener.onItemClick(position)
//            }
//        }
//    }
//}


