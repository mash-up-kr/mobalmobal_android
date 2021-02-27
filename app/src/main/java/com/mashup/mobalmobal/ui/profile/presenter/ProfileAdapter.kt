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

class ProfileAdapter(private val clickListener: ProfileClickListener?) :
    ListAdapter<ProfileItem, RecyclerView.ViewHolder>(ProfileItemDiffCallback()) {

    companion object {
        private const val PAYLOAD_REQUEST_DONATION_TITLE = "payload_request_donation_title"
        private const val PAYLOAD_DONATED_TITLE = "payload_donated_title"
    }

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
                (getItem(position) as? ProfileItem.User)?.let { holder.bind(it) }
            }
            is ProfilePointViewHolder -> {
                (getItem(position) as? ProfileItem.Point)?.let { holder.bind(it) }
            }
            is ProfileDonationSummaryViewHolder -> {
                (getItem(position) as? ProfileItem.DonationSummary)?.let { holder.bind(it) }
            }
            is ProfileRequestDonationViewHolder -> {
                (getItem(position) as? ProfileItem.RequestDonation)?.let { holder.bind(it) }
            }
            is ProfileDonatedViewHolder -> {
                (getItem(position) as? ProfileItem.Donated)?.let { holder.bind(it) }
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
                    PAYLOAD_REQUEST_DONATION_TITLE -> {
                        (item as? ProfileItem.RequestDonation)?.let { item ->
                            (holder as? ProfileRequestDonationViewHolder)?.bindTitle(item)
                        }
                    }

                    PAYLOAD_DONATED_TITLE -> {
                        (item as? ProfileItem.Donated)?.let { item ->
                            (holder as? ProfileDonatedViewHolder)?.bindTitle(item)
                        }
                    }
                }
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
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProfileItem, newItem: ProfileItem): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: ProfileItem, newItem: ProfileItem): Any? {
            if (oldItem is ProfileItem.RequestDonation
                && newItem is ProfileItem.RequestDonation
            ) {
                if (oldItem.title == newItem.title
                    && oldItem.copy(newItem.title) == newItem
                ) {
                    return PAYLOAD_REQUEST_DONATION_TITLE
                }
            } else if (oldItem is ProfileItem.Donated
                && newItem is ProfileItem.Donated
            ) {
                if (oldItem.title == newItem.title
                    && oldItem.copy(newItem.title) == newItem
                ) {
                    return PAYLOAD_DONATED_TITLE
                }
            }
            return null
        }
    }

    interface ProfileClickListener {
        fun onProfileItemClick(view: View, position: Int)
    }
}