package com.omni.travel.domain

import android.net.Uri
import android.util.Log
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.omni.travel.entities.TravelDeal

val repository: RemoteDataSource by lazy {
    RemoteDataSource()
}

interface TravelDealsDataSourceInterface {
    fun insertADeal(deal: TravelDeal, selectedImageURI: Uri?)
    fun getAllTravelDealsOptions(): FirebaseRecyclerOptions<TravelDeal>

}

class RemoteDataSource(
    private val mFirebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    , private val mStorage: FirebaseStorage = FirebaseStorage.getInstance()
) : TravelDealsDataSourceInterface {

    override fun insertADeal(deal: TravelDeal, selectedImageURI: Uri?) {
        val mStorageRef = mStorage.reference.child("deals_pictures")
        if (selectedImageURI != null) {
            val ref = mStorageRef.child(selectedImageURI.lastPathSegment!!)
            val uploadTask = ref.putFile(selectedImageURI)
             uploadTask.continueWithTask(
                Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    return@Continuation ref.downloadUrl
                }).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    val pictureName = downloadUri?.path
                    deal.imageUrl = downloadUri.toString()
                    deal.imageName = pictureName
                    mFirebaseDatabase.reference.child("traveldeals").push()
                        .setValue(deal)
                    Log.d("deal Url: ", deal.imageUrl)
                    Log.d("Name", pictureName)
                } else {
                    // Handle failures
                    // ...
                }
            }
            // [END upload_get_download_url]
        }

//            .addOnSuccessListener {
//                Log.d("deal", "dealSaved")
//
//            }
        Log.d("deal", deal.toString())
    }

    override fun getAllTravelDealsOptions(): FirebaseRecyclerOptions<TravelDeal> {
        val query = mFirebaseDatabase.reference.child("traveldeals")
        return FirebaseRecyclerOptions.Builder<TravelDeal>()
            .setQuery(query, TravelDeal::class.java)
            .build()
    }

}