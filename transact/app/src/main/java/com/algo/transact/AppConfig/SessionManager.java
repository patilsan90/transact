package com.algo.transact.AppConfig;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by sandeep on 10/7/17.
 */

public class SessionManager {

    public static final String TAG = SessionManager.class.getSimpleName();

    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;

    final int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "FutureInductionLogin";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    public SessionManager(Context context)
    {
        this._context = context;
        this.pref=_context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor=pref.edit();
    }

    public void setLogin(boolean isLoggedIn)
    {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.commit();
        Log.d(AppState.TAG,"User login session modified");
    }

    public boolean isLoggedIn()
    {
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public boolean logoutUser(SQLiteHandler db, SessionManager session)
    {
        session.setLogin(false);

        db.deleteUsers();

        return true;
        // Launching the login activity
       // Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        //startActivity(intent);
        //finish();

    }
}
