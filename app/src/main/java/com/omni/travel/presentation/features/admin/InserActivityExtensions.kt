package com.omni.travel.presentation.features.admin

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import com.omni.travel.entities.TravelDeal
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_insert.*


internal fun InsertActivity.collectDealDetails(): TravelDeal {
    val deal = TravelDeal()
    deal.title = txtTitle?.text.toString()
    deal.price = txtPrice?.text.toString()
    deal.description = txtDescription?.text.toString()
    Log.d("deal", selectedImageURI.toString())

    return deal
}


internal fun InsertActivity.clean() {
    txtTitle.setText("")
    txtPrice.setText("")
    txtDescription.setText("")
    txtTitle.requestFocus()
}


fun InsertActivity.checkGalleryPermissions() {

    if (Build.VERSION.SDK_INT >= 23) {
        if (ActivityCompat.checkSelfPermission(
                this,
                READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(READ_EXTERNAL_STORAGE),
                REQUEST_CODE_ASK_PERMISSIONS
            )
            return
        }
    }
    loadImage(this)
}

fun loadImage(insertActivity: InsertActivity) {
    val intent = Intent(
        Intent.ACTION_PICK,
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    )
    intent.type = "image/*"

    startActivityForResult(insertActivity, intent, RESULT_LOAD_IMAGE, null)

}

fun InsertActivity.showImage(url: Uri?) {
    if (url != null ) {
        val width = Resources.getSystem().displayMetrics.widthPixels
        Picasso.get()
            .load(url)
            .resize(width, width * 2 / 3)
            .centerCrop()
            .into(image)
    }
}