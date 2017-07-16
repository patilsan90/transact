package com.algo.transact.AppConfig;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by sandeep on 10/7/17.
 */

public class HTTPReqController extends Application{

    public static final String TAG = HTTPReqController.class.getSimpleName();

    private RequestQueue requestQueue;
    private static HTTPReqController httpReqController;

    @Override
    public void onCreate() {
        super.onCreate();
        httpReqController = this;
    }
    public static synchronized HTTPReqController getInstance()
    {
        Log.i(AppState.TAG, "Check httpReqController: " + (httpReqController == null));

        return httpReqController;
    }

    public RequestQueue getRequestQueue()
    {
        if(requestQueue == null)
            requestQueue = Volley.newRequestQueue(getApplicationContext());

        return requestQueue;
    }

    public<T> void addToRequestQueue(Request<T> req, String tag)
    {
        req.setTag(TextUtils.isEmpty(tag)? TAG : tag);
        getRequestQueue().add(req);
    }

    public<T> void addToRequestQueue(Request<T> req)
    {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
    public void cancelPendingRequests(Object tag)
    {
        if(requestQueue != null)
            requestQueue.cancelAll(tag);
    }

}
