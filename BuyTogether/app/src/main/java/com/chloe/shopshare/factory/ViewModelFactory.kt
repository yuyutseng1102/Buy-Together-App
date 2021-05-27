package com.chloe.shopshare.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chloe.shopshare.MainViewModel
import com.chloe.shopshare.shop.ShopViewModel
import com.chloe.shopshare.manage.groupmessage.GroupMessageViewModel
import com.chloe.shopshare.data.source.Repository
import com.chloe.shopshare.detail.item.DetailDescriptionViewModel
import com.chloe.shopshare.host.HostViewModel
import com.chloe.shopshare.host.item.GatherConditionViewModel
import com.chloe.shopshare.home.item.HomeCollectViewModel
import com.chloe.shopshare.home.item.HomePageViewModel
import com.chloe.shopshare.login.LoginViewModel
import com.chloe.shopshare.notify.NotifyViewModel
import com.chloe.shopshare.profile.ProfileViewModel

/**
 *
 * Factory for all ViewModels.
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
        private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
            with(modelClass) {
                when {
                    isAssignableFrom(MainViewModel::class.java) ->
                        MainViewModel(repository)

                    isAssignableFrom(LoginViewModel::class.java) ->
                        LoginViewModel(repository)

                    isAssignableFrom(HomePageViewModel::class.java) ->
                        HomePageViewModel(repository)

                    isAssignableFrom(HomeCollectViewModel::class.java) ->
                        HomeCollectViewModel(repository)

                    isAssignableFrom(HostViewModel::class.java) ->
                        HostViewModel(repository)

                    isAssignableFrom(GatherConditionViewModel::class.java) ->
                        GatherConditionViewModel(repository)

                    isAssignableFrom(ShopViewModel::class.java) ->
                        ShopViewModel(repository)

                    isAssignableFrom(GroupMessageViewModel::class.java) ->
                        GroupMessageViewModel(repository)

                    isAssignableFrom(DetailDescriptionViewModel::class.java) ->
                        DetailDescriptionViewModel(repository)

                    isAssignableFrom(NotifyViewModel::class.java) ->
                        NotifyViewModel(repository)

                    isAssignableFrom(ProfileViewModel::class.java) ->
                        ProfileViewModel(repository)



                    else ->
                        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            } as T
}
