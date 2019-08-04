package com.omni.travel.presentation.features.admin

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.omni.travel.R
import kotlinx.android.synthetic.main.activity_insert.*


internal const val RESULT_LOAD_IMAGE = 346
internal const val REQUEST_CODE_ASK_PERMISSIONS = 123
class InsertActivity : AppCompatActivity() {

    private val viewModel: InsertionViewModel by lazy {
        ViewModelProviders.of(this).get(InsertionViewModel::class.java)
    }

    internal  var selectedImageURI :Uri ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)
        //todo 1 storage
        btnImage.setOnClickListener {
            checkGalleryPermissions()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            selectedImageURI = data?.data!!
            showImage(selectedImageURI)
        }
    }



    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            REQUEST_CODE_ASK_PERMISSIONS -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    loadImage(this)
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(com.omni.travel.R.menu.save_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.save_action -> {
                viewModel.saveDeal(collectDealDetails() , selectedImageURI)
                Toast.makeText(this, "Deal saved", Toast.LENGTH_LONG).show()
                clean()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
