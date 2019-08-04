package com.omni.travel.presentation.features.user

import androidx.lifecycle.ViewModel
import com.omni.travel.domain.RemoteDataSource
import com.omni.travel.domain.repository


class TravelListViewModel(private val dealRepo: RemoteDataSource = repository) : ViewModel() {

     fun getFirebaseOptionsOfAllDeals() =
        dealRepo.getAllTravelDealsOptions()

}