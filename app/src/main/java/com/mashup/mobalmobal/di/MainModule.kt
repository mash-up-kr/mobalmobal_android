package com.mashup.mobalmobal.di

import androidx.fragment.app.Fragment
import com.mashup.base.image.GlideRequests
import com.mashup.mobalmobal.ui.main.MainAdapter
import com.mashup.mobalmobal.ui.main.MainDonationAdapter
import com.mashup.mobalmobal.ui.main.MainFragment
import com.mashup.mobalmobal.ui.main.MyDonationAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object MainModule {

    @Provides
    fun provideMainDonationAdapter(
        fragment: Fragment,
        glideRequests: GlideRequests
    ): MainDonationAdapter = MainDonationAdapter(glideRequests, fragment as? MainFragment)

    @Provides
    fun provideMyDonationAdapter(
        fragment: Fragment
    ): MyDonationAdapter = MyDonationAdapter(fragment as? MainFragment)

    @Provides
    fun provideMainAdapter(
        myDonationAdapter: MyDonationAdapter,
        mainDonationAdapter: MainDonationAdapter
    ): MainAdapter = MainAdapter(myDonationAdapter, mainDonationAdapter)
}