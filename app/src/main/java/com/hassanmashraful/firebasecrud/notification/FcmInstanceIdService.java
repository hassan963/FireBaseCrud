package com.hassanmashraful.firebasecrud.notification;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Sourav on 3/19/2017.
 */

public class FcmInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String fcm_token = FirebaseInstanceId.getInstance().getToken();
        Log.v("FCM_TOKEN $### ",fcm_token);
    }
}
