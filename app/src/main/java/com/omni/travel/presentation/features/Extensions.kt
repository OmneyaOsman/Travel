package com.omni.travel.presentation.features

import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.omni.travel.presentation.features.user.MainActivity
import com.omni.travel.presentation.features.user.RC_SIGN_IN

private fun MainActivity.checkAdmin(uid: String) {
    isAdmin = false
    val ref = mFirebaseDatabase.reference.child("administrators")
        .child(uid)
    val listener = object : ChildEventListener {
        override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
            isAdmin = true
            invalidateOptionsMenu()
        }

        override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {

        }

        override fun onChildRemoved(dataSnapshot: DataSnapshot) {

        }

        override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {

        }

        override fun onCancelled(databaseError: DatabaseError) {

        }
    }
    ref.addChildEventListener(listener)
}

fun MainActivity.signInSettings() {
    mAuthStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser == null) {
                signIn()
            } else {
                val userId = firebaseAuth.uid
                checkAdmin(userId!!)
            }
    }
}

private fun MainActivity.signIn() {
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setIsSmartLockEnabled(false)
                    .setAvailableProviders(
                        arrayListOf(
                            AuthUI.IdpConfig.EmailBuilder().build(),
                            AuthUI.IdpConfig.GoogleBuilder().build()
                        )
                    )
                    .build(),
                RC_SIGN_IN
            )
        }
fun MainActivity.attachListener() {
    mFirebaseAuth.addAuthStateListener(mAuthStateListener)
}

fun MainActivity.detachListener() {
    mFirebaseAuth.removeAuthStateListener(mAuthStateListener)
}