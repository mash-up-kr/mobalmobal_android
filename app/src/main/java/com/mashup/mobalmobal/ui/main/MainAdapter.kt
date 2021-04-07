package com.mashup.mobalmobal.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.*
import com.funin.base.funinbase.extension.inflate
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.ItemMainHeaderBinding
import com.mashup.mobalmobal.databinding.ItemMainMyDonationsBinding
import com.mashup.mobalmobal.databinding.ItemProgressDonationsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainAdapter(
    private val lifecycleCoroutineScope: CoroutineScope,
    private val myDonationAdapter: MyDonationAdapter,
    private val progressDonationAdapter: MainDonationAdapter
) : ListAdapter<MainAdapterItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private const val VIEW_TYPE_MY_DONATION = R.layout.item_main_my_donations
        private const val VIEW_TYPE_HEADER = R.layout.item_main_header
        private const val VIEW_TYPE_PROGRESS_DONATIONS = R.layout.item_progress_donations

        private val DIFF_CALLBACK =
            AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<MainAdapterItem>() {
                override fun areItemsTheSame(
                    oldItem: MainAdapterItem,
                    newItem: MainAdapterItem
                ): Boolean = oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: MainAdapterItem,
                    newItem: MainAdapterItem
                ): Boolean = oldItem == newItem
            }).build()
    }

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long =
        getItem(position)?.hashCode()?.toLong() ?: Long.MAX_VALUE

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MainAdapterItem.MyDonation -> VIEW_TYPE_MY_DONATION
            is MainAdapterItem.Header -> VIEW_TYPE_HEADER
            else -> VIEW_TYPE_PROGRESS_DONATIONS
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return parent.inflate(viewType, false).let {
            when (viewType) {
                VIEW_TYPE_MY_DONATION -> MyDonationViewHolder(
                    binding = ItemMainMyDonationsBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    adapter = myDonationAdapter
                )
                VIEW_TYPE_PROGRESS_DONATIONS -> ProgressDonationViewHolder(
                    binding = ItemProgressDonationsBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    adapter = progressDonationAdapter
                )
                else -> HeaderViewHolder(
                    ItemMainHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when {
            item is MainAdapterItem.MyDonation && holder is MyDonationViewHolder -> holder.bind(item)
            item is MainAdapterItem.Header && holder is HeaderViewHolder -> holder.bind(item)
            item is MainAdapterItem.ProgressDonation && holder is ProgressDonationViewHolder ->
                holder.bind(item)
        }
    }

    private fun MyDonationViewHolder.bind(item: MainAdapterItem.MyDonation) {
        myDonationAdapter.submitList(item.donations)
    }

    private fun HeaderViewHolder.bind(item: MainAdapterItem.Header) {
        textView.text = item.title
    }

    private fun ProgressDonationViewHolder.bind(item: MainAdapterItem.ProgressDonation) {
        lifecycleCoroutineScope.launch {
            progressDonationAdapter.submitData(item.donations)
        }
    }

    private class MyDonationViewHolder(
        binding: ItemMainMyDonationsBinding,
        adapter: MyDonationAdapter
    ) : RecyclerView.ViewHolder(binding.root) {
        val recyclerView: RecyclerView = binding.mainMyDonationRecycler
        val myDonationAdapter: MyDonationAdapter = adapter

        init {
            recyclerView.adapter = myDonationAdapter
        }
    }

    private class HeaderViewHolder(
        binding: ItemMainHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val textView: TextView = binding.root
    }

    private class ProgressDonationViewHolder(
        binding: ItemProgressDonationsBinding,
        adapter: MainDonationAdapter
    ) : RecyclerView.ViewHolder(binding.root) {
        val recyclerView: RecyclerView = binding.root
        val progressDonationAdapter: MainDonationAdapter = adapter

        init {
            recyclerView.apply {
                this.adapter = progressDonationAdapter
                layoutManager = GridLayoutManager(itemView.context, 2)
            }
        }
    }

    interface OnClickListener {
        fun onDonationClick(donationId: Int)
        fun onAddDonationClick()
    }
}