package com.mashup.mobalmobal.extensions

import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mashup.base.extensions.show
import com.mashup.mobalmobal.R

fun Fragment.showChargeBottomSheet(
    title: String,
    onPriceClick: (Int) -> Unit,
    onDirectClick: () -> Unit
) {
    BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme).show {
        setContentView(R.layout.bottom_sheet_donate)
        findViewById<TextView>(R.id.bottom_sheet_title)?.text = title
        findViewById<LinearLayout>(R.id.bottom_sheet_charge_1000)?.setOnClickListener {
            onPriceClick(1000)
        }
        findViewById<LinearLayout>(R.id.bottom_sheet_charge_2000)?.setOnClickListener {
            onPriceClick(2000)
        }
        findViewById<LinearLayout>(R.id.bottom_sheet_charge_5000)?.setOnClickListener {
            onPriceClick(5000)
        }
        findViewById<LinearLayout>(R.id.bottom_sheet_charge_10000)?.setOnClickListener {
            onPriceClick(10000)
        }
        findViewById<LinearLayout>(R.id.bottom_sheet_charge_50000)?.setOnClickListener {
            onPriceClick(50000)
        }
        findViewById<LinearLayout>(R.id.bottom_sheet_charge_100000)?.setOnClickListener {
            onPriceClick(100000)
        }
        findViewById<LinearLayout>(R.id.bottom_sheet_charge_writing)?.setOnClickListener {
            onDirectClick()
        }
    }
}

