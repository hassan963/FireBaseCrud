package com.hassanmashraful.firebasecrud.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hassanmashraful.firebasecrud.R;
import com.hassanmashraful.firebasecrud.key.key_url;

import static com.hassanmashraful.firebasecrud.activity.MainActivity.EncodeString;

/**
 * Created by Hassan M.Ashraful on 3/21/2017.
 */

public class FragmentOne extends Fragment {
    TextView numOfTurbineOneTV,
            oneTurbineOutputOneTV,
            totalTurbineOutputOneTV,
            numOfTurbineTwoTV,
            oneTurbineOutputTwoTV,
            totalTurbineOutputTwoTV,
            numOfTurbineThreeTV,
            oneTurbineOutputThreeTV,
            totalTurbineOutputThreeTV;

    private SharedPreferences sharedpreferences;

    private String numOfTurbineOne,
            oneTurbineOutputOne,
            totalTurbineOutputOne,
            numOfTurbineTwo,
            oneTurbineOutputTwo,
            totalTurbineOutputTwo,
            numOfTurbineThree,
            oneTurbineOutputThree,
            totalTurbineOutputThree;

    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference verdictReference, userReference;
    private static String email = "b@a,com";

    public FragmentOne() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, null, false);
        getUSERDATA();
        numOfTurbineOneTV = (TextView) view.findViewById(R.id.numOfTurbineOneTV);
        totalTurbineOutputOneTV = (TextView) view.findViewById(R.id.totalTurbineOutputOneTV);
        oneTurbineOutputOneTV = (TextView) view.findViewById(R.id.oneTurbineOutputOneTV);

        oneTurbineOutputTwoTV = (TextView) view.findViewById(R.id.oneTurbineOutputTwoTV);
        totalTurbineOutputTwoTV = (TextView) view.findViewById(R.id.totalTurbineOutputTwoTV);
        numOfTurbineTwoTV = (TextView) view.findViewById(R.id.numOfTurbineTwoTV);

        numOfTurbineThreeTV = (TextView) view.findViewById(R.id.numOfTurbineThreeTV);
        oneTurbineOutputThreeTV = (TextView) view.findViewById(R.id.oneTurbineOutputThreeTV);
        totalTurbineOutputThreeTV = (TextView) view.findViewById(R.id.totalTurbineOutputThreeTV);

/*        sharedpreferences = this.getActivity().getSharedPreferences(key_url.TurbineDetails, Context.MODE_PRIVATE);

        double a = getDouble(sharedpreferences,key_url.numOfTurbineOne ,numOfTurbineOne);
        double b = getDouble(sharedpreferences,key_url.oneTurbineOutputOne ,oneTurbineOutputOne);
        double c = getDouble(sharedpreferences,key_url.totalTurbineOutputOne ,totalTurbineOutputOne);*/

        mFirebaseInstance = FirebaseDatabase.getInstance();

        verdictReference = mFirebaseInstance.getReference("verdict");
        userReference = mFirebaseInstance.getReference("users");

        /*nameTV.setText("hasan");
        phnTV.setText("d");
        landSizeTV.setText("1");
        numOfTurbine.setText(String.valueOf(a));
        totalTurbineOutput.setText(String.valueOf(String.valueOf(b)));
        oneTurbineOutput.setText(String.valueOf(String.valueOf(c)));
        Log.v("@@$%%^$#%$", a+"   "+b+"   "+c);*/


        verdictReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(email)){
                    verdictResult();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        /*if (bundle != null) {
            numOfTurbineOne = bundle.getDouble("numOfTurbineOne", numOfTurbineOne);
            oneTurbineOutputOne = bundle.getDouble("numOfTurbineOne", oneTurbineOutputOne);
            totalTurbineOutputOne = bundle.getDouble("numOfTurbineOne", totalTurbineOutputOne);
        }*/


    }

    double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(defaultValue)));
    }

    //Getting current user
    private void getUSERDATA(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                //String providerId = profile.getProviderId();

                // UID specific to the provider
                email = profile.getUid();
                email = EncodeString(email);

            };
        }

    }

    private void verdictResult(){
        verdictReference.child(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                numOfTurbineOne = dataSnapshot.child("numOfTurbineOne").getValue().toString();
                oneTurbineOutputOne = dataSnapshot.child("oneTurbineOutputOne").getValue().toString();
                totalTurbineOutputOne = dataSnapshot.child("totalTurbineOutputOne").getValue().toString();

                numOfTurbineTwo = dataSnapshot.child("numOfTurbineTwo").getValue().toString();
                oneTurbineOutputTwo = dataSnapshot.child("oneTurbineOutputTwo").getValue().toString();
                totalTurbineOutputTwo = dataSnapshot.child("totalTurbineOutputTwo").getValue().toString();

                numOfTurbineThree = dataSnapshot.child("numOfTurbineThree").getValue().toString();
                oneTurbineOutputThree = dataSnapshot.child("oneTurbineOutputThree").getValue().toString();
                totalTurbineOutputThree = dataSnapshot.child("totalTurbineOutputThree").getValue().toString();

                numOfTurbineOneTV.setText("Number of turbine: "+numOfTurbineOne);
                oneTurbineOutputOneTV.setText("Per turbine output"+oneTurbineOutputOne+" mw ");
                totalTurbineOutputOneTV.setText("Total output"+totalTurbineOutputOne+" mw ");

                numOfTurbineTwoTV.setText("Number of turbine: "+numOfTurbineTwo);
                oneTurbineOutputTwoTV.setText("Per turbine output"+oneTurbineOutputTwo+" mw ");
                totalTurbineOutputTwoTV.setText("Total output"+totalTurbineOutputTwo+" mw ");

                numOfTurbineThreeTV.setText("Number of turbine: "+numOfTurbineThree);
                oneTurbineOutputThreeTV.setText("Per turbine output"+oneTurbineOutputThree+" mw ");
                totalTurbineOutputThreeTV.setText("Total output"+totalTurbineOutputThree+" mw ");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
