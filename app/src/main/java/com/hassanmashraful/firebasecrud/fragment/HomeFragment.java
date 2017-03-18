package com.hassanmashraful.firebasecrud.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.hassanmashraful.firebasecrud.activity.Map_Activity;
import com.hassanmashraful.firebasecrud.model.LocationDetails;
import com.hassanmashraful.firebasecrud.model.User;

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
    private String address;
    private String landDetails;
    private double latitude, longitude;

    private static String email;

    ArrayList<LocationDetails> locationDetailses = new ArrayList<>();

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

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.mapHome);
        mapFragment.getMapAsync(this);

        userDataCall(); //getting user data

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    private void userDataCall(){

        mList.child(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                name = dataSnapshot.child("name").getValue().toString();
                phnNum = dataSnapshot.child("phnNum").getValue().toString();
                landDetails = dataSnapshot.child("landDetails").getValue().toString();
                address = dataSnapshot.child("address").getValue().toString();
                latitude = Double.parseDouble(dataSnapshot.child("latitude").getValue().toString());
                longitude = Double.parseDouble(dataSnapshot.child("longitude").getValue().toString());

                nameTV.setText("Name: "+name);
                phnTV.setText("Phone Num: "+phnNum);
                landSizeTV.setText("Land Details: "+landDetails);
                landTypTV.setText("Address: "+address);
                prevTV.setText("Still working");
                addTV.setText("Address: "+address);
                latLonTV.setText("Latitude: "+latitude+"\n"+" "+"Longitude: "+longitude);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
/*
    @Override
    public void onMapReady(GoogleMap googleMap) {

                mMap = googleMap;

                mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Response").snippet(address).icon(BitmapDescriptorFactory.fromResource(R.drawable.wlll)));
                //marker.

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude),1));

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


                        // Add the button

                        return false;
                    }
                });



        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(, -82.80654),7));

    }*/

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

}
