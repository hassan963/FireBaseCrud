package com.hassanmashraful.firebasecrud.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hassanmashraful.firebasecrud.Location;
import com.hassanmashraful.firebasecrud.R;
import com.hassanmashraful.firebasecrud.key.key_url;
import com.hassanmashraful.firebasecrud.model.LocationDetails;

import java.util.ArrayList;
import java.util.Map;

import static com.hassanmashraful.firebasecrud.activity.MainActivity.EncodeString;

/**
 * Created by Hassan M.Ashraful on 3/16/2017.
 */

public class HomeFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private GoogleMap mMap;
    private DatabaseReference mList;
    private ArrayList<Location> futureLocation = new ArrayList<>();
    private FirebaseDatabase mFirebaseInstance;
    private TextView nameTV, phnTV, landSizeTV, landTypTV, prevTV, addTV, latLonTV;

    private String name;
    private String phnNum;
    private String address, spaceType, areaValue;
    private double latitude, longitude, speed, landSize;

    private String email = "b@a,com";

    ArrayList<LocationDetails> locationDetailses = new ArrayList<>();
    private SharedPreferences sharedpreferences;
    private double numOfTurbineOne,
            oneTurbineOutputOne,
            totalTurbineOutputOne,
            numOfTurbineTwo,
            oneTurbineOutputTwo,
            totalTurbineOutputTwo,
            numOfTurbineThree,
            oneTurbineOutputThree,
            totalTurbineOutputThree;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        /*SupportMapFragment mapFragment = (SupportMapFragment) rootView.getSupportFragmentManager()
                .findFragmentById(R.id.map);*/


        View view = inflater.inflate(R.layout.fragment_home, null, false);

        getUSERDATA();

        nameTV = (TextView) view.findViewById(R.id.nameTV);
        phnTV = (TextView) view.findViewById(R.id.phnTV);
        landSizeTV = (TextView) view.findViewById(R.id.landSizeTV);
        landTypTV = (TextView) view.findViewById(R.id.landTypTV);

        prevTV = (TextView) view.findViewById(R.id.prevTV);
        addTV = (TextView) view.findViewById(R.id.addTV);
        latLonTV = (TextView) view.findViewById(R.id.latLonTV);


        mFirebaseInstance = FirebaseDatabase.getInstance();

        mList = mFirebaseInstance.getReference("users");
        sharedpreferences = this.getActivity().getSharedPreferences(key_url.TurbineDetails, Context.MODE_PRIVATE);

        /*SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.mapHome);
        mapFragment.getMapAsync(this);*/



       mList.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               if (dataSnapshot.hasChild(email)){
                       userDataCall(); //getting user data
                       getMAP();

               }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void getMAP(){
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.mapHome);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void userDataCall(){

        mList.child(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                name = dataSnapshot.child("name").getValue().toString();
                phnNum = dataSnapshot.child("phnNum").getValue().toString();
                address = dataSnapshot.child("address").getValue().toString();
                latitude = Double.parseDouble(dataSnapshot.child("latitude").getValue().toString());
                longitude = Double.parseDouble(dataSnapshot.child("longitude").getValue().toString());
                landSize = Double.parseDouble(dataSnapshot.child("landSize").getValue().toString());
                spaceType = dataSnapshot.child("spaceType").getValue().toString();
                speed = Double.parseDouble(dataSnapshot.child("windspeed").getValue().toString());
                areaValue = dataSnapshot.child("areaValue").getValue().toString();

                verdictResult();

                //nameTV.setText("Name: "+name);
                //phnTV.setText("Phone Num: "+phnNum);
                //landTypTV.setText("Address: "+address);
                prevTV.setText("Still working");
                addTV.setText("Address: "+address);
                latLonTV.setText("Latitude: "+latitude+"\n"+" "+"Longitude: "+longitude);

                nameTV.setText("For 25m Turbine, Num of turbine: "+numOfTurbineOne+" \nPer turbine power output: "+oneTurbineOutputOne+" \nTotal output: "+totalTurbineOutputOne);
                phnTV.setText("For 50m Turbine, Num of turbine: "+numOfTurbineTwo+" \nPer turbine power output: "+oneTurbineOutputTwo+" \nTotal output: "+totalTurbineOutputTwo);
                landTypTV.setText("For 100m Turbine, Num of turbine: "+numOfTurbineThree+" \nPer turbine power output: "+oneTurbineOutputThree+" \nTotal output: "+totalTurbineOutputThree);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //Getting current user
    public void getUSERDATA(){

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
                /*String name = profile.getDisplayName();
                email = profile.getEmail();
                Uri photoUrl = profile.getPhotoUrl();*/
            };
        }

        //Toast.makeText(getApplicationContext(), " "+email, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mList.child(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Response").snippet(address).icon(BitmapDescriptorFactory.fromResource(R.drawable.wlll)));
                //marker.

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude),8.0f));

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                        builder.setTitle("We will get back to you soon..");
                        builder.setMessage("Address: "+address+"\n"+"We can plant WindMill here");




                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                            }
                        });
                        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });

                        // Set other dialog properties

                        // Create the AlertDialog
                        AlertDialog dialog = builder.create();
                        dialog.show();


                        return false;
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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

            futureLocation.add(new Location(city, 230094l, lat, lont, speed));

        }

        for (int i = 0; i< futureLocation.size(); i++)
            Log.v("##$##$#$%%##@$%#%$# ", futureLocation.get(i).getCity()+" "+futureLocation.get(i).getDeg()+" "+futureLocation.get(i).getLat()+" "+futureLocation.get(i).getLon()+" "+futureLocation.get(i).getSpeed());


    }


    private void verdictResult(){

            switch (areaValue){
                case "Agriculture field":
                    resultTurbine(25);
                    resultTurbine(50);
                    resultTurbine(100);
                    break;
                case "Coastal area":
                    resultTurbine(50);
                    resultTurbine(100);
                    break;
                case "Residential area":
                    resultTurbine(25);
                    break;
                case "Riverside":
                    resultTurbine(50);
                    resultTurbine(100);
                    break;
                default:
                    break;
            }


    }

    private void resultTurbine(double turbineHeight){

        //0.5*p*A*Cp*V3*Ng*Nb
        double air_density = 1.2, //p 1.2 kg/m3 (sea level)
                rotor_swept_area, // meter^2
                coefficient_of_performance = 0.35, //Cp  0.35 is typical, 0.56 is the theoretical maximum known as the Betz limit.
                wind_velocity = speed, // V
                generator_efficiency = 50, // Ng	50 percent to 80 percent.
                gear_box_bearing_efficiency = 95, // Nb 95 percent
                pi = 3.1416;
        double land; land = landSize;
        land = land * 1000000;


        if (turbineHeight==25){
            rotor_swept_area = pi*(turbineHeight*turbineHeight);
            numOfTurbineOne = land/1000;
            oneTurbineOutputOne = (0.5*air_density*rotor_swept_area*coefficient_of_performance*(wind_velocity*wind_velocity*wind_velocity)*generator_efficiency*gear_box_bearing_efficiency)/1000000;
            totalTurbineOutputOne = numOfTurbineOne*oneTurbineOutputOne;



        }
        if (turbineHeight==50){
            rotor_swept_area = pi*(turbineHeight*turbineHeight);
            numOfTurbineTwo = land/1500;
            oneTurbineOutputTwo = (0.5*air_density*rotor_swept_area*coefficient_of_performance*(wind_velocity*wind_velocity*wind_velocity)*generator_efficiency*gear_box_bearing_efficiency)/1000000;
            totalTurbineOutputTwo = numOfTurbineTwo*oneTurbineOutputTwo;

        }
        if (turbineHeight==100){
            rotor_swept_area = pi*(turbineHeight*turbineHeight);
            numOfTurbineThree = land/2000;
            oneTurbineOutputThree = (0.5*air_density*rotor_swept_area*coefficient_of_performance*(wind_velocity*wind_velocity*wind_velocity)*generator_efficiency*gear_box_bearing_efficiency)/1000000;
            totalTurbineOutputThree = numOfTurbineThree*oneTurbineOutputThree;

        }

    }



}
