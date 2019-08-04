package com.omni.travel.presentation.features.admin

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.omni.travel.domain.RemoteDataSource
import com.omni.travel.domain.repository
import com.omni.travel.entities.TravelDeal


class InsertionViewModel(private val dealRepo: RemoteDataSource = repository) : ViewModel() {

     fun saveDeal(deal: TravelDeal, selectedImageURI: Uri?) {
        dealRepo.insertADeal(deal , selectedImageURI)
    }
}