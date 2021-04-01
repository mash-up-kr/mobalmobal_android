package com.mashup.mobalmobal.extensions

import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mashup.base.extensions.show
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.LayoutBottomSheetDonateBinding

fun Fragment.showChargeBottomSheet(
    title: String,
    onPriceClick: (Int) -> Unit,
    onDirectClick: () -> Unit,
    binding: LayoutBottomSheetDonateBinding
) {
    BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme).show {
        setContentView(R.layout.layout_bottom_sheet_donate)
        binding.bottomSheetTitle.text = title
        binding.bottomSheetCharge1000.setOnClickListener {
            onPriceClick(1000)
        }
        binding.bottomSheetCharge2000.setOnClickListener {
            onPriceClick(2000)
        }
        binding.bottomSheetCharge5000.setOnClickListener {
            onPriceClick(5000)
        }
        binding.bottomSheetCharge10000.setOnClickListener {
            onPriceClick(10000)
        }
        binding.bottomSheetCharge50000.setOnClickListener {
            onPriceClick(50000)
        }
        binding.bottomSheetCharge100000.setOnClickListener {
            onPriceClick(100000)
        }
        binding.bottomSheetChargeWriting.setOnClickListener {
            onDirectClick()
        }
    }
}

