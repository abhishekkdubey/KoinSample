package com.example.koin.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.koin.R
import com.example.koin.databinding.UserListItemBinding
import com.example.koin.db.User


class UserAdapter(private var data: List<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding: UserListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.user_list_item,
            parent,
            false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = data[position]
        holder.binding.user = user

        Glide
            .with(holder.itemView.context)
            .load(user.avatar)
            .centerCrop()
            .placeholder(R.drawable.ic_user)
            .into(holder.binding.profileImage)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    fun setData(assetsItems: List<User>) {
        data = assetsItems
        notifyDataSetChanged()
    }


    inner class UserViewHolder(var binding: UserListItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}