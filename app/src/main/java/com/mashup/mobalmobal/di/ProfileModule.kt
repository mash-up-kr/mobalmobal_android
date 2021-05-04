package com.mashup.mobalmobal.di

import androidx.fragment.app.Fragment
import com.mashup.base.image.GlideRequests
import com.mashup.mobalmobal.ui.profile.presenter.ProfileAdapter
import com.mashup.mobalmobal.ui.profile.presenter.ProfileFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object ProfileModule {

    @Provides
    fun provideProfileAdapter(fragment: Fragment, glideRequests: GlideRequests): ProfileAdapter =
        ProfileAdapter(fragment as? ProfileFragment, glideRequests)
}