package com.example.keke.iptracer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.keke.helper.SQLiteHandler;
import com.example.keke.helper.SessionManager;

import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class IPtracerv1 extends Activity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private TracingTask mAuthTask = null;
    private GetCoordTask mCoordTask = null;
    private DeleteTask mDeleteTask = null;
    private AliasTask mAliasTask = null;

    // UI references.
    private AutoCompleteTextView mIPView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private SessionManager session;
    private SQLiteHandler db;
    private ProgressDialog pDialog;
    private ProgressDialog progress;
    private TextView mStatusView;
    private TextView mDelView;
    private int i = 0;
    private ProgressBar one;
    private String m_Text = "";
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iptracerv1);
        // Set up the login form.

        mIPView = (AutoCompleteTextView) findViewById(R.id.IP);
        mStatusView = (TextView) findViewById(R.id.StatusView);
        one = (ProgressBar)findViewById(R.id.firstbar);
        Log.d(mIPView.getText().toString(), "WHY U NO EMPTY LOL");
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());
        progress = new ProgressDialog(this);
        // Session manager
        session = new SessionManager(getApplicationContext());

        Button mIPAliasButton = (Button) findViewById(R.id.IpAlias);
        mIPAliasButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {

                String IP = mIPView.getText().toString();
                if (TextUtils.isEmpty(IP) || !isIPValid(IP) ) {
                    mIPView.setError(getString(R.string.error_invalid_IP));
                    View focusView = null;
                    focusView = mIPView;

                }else {

                    LayoutInflater li = LayoutInflater.from(context);
                    View promptsView = li.inflate(R.layout.prompts, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context);

                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);

                    final EditText userInput = (EditText) promptsView
                            .findViewById(R.id.editTextDialogUserInput);

                    // set dialog message
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("Submit",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // get user input and set it to result
                                            // edit text
                                            if(userInput.getText().toString().length()<32)
                                            {
                                                m_Text = (userInput.getText()).toString();
                                                attemptAlias();
                                            }else{
                                                userInput.setError("Alias too big");
                                            }
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();


                }
            }
        });


        Button mdelIPButton = (Button) findViewById(R.id.delIP);
        mdelIPButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptDelete();

            }
        });

        Button mIPTraceButton = (Button) findViewById(R.id.IPTrace);
        mIPTraceButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptTrace();
                Log.d("Fin", "Attempt trace fin");
            }
        });

        Button mCoordButton = (Button) findViewById(R.id.getCoord);
        mCoordButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptCoords();
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    public void open(View view){
        progress.setMessage("Downloading Music :) ");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.show();

        final int totalProgressTime = 100;

        final Thread t = new Thread(){

            @Override
            public void run(){

                int jumpTime = 0;
                while(jumpTime < totalProgressTime){
                    try {
                        sleep(200);
                        jumpTime += 5;
                        progress.setProgress(jumpTime);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

            }
        };
        t.start();
    }



    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptTrace() {


        // Reset errors.
        mIPView.setError(null);


        // Store values at the time of the login attempt.
        String IP = mIPView.getText().toString();
        Log.d(IP, "IP still is:");

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(IP) || !isIPValid(IP)) {
            mIPView.setError(getString(R.string.error_invalid_IP));
            focusView = mIPView;
            cancel = true;
        }



        if (cancel) {
            // Focus the field
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new TracingTask(IP);
            boolean success = mAuthTask.doInBackground();
            showProgress(false);

            //mAuthTask.execute((Void) null);

        }

    }

    private void attemptCoords() {


        // Reset errors.
        mIPView.setError(null);


        // Store values at the time of the login attempt.
        String IP = mIPView.getText().toString();


        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(IP) && !isIPValid(IP)) {
            mIPView.setError(getString(R.string.error_invalid_IP));
            focusView = mIPView;
            cancel = true;
        }



        if (cancel) {
            // Focus the field
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mCoordTask = new GetCoordTask(IP);
            boolean success = mCoordTask.doInBackground();
            showProgress(false);
            //mAuthTask.execute((Void) null);

        }
    }

    private void attemptDelete() {


        // Reset errors.
        mIPView.setError(null);


        // Store values at the time of the login attempt.
        String IP = mIPView.getText().toString();


        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(IP) && !isIPValid(IP)) {
            mIPView.setError(getString(R.string.error_invalid_IP));
            focusView = mIPView;
            cancel = true;
        }



        if (cancel) {
            // Focus the field
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mDeleteTask = new DeleteTask(IP);
            boolean success = mDeleteTask.doInBackground();
            showProgress(false);
            //mAuthTask.execute((Void) null);

        }
    }

    private void attemptAlias() {


        // Reset errors.
        mIPView.setError(null);


        // Store values at the time of the login attempt.
        String IP = mIPView.getText().toString();


        boolean cancel = false;
        View focusView = null;

            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAliasTask = new AliasTask(IP, m_Text);
            boolean success = mAliasTask.doInBackground();
            showProgress(false);
            //mAuthTask.execute((Void) null);


    }

    private boolean isIPValid(String IP) {

        Boolean[] sectioncheck = new Boolean[4];
        int dotcount = 0;
        int savepoint = 0;
        int checkpoint = 0;
        boolean goodstring = goodstring(IP);



        if(goodstring == true)
        {

            for( int i = 0 ; i <IP.length(); i++)
            {



                if (IP.charAt(i) == '.')
                {
                    dotcount++;
                    if(dotcount == 1)
                    {
                        int y = Integer.parseInt(IP.substring(0,i));
                        if (y > 0 && y<255)
                            sectioncheck[checkpoint++] = true;

                    }else{

                        int y = Integer.parseInt(IP.substring(savepoint+1,i));

                        if (y > 0 && y<255)
                        {

                            sectioncheck[checkpoint++] = true;
                        }

                    }
                    savepoint = i;
                }
            }

            int y = Integer.parseInt(IP.substring(savepoint+1,IP.length()));
            if (y > 0 && y<255)
            {

                sectioncheck[checkpoint++] = true;
            }

            dotcount = 0;
            for(int x = 0; x<sectioncheck.length; x++)
            {

                if(sectioncheck[x] = true)
                    dotcount++;


            }

        }

        if(dotcount == 4)
            return true;
        else
            return false;


    }

    public static boolean goodstring(String s) {
        if(s.length()>15)
            return false;
        for( int i = 0 ; i <s.length(); i++)
        {
            if (s.charAt(i) != '.' && !(Character.isDigit(s.charAt(i)))  )
            {
                return false;
            }
        }

        return true;
    }
    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }




    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class TracingTask extends AsyncTask<Void, Void, Boolean> {

        private final String mIP;
        private final String mcoordX;
        private final String mcoordY;

        TracingTask(String IP) {
            mIP = IP;
            Random r = new Random();
            mcoordX = String.valueOf(r.nextInt(26 - 0) + 0);
            mcoordY = String.valueOf(r.nextInt(44 - 0) + 0);
           // mcoordX = String.valueOf(3);
           // mcoordY = String.valueOf(4);
            Log.d(mIP, "The IP is: ");
            Log.d(mcoordX, "The coord X is: ");
            Log.d(mcoordY, "The coord Y is: ");
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            //Check


            String tag_string_req = "req_register";

            pDialog.setMessage("Tracing...");
            showDialog();

            StringRequest strReq = new StringRequest(Request.Method.POST,
                    AppConfig.URL_REGISTER, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d("TAG", "Register Response: " + response.toString());
                    hideDialog();

                    try {
                        Log.d("TAG", "Register Response: " + response.toString());
                        JSONObject jObj = new JSONObject(response);

                        boolean error = jObj.getBoolean("error");

                        if (!error) {
                            // User successfully stored in MySQL
                            // Now store the user in sqlite
                            /*String uid = jObj.getString("uid");

                            JSONObject user = jObj.getJSONObject("user");
                            String name = user.getString("name");
                            String email = user.getString("email");
                            String created_at = user
                                    .getString("created_at");

                            // Inserting row in users table
                            db.addUser(name, email, uid, created_at);
                            */


                            mStatusView.setTextColor(Color.WHITE);
                            mStatusView.setText("");
                            mStatusView.setText("IP traced successfully!");


                            // Launch login activity
                            /*Intent intent = new Intent(
                                    RegisterActivity.this,
                                    LoginActivity.class);
                            startActivity(intent);*/


                        } else {

                            // Error occurred in registration. Get the error
                            // message
                            mIPView.setText("");
                            String errorMsg = jObj.getString("error_msg");
                            Toast.makeText(getApplicationContext(),
                                    errorMsg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("TAG", "Registration Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                    hideDialog();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting params to register url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("IP", mIP);
                    params.put("coordX",mcoordX );
                    params.put("coordY", mcoordY);
                    Log.d(params.toString(),"PARAMS HERE:");
                    return params;
                }

            };
            Log.d(strReq.toString(),"LOOK HEREEEEEE");
            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

            return true;
        }

        private void showDialog() {
            if (!pDialog.isShowing())
                pDialog.show();
        }

        private void hideDialog() {
            if (pDialog.isShowing())
                pDialog.dismiss();
        }

            // TODO: register the new account here.

        }

    public class GetCoordTask extends AsyncTask<Void, Void, Boolean> {

        private final String mIP;


        GetCoordTask(String IP) {
            mIP = IP;

            Log.d(mIP, "The IP is: ");

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            //Check


            String tag_string_req = "req_register";

            pDialog.setMessage("Trying to obtain IP ...");
            showDialog();
            final String URL_GetCoords = "http://95.93.234.88:8000/android_login/userdb.php?IP=" + mIP;
            StringRequest strReq = new StringRequest(Request.Method.GET,
                    URL_GetCoords, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d("TAG", "Register Response: " + response.toString());
                    hideDialog();

                    try {
                        Log.d("TAG", "Register Response: " + response.toString());
                        JSONObject jObj = new JSONObject(response);

                        boolean error = jObj.getBoolean("error");

                        if (!error) {
                            // User successfully stored in MySQL
                            // Now store the user in sqlite
                            //String uid = jObj.getString("uid");

                           // JSONObject user = jObj.getJSONObject("user");
                            String coordX = jObj.getString("coordX");
                            String coordY = jObj.getString("coordY");
                            coordX = coordX.replaceAll("\\D+","");
                            coordY = coordY.replaceAll("\\D+","");
                           // String created_at = user
                           //         .getString("created_at");


                            mStatusView.setTextColor(Color.WHITE);
                            mStatusView.setText("IP Coords are: (" + coordX + ", " + coordY + ")");
                            //Toast.makeText(getApplicationContext(), "IP traced.", Toast.LENGTH_LONG).show();

                            // Launch login activity
                            /*Intent intent = new Intent(
                                    RegisterActivity.this,
                                    LoginActivity.class);
                            startActivity(intent);*/


                        } else {

                            // Error occurred in registration. Get the error
                            // message
                            mIPView.setText("");
                            String errorMsg = jObj.getString("error_msg");
                            Toast.makeText(getApplicationContext(),
                                    errorMsg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("TAG", "Registration Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                    hideDialog();
                }
            }) {



            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

            return true;
        }

        private void showDialog() {
            if (!pDialog.isShowing())
                pDialog.show();
        }

        private void hideDialog() {
            if (pDialog.isShowing())
                pDialog.dismiss();
        }

        // TODO: register the new account here.

    }

    public class DeleteTask extends AsyncTask<Void, Void, Boolean> {

        private final String mIP;


        DeleteTask(String IP) {
            mIP = IP;

            Log.d(mIP, "The IP is: ");

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            //Check


            String tag_string_req = "req_register";

            pDialog.setMessage("Deleting ...");
            showDialog();
            final String URL_delete = "http://95.93.234.88:8000/android_login/userdb.php?IP=" + mIP;
            StringRequest strReq = new StringRequest(Request.Method.DELETE,
                    URL_delete, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d("TAG", "Register Response: " + response.toString());
                    hideDialog();

                    try {
                        Log.d("TAG", "Register Response: " + response.toString());
                        JSONObject jObj = new JSONObject(response);

                        boolean error = jObj.getBoolean("error");

                        if (!error) {
                            // User successfully stored in MySQL
                            // Now store the user in sqlite
                            //String uid = jObj.getString("uid");

                            // JSONObject user = jObj.getJSONObject("user");

                            // String created_at = user
                            //         .getString("created_at");

                            mIPView.setText("");
                            mStatusView.setTextColor(Color.RED);
                            mStatusView.setText("IP successfully erased");

                            //Toast.makeText(getApplicationContext(), "IP traced.", Toast.LENGTH_LONG).show();

                            // Launch login activity
                            /*Intent intent = new Intent(
                                    RegisterActivity.this,
                                    LoginActivity.class);
                            startActivity(intent);*/


                        } else {

                            // Error occurred in registration. Get the error
                            // message
                            mIPView.setText("");
                            String errorMsg = jObj.getString("error_msg");
                            Toast.makeText(getApplicationContext(),
                                    errorMsg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("TAG", "Registration Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                    hideDialog();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting params to register url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("IP", mIP);


                    return params;
                }

            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

            return true;
        }

        private void showDialog() {
            if (!pDialog.isShowing())
                pDialog.show();
        }

        private void hideDialog() {
            if (pDialog.isShowing())
                pDialog.dismiss();
        }

        // TODO: register the new account here.

    }

    public class AliasTask extends AsyncTask<Void, Void, Boolean> {

        private final String mIP;
        private final String mAlias;

        AliasTask(String IP, String alias) {
            mIP = IP;
            mAlias = alias;
            Log.d(mIP, "The IP is: ");
            Log.d(mAlias, "The Alias is: ");
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            //Check


            String tag_string_req = "req_register";

            pDialog.setMessage("Binding...");
            showDialog();

            StringRequest strReq = new StringRequest(Request.Method.PUT,
                    AppConfig.URL_REGISTER, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d("TAG", "Register Response: " + response.toString());
                    hideDialog();

                    try {
                        Log.d("TAG", "Register Response: " + response.toString());
                        JSONObject jObj = new JSONObject(response);

                        boolean error = jObj.getBoolean("error");
                        Log.d(mIP, "Check ip  here:");
                        Log.d(mAlias, "Check Alias  here:");
                        if (!error) {
                            // User successfully stored in MySQL
                            // Now store the user in sqlite
                            //String uid = jObj.getString("uid");

                            // JSONObject user = jObj.getJSONObject("user");

                            // String created_at = user
                            //         .getString("created_at");


                            mStatusView.setTextColor(Color.WHITE);
                            mStatusView.setText("IP Alias successfully bound");

                            //Toast.makeText(getApplicationContext(), "IP traced.", Toast.LENGTH_LONG).show();

                            // Launch login activity
                            /*Intent intent = new Intent(
                                    RegisterActivity.this,
                                    LoginActivity.class);
                            startActivity(intent);*/


                        } else {

                            // Error occurred in registration. Get the error
                            // message
                            mIPView.setText("");
                            String errorMsg = jObj.getString("error_msg");
                            Toast.makeText(getApplicationContext(),
                                    errorMsg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("TAG", "Registration Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                    hideDialog();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting params to register url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("IP", mIP);
                    params.put("IPAlias",mAlias);

                    Log.d(params.toString(),"PARAMS HERE:");
                    return params;
                }

            };
            Log.d(strReq.toString(),"LOOK HEREEEEEE");
            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

            return true;
        }

        private void showDialog() {
            if (!pDialog.isShowing())
                pDialog.show();
        }

        private void hideDialog() {
            if (pDialog.isShowing())
                pDialog.dismiss();
        }

        // TODO: register the new account here.

    }
    }





