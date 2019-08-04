package com.omni.travel.presentation.features.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.omni.travel.R
import com.omni.travel.presentation.features.admin.InsertActivity
import com.omni.travel.presentation.features.attachListener
import com.omni.travel.presentation.features.detachListener
import com.omni.travel.presentation.features.signInSettings
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


const val RC_SIGN_IN = 101

class MainActivity : AppCompatActivity() {

    private val viewModel: TravelListViewModel by lazy {
        ViewModelProviders.of(this).get(TravelListViewModel::class.java)
    }

    val mFirebaseDatabase = FirebaseDatabase.getInstance()
   internal val mFirebaseAuth = FirebaseAuth.getInstance()
    lateinit var mAuthStateListener: FirebaseAuth.AuthStateListener
    private  var dealsAdapter: DealsAdapter?= null
    var isAdmin: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signInSettings()
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fab.setOnClickListener {
            AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    Log.d("Logout", "User Logged Out")
                    attachListener()
                }
            detachListener()
        }

        val uID = mFirebaseAuth.uid
        if (uID != null) {

        with(deal_recycler_view) {
            layoutManager = LinearLayoutManager(this.context)
            dealsAdapter = DealsAdapter(viewModel.getFirebaseOptionsOfAllDeals())
            adapter = dealsAdapter
            dealsAdapter?.startListening()
        }
        }


    }

    override fun onStart() {
        super.onStart()
        if (dealsAdapter != null)
            dealsAdapter?.startListening()
    }

    override fun onResume() {
        super.onResume()
        attachListener()
    }


    override fun onPause() {
        super.onPause()
        detachListener()
    }

    override fun onStop() {
        super.onStop()
        dealsAdapter?.stopListening()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val insertMenu = menu.findItem(R.id.action_insert)
        insertMenu.isVisible = isAdmin
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                val intent = Intent(this, InsertActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
