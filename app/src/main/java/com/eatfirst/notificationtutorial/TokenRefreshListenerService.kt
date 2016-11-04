package com.eatfirst.notificationtutorial

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class TokenRefreshListenerService: FirebaseInstanceIdService() {

    companion object {
        val TAG = TokenRefreshListenerService::class.java.canonicalName
    }

    //This callback fires whenever a new token is generated, so calling getToken in its context ensures that we are accessing a current,
    // available registration token
    override fun onTokenRefresh() {
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d(TAG, "Refreshed token: " + refreshedToken)
    }

}