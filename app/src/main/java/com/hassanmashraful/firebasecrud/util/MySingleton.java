package com.hassanmashraful.firebasecrud.util;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Sourav on 3/19/2017.
 */

public class MySingleton {

    private static MySingleton mySingleton;
    private static Context context;
    private RequestQueue mRequestQueue;

    public MySingleton(Context context) {
        this.context = context;
        mRequestQueue = getmRequestQueue();
    }

    private RequestQueue getmRequestQueue(){
        if (mRequestQueue==null)
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        return mRequestQueue;
    }

    public static synchronized MySingleton getmInstance(Context context){
        if (mySingleton==null)
            mySingleton = new MySingleton(context);
        return mySingleton;
    }

    public <T>void addToReuestueue(Request<T> request){
        getmRequestQueue().add(request);
    }
}
