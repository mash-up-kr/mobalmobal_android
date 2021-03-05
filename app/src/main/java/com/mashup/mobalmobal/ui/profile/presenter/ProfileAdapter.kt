package com.mashup.mobalmobal.ui.profile.presenter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.base.image.GlideRequests
import com.mashup.mobalmobal.databinding.*
import com.mashup.mobalmobal.ui.profile.domain.model.ProfileItem
import com.mashup.mobalmobal.ui.profile.presenter.viewholder.*
import javax.inject.Inject

class ProfileAdapter @Inject constructor(
    private val clickListener: ProfileClickListener?,
    private val glideRequests: GlideRequests
) : ListAdapter<ProfileItem, RecyclerView.ViewHolder>(ProfileItemDiffCallback()) {

    companion object {
        private const val PAYLOAD_DONATION_TITLE = "payload_donation_title"
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ProfileItem.Header -> ViewType.HEADER.ordinal
            is ProfileItem.User -> ViewType.USER.ordinal
            is ProfileItem.DonationSummary -> ViewType.DONATION_SUMMARY.ordinal
            is ProfileItem.Donation -> ViewType.DONATION.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.HEADER.ordinal -> ProfileHeaderViewHolder(
                HolderProfileHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            ViewType.USER.ordinal -> ProfileUserViewHolder(
                HolderProfileUserBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                clickListener,
                glideRequests
            )
            ViewType.DONATION_SUMMARY.ordinal -> ProfileDonationSummaryViewHolder(
                HolderProfileDonationSummaryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), clickListener
            )
            ViewType.DONATION.ordinal -> ProfileDonationViewHolder(
                HolderProfileDonationBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                clickListener,
                glideRequests
            )
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProfileHeaderViewHolder -> {
                (getItem(position) as? ProfileItem.Header)?.let { holder.bind(it) }
            }
            is ProfileUserViewHolder -> {
                (getItem(position) as? ProfileItem.User)?.let { holder.bind(it) }
            }
            is ProfileDonationSummaryViewHolder -> {
                (getItem(position) as? ProfileItem.DonationSummary)?.let { holder.bind(it) }
            }
            is ProfileDonationViewHolder -> {
                (getItem(position) as? ProfileItem.Donation)?.let { holder.bind(it) }
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            val item = getItem(position)
            payloads.forEach { payload ->
                when (payload) {
                    PAYLOAD_DONATION_TITLE -> {
                        (item as? ProfileItem.Donation)?.let { item ->
                            (holder as? ProfileDonationViewHolder)?.bindTitle(item)
                        }
                    }
                }
            }
        }
    }

    enum class ViewType {
        HEADER,
        USER,
        DONATION_SUMMARY,
        DONATION
    }

    class ProfileItemDiffCallback : DiffUtil.ItemCallback<ProfileItem>() {
        override fun areItemsTheSame(oldItem: ProfileItem, newItem: ProfileItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProfileItem, newItem: ProfileItem): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: ProfileItem, newItem: ProfileItem): Any? {
            if (oldItem is ProfileItem.Donation
                && newItem is ProfileItem.Donation
            ) {
                if (oldItem.title == newItem.title
                    && oldItem.copy(newItem.title) == newItem
                ) {
                    return PAYLOAD_DONATION_TITLE
                }
            }
            return null
        }
    }

    interface ProfileClickListener {
        fun onProfileItemClick(view: View, position: Int)
    }
}