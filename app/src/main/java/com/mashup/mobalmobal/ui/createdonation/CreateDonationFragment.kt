package com.mashup.mobalmobal.ui.createdonation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.funin.base.funinbase.base.BaseViewBindingFragment
import com.mashup.mobalmobal.databinding.FragmentCreateDonationBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

@AndroidEntryPoint
class CreateDonationFragment : BaseViewBindingFragment<FragmentCreateDonationBinding>() {
    private lateinit var viewPager: ViewPager2

    companion object {
        private const val NUM_PAGES = 3
    }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCreateDonationBinding =
        FragmentCreateDonationBinding.inflate(inflater, container, false)


    override fun onSetupViews(view: View) {
        viewPager = binding.createDonationPager

        val pageAdapter = CreateDonationAdapter(this)
        viewPager.adapter = pageAdapter
    }

    private class CreateDonationAdapter(fa: Fragment): FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = Companion.NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> CreateDonationNameFragment()
                1 -> CreateDonationInformationFragment()
                else -> CreateDonationCompleteFragment()
            }
        }

    }

}

