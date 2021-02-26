package com.mashup.mobalmobal.ui.profile.presenter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.mobalmobal.databinding.*
import com.mashup.mobalmobal.ui.profile.domain.model.ProfileItem
import com.mashup.mobalmobal.ui.profile.presenter.viewholder.*

class ProfileAdapter(val clickListener: ProfileClickListener) :
    ListAdapter<ProfileItem, RecyclerView.ViewHolder>(ProfileItemDiffCallback()) {
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ProfileItem.User -> ViewType.USER.ordinal
            is ProfileItem.Point -> ViewType.POINT.ordinal
            is ProfileItem.DonationSummary -> ViewType.DONATION_SUMMARY.ordinal
            is ProfileItem.RequestDonation -> ViewType.REQUEST_DONATION.ordinal
            is ProfileItem.Donated -> ViewType.DONATED.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.USER.ordinal -> ProfileUserViewHolder(
                HolderProfileUserBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), clickListener
            )
            ViewType.POINT.ordinal -> ProfilePointViewHolder(
                HolderProfilePointBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), clickListener
            )
            ViewType.DONATION_SUMMARY.ordinal -> ProfileDonationSummaryViewHolder(
                HolderProfileDonationSummaryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), clickListener
            )
            ViewType.REQUEST_DONATION.ordinal -> ProfileRequestDonationViewHolder(
                HolderProfileRequestDonationBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), clickListener
            )
            ViewType.DONATED.ordinal -> ProfileDonatedViewHolder(
                HolderProfileDonatedBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), clickListener
            )
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProfileUserViewHolder -> {
                holder.bind(getItem(position) as ProfileItem.User)
            }
            is ProfilePointViewHolder -> {
                holder.bind(getItem(position) as ProfileItem.Point)
            }
            is ProfileDonationSummaryViewHolder -> {
                holder.bind(getItem(position) as ProfileItem.DonationSummary)
            }
            is ProfileRequestDonationViewHolder -> {
                holder.bind(getItem(position) as ProfileItem.RequestDonation)
            }
            is ProfileDonatedViewHolder -> {
                holder.bind(getItem(position) as ProfileItem.Donated)
            }
        }
    }

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

    interface ProfileClickListener {
        fun onProfileItemClick(view: View, position: Int)
    }
}