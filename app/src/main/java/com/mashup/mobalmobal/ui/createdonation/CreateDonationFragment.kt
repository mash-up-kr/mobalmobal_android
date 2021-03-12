package com.mashup.mobalmobal.ui.createdonation

import android.view.LayoutInflater
import android.view.ViewGroup
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.mashup.mobalmobal.databinding.FragmentCreateDonationBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.funin.base.funinbase.base.BaseFragment

@AndroidEntryPoint
class CreateDonationFragment : BaseViewBindingFragment<FragmentCreateDonationBinding>() {
    private lateinit var viewPager: ViewPager2

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCreateDonationBinding {
        viewPager = binding.createDonationPager

        val pageAdapter = CreateDonationAdapter(this)
        viewPager.adapter = pageAdapter
        return FragmentCreateDonationBinding.inflate(inflater, container, false)
    }

    private inner class CreateDonationAdapter(fa: BaseFragment): FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = Companion.NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> CreateDonationNameFragment()
                1 -> CreateDonationInformationFragment()
                else -> CreateDonationCompleteFragment()
            }
        }

    }

    companion object {
        private const val NUM_PAGES = 3
    }
}

