package com.hassanmashraful.firebasecrud.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hassanmashraful.firebasecrud.R;
import com.hassanmashraful.firebasecrud.key.key_url;
import com.hassanmashraful.firebasecrud.model.Wind_Requirement;
import com.hassanmashraful.firebasecrud.util.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hassanmashraful.firebasecrud.R.id.btn_save;
import static com.hassanmashraful.firebasecrud.R.id.landSizeET;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView locSelectTV, apiTV;
    private EditText nameET, landSizeET, remarkET, phnET;
    private Button btnSave;
    private CheckBox checkBox;
    private Spinner areaTYP, prevTYP;
    private LinearLayout verdictField;

    private DatabaseReference mFirebaseDatabase;

    private FirebaseDatabase mFirebaseInstance;
    private String userId;
    //private DatabaseReference mList;
    private String prevValue, areaValue;

    //private ArrayList<Location> futureLocation = new ArrayList<>();
    private boolean checkFLAG = false;

    //HashMap<String, String> location = new HashMap();


    private static double latitude, longitude;
    private static String address, placename, speed, deg;
    public static final String ANONYMOUS = "anonymous";

    private RequestQueue requestQueue;

    // private ListView mMessageListView;
    //private MessageAdapter mMessageAdapter;
    private ProgressBar mProgressBar;
    //private ImageButton mPhotoPickerButton;
    //private EditText mMessageEditText;
    //private Button mSendButton;

    private static String email;
    private String mUsername;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    //private ChildEventListener childEventListener;

    //right nw
   // private FirebaseAuth firebaseAuth;
   // private FirebaseAuth.AuthStateListener authStateListener;

    public static final int RC_SIGN_IN = 1;

    //For placepicker
    private static final int PLACE_PICKER_REQUEST = 1000;
    private GoogleApiClient mClient;

    private static MainActivity sInstance;

    private static ArrayList<Wind_Requirement> wind_requirements = new ArrayList<>();

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

    private VolleySingleton volleySingleton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Verdict");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                    .getColor(R.color.colorPrimary)));
        }

        email = getIntent().getStringExtra("email");

        //to init VolleySingleton class
        sInstance = this;


        verdictField = (LinearLayout) findViewById(R.id.verdictField);
        apiTV = (TextView) findViewById(R.id.apiTV);
        nameET = (EditText) findViewById(R.id.nameET);
        remarkET = (EditText) findViewById(R.id.remarkET);
        phnET = (EditText) findViewById(R.id.phnET);
        landSizeET = (EditText) findViewById(R.id.landSizeET);
        //locTV = (TextView) findViewById(R.id.locTV);
        btnSave = (Button) findViewById(btn_save);
        locSelectTV = (TextView) findViewById(R.id.locSelectTV);

        mUsername = ANONYMOUS;
        //firebaseDatabase = FirebaseDatabase.getInstance();
        //right nw
        //firebaseAuth = FirebaseAuth.getInstance();
        mFirebaseInstance = FirebaseDatabase.getInstance();

        //right nw
       /* authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    //user signed in
                    onSignedInInitialize(user.getDisplayName());
                    getUSERDATA();

                }else {
                    //user not signed in
                    onSignedOutCleanUp();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false).setProviders(
                                    AuthUI.EMAIL_PROVIDER,
                                    AuthUI.GOOGLE_PROVIDER).setTheme(R.style.AppThemeFirebaseAuth).setLogo(q).build(), RC_SIGN_IN);

                }


            }
        };*/

        //for placepicker
        mClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();



        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        databaseReference = mFirebaseInstance.getReference("users");

        // store app title to 'app_title' node
       // mFirebaseInstance.getReference("app_title").setValue("Realtime Database");




       // mList = mFirebaseInstance.getReference("windfirm/future");

        HashMap<String, String> names = new HashMap();
        names.put("place1", "28.9034,27.90783");
        names.put("place2", "28.9034,27.90783");
        names.put("place3", "28.9034,27.90783");
        names.put("place4", "28.9034,27.90783");
        //mList.setValue(names);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameET.getText().toString();
                //String email = inputEmail.getText().toString();
                String phnNum = phnET.getText().toString();
                //String address = addressET.getText().toString();
                String remarks = remarkET.getText().toString();
                double landSize = Double.parseDouble(landSizeET.getText().toString());
                //mList.setValue(names);
                // Check for already existed userId

                /*if (TextUtils.isEmpty(userId)) {
                    createUser(name, phnNum, address, landDetails);
                } else {
                    updateUser(address, landDetails);
                }*/
                /*if (email==null){
                    createUser(name, phnNum, address, landDetails);
                }else {
                    updateUser(address, landDetails);
                }*/
                updateUser(name, phnNum, address, placename, remarks, latitude, longitude, landSize, Double.parseDouble(speed), Double.parseDouble(deg));

                //createUser(name, email, phnNum, address, landDetails);

              //  gettingData();

               // for (int i = 0; i< futureLocation.size(); i++)
              //  Log.v("##$##$#$%%##@$%#%$# ", futureLocation.get(i).getCity()+" "+futureLocation.get(i).getDeg()+" "+futureLocation.get(i).getLat()+" "+futureLocation.get(i).getLon()+" "+futureLocation.get(i).getSpeed());

                nameET.setText("");
                phnET.setText("");
                //locTV.setText("");
                remarkET.setText("");

            }
        });



        locSelectTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(MainActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                verdictField.setVisibility(View.GONE);

            }
        });

        // Spinner element
        Spinner areaTYP = (Spinner) findViewById(R.id.areaTYP);
        Spinner prevTYP = (Spinner) findViewById(R.id.prevTYP);


        // Spinner click listener
        areaTYP.setOnItemSelectedListener(this);
        prevTYP.setOnItemSelectedListener(this);


        // Spinner Drop down elements
        List<String> area = new ArrayList<String>();
        area.add("None");
        area.add("Open field");
        area.add("Coastal area");
        area.add("Residential area");
        area.add("Riverside");

        List<String> prev = new ArrayList<>();
        prev.add("None");
        prev.add("Flat Ground");
        prev.add("Improved Ground From Pond");

        // Creating adapter for spinner
        ArrayAdapter<String> areaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, area);

        // Drop down layout style - list view with radio button
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        areaTYP.setAdapter(areaAdapter);


        ArrayAdapter<String> prevAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, prev);
        prevAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prevTYP.setAdapter(prevAdapter);




    }

    private void collectPhoneNumbers(Map<String,Object> users) {



        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            //futureLocation.add((Double) singleUser.get("lat"));
            String city = (String) singleUser.get("city");
            //Long deg = (Long) singleUser.get("deg");
            double speed = (double) singleUser.get("speed");
            double lat = (double) singleUser.get("lat");
            double lont = (double) singleUser.get("lon");

         //   futureLocation.add(new Location(city, 230094l, lat, lont, speed));

        }


    }



    /**
     * Creating new user node under 'users'
     */
    private void createUser(String name, String phnNum, String address, String landDetails) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }



        //User user = new User(name, phnNum, address, landDetails);

       //mFirebaseDatabase.child(userId).setValue(user);

        //addUserChangeListener();
    }

   /* public void gettingData(){
        mList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Get map of users in datasnapshot
                //loc = dataSnapshot.getValue().toString();
                apiTV.setText(dataSnapshot.getValue().toString());
                //Log.v("#$$$%%$$#", loc);
                //Get map of users in datasnapshot
                collectPhoneNumbers((Map<String,Object>) dataSnapshot.getValue());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/

    private void updateUser(final String name, final String phnNum, final String address, final String placename, final String remarks, final double latitude, final double longitude, final double landSize, final double speed, final double deg) {
        // updating the user via child nodes

        /*if (!TextUtils.isEmpty(address))
            mFirebaseDatabase.child(userId).child("address").setValue(address);
        if (!TextUtils.isEmpty(landDetails))
            mFirebaseDatabase.child(userId).child("landDetails").setValue(landDetails);
*/
        //mFirebaseDatabase.child("KfCUrfWIDMbLhxv1VRR").child("address").setValue(address);
        //mFirebaseDatabase.child("KfCUrfWIDMbLhxv1VRR").child("landDetails").setValue(landDetails);

        databaseReference.child(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try { mFirebaseDatabase.child(email).child("name").setValue(name); mFirebaseDatabase.child(email).child("phnNum").setValue(phnNum);
                    mFirebaseDatabase.child(email).child("address").setValue(address); mFirebaseDatabase.child(email).child("remarks").setValue(remarks);
                    mFirebaseDatabase.child(email).child("placename").setValue(placename); mFirebaseDatabase.child(email).child("longitude").setValue(longitude);
                    mFirebaseDatabase.child(email).child("deg").setValue(deg); mFirebaseDatabase.child(email).child("windspeed").setValue(speed);
                    mFirebaseDatabase.child(email).child("landSize").setValue(landSize);mFirebaseDatabase.child(email).child("latitude").setValue(latitude);}
                catch (Exception e) { e.printStackTrace(); }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            if (requestCode == RESULT_OK){
                Toast.makeText(getApplicationContext(), "Signed IN", Toast.LENGTH_SHORT).show();
            }else if (requestCode == RESULT_CANCELED){
                Toast.makeText(getApplicationContext(), "Not Signed in", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                StringBuilder stBuilder = new StringBuilder();
                 placename = String.format("%s", place.getName());
                latitude = place.getLatLng().latitude;
                longitude = place.getLatLng().longitude;
                address = String.format("%s", place.getAddress());

                //stBuilder.append("Name: ");
                //stBuilder.append(placename);
                stBuilder.append("Address: ");
                stBuilder.append(address);
                stBuilder.append("\n");
                stBuilder.append("Latitude: ");
                stBuilder.append(latitude);
                stBuilder.append("\n");
                stBuilder.append("Logitude: ");
                stBuilder.append(longitude);
                stBuilder.append("\n");
                apiTV.setText("  ::  "+stBuilder.toString()+"  ::  ");
                Log.v("@#@$@%%", stBuilder.toString());

                callingHOST();
            }
        }



    }

    private void onSignedOutCleanUp() {
        mUsername = ANONYMOUS;
        //mMessageAdapter.clear();
        //detachDBreadListener();
    }

    private void onSignedInInitialize(String user) {
        mUsername = user;
        //attachDBreadListener();
    }



    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //switch (item.getItemId()){
            /*case R.id.action_settings:
                AuthUI.getInstance().signOut(this);
                finish();
                return true;*/

            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == android.R.id.home) {
                // finish the activity
                onBackPressed();
                return true;
            }
            return super.onOptionsItemSelected(item);
            //noinspection SimplifiableIfStatement
            /*case R.id.home:
                // finish the activity
                onBackPressed();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }*/
    }

    //right nw
   /* @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (authStateListener != null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
        //detachDBreadListener();
        //mMessageAdapter.clear();

    }*/

    @Override
    protected void onStart() {
        super.onStart();
        mClient.connect();
    }

    @Override
    protected void onStop() {
        mClient.disconnect();
        super.onStop();
    }

    //right nw
    /*public void getUSERDATA(){

        //firebaseAuth.getCurrentUser().getEmail();
        //String email = "#";
        //String uid="";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                //String providerId = profile.getProviderId();

                // UID specific to the provider
                email = profile.getUid();
                email = EncodeString(email);

                // Name, email address, and profile photo Url
                *//*String name = profile.getDisplayName();
                email = profile.getEmail();
                Uri photoUrl = profile.getPhotoUrl();*//*
            };
        }

        Toast.makeText(getApplicationContext(), " "+email, Toast.LENGTH_SHORT).show();

    }*/

    public static String EncodeString(String string) {
        return string.replace(".", ",");
    }

    public static String DecodeString(String string) {
        return string.replace(",", ".");
    }


    //getting data from server
    public void callingHOST(){
        volleySingleton = volleySingleton.getInstance();  requestQueue = volleySingleton.getRequestQueue();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getJSOnURL(), (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                    parseJsonResponse(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getAppContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    public void parseJsonResponse(JSONObject response){

        if (response == null || response.length() == 0){return;}

        try {

            if (response.has(key_url.KEY_LIST)){
                JSONArray jsonArray = response.getJSONArray(key_url.KEY_LIST);

                for (int i = 0; i<jsonArray.length(); i++){
                    JSONObject currentData = jsonArray.getJSONObject(i);
                    //parsing data by individual key
                    String id = currentData.getString(key_url.KEY_NAME);

                    JSONObject wind = currentData.getJSONObject(key_url.KEY_WIND);
                     speed = wind.getString(key_url.KEY_SPEED);
                     deg = wind.getString(key_url.KEY_DEG);

                    Log.v("speed%^%%&^%&%:  ", speed+"  "+deg);

                    wind_requirements.add(new Wind_Requirement(Double.parseDouble(speed), Double.parseDouble(deg)));



                }
                /*for (int m = 0; m<jsonArray.length(); m++)
                    Toast.makeText(getAppContext(), "Num: "+userInfos.get(m).getNumber()+" ID: "+userInfos.get(m).getId()+" SMS: "+userInfos.get(m).getSms()+" STATUS: "+userInfos.get(m).getStatus(), Toast.LENGTH_SHORT).show();*/

            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error connection..", Toast.LENGTH_SHORT).show();
        }
        //queueShow.setText(String.valueOf(queueCount));

        if (Double.parseDouble(speed)>=3){
            verdictField.setVisibility(View.VISIBLE);
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setTitle("Sorry..");
            builder.setMessage("Address: "+address+", here WindMill can't be plant as wind speed is "+speed+"ms\n"+"Need to be above 3 ms");

            builder.setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                }
            });

            // Set other dialog properties

            // Create the AlertDialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public static String getJSOnURL(){
        return key_url.URL_GET_DATA + key_url.KEY_LAT + latitude + key_url.KEY_LONG + longitude + key_url.APP_ID;
    }

    public void checkRequirement(){
        for (int i = 0; i<wind_requirements.size(); i++){
            if (wind_requirements.get(i).getSpeed()>=3.00){
                checkFLAG = true;
            }
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        switch(adapterView.getId()) {

            case R.id.areaTYP:
                // On selecting a spinner item
                areaValue = adapterView.getItemAtPosition(position).toString();

            case R.id.prevTYP:
                // On selecting a spinner item
                prevValue = adapterView.getItemAtPosition(position).toString();

                // Showing selected spinner item
                Toast.makeText(adapterView.getContext(), "Selected: " + areaValue+prevValue, Toast.LENGTH_SHORT).show();


        }




    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

        areaValue = adapterView.getItemAtPosition(0).toString();
        prevValue = adapterView.getItemAtPosition(0).toString();

    }
}
