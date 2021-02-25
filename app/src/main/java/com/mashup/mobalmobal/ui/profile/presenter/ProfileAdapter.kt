package com.mashup.mobalmobal.ui.profile.presenter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.mobalmobal.ui.profile.domain.model.ProfileItem

class ProfileAdapter : ListAdapter<ProfileItem, RecyclerView.ViewHolder>(ProfileItemDiffCallback()){
    enum class ViewType {
        USER,
        POINT,
        DONATION_SUMMARY,
        REQUEST_DONATION,
        DONATED
    }

    class ProfileItemDiffCallback : DiffUtil.ItemCallback<ProfileItem>() {
        override fun areItemsTheSame(oldItem: ProfileItem, newItem: ProfileItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ProfileItem, newItem: ProfileItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is ProfileItem.User -> ViewType.USER.ordinal
            is ProfileItem.Point -> ViewType.POINT.ordinal
            is ProfileItem.DonationSummary -> ViewType.DONATION_SUMMARY.ordinal
            is ProfileItem.RequestDonation -> ViewType.REQUEST_DONATION.ordinal
            is ProfileItem.Donated -> ViewType.DONATED.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}