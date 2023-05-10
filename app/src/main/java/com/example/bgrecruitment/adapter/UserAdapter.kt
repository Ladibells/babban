package com.example.bgrecruitment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    fun setData(user: List<User>) {
        this.userList = user
        notifyDataSetChanged()
    }
}

class RecruitmentAdapter: RecyclerView.Adapter<RecruitmentAdapter.RecruitmentViewHolder>() {

    private var recList = emptyList<Recruitment>()

    class RecruitmentViewHolder(val binding: RvRecruitmentLayoutBinding): RecyclerView.ViewHolder(binding.root)

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
}