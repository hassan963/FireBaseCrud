package com.hassanmashraful.firebasecrud.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hassanmashraful.firebasecrud.R;
import com.hassanmashraful.firebasecrud.key.key_url;

/**
 * Created by Hassan M.Ashraful on 3/21/2017.
 */

public class FragmentOne extends Fragment {
    TextView nameTV, phnTV, landSizeTV, numOfTurbine, oneTurbineOutput, totalTurbineOutput, latLonTV;

    private SharedPreferences sharedpreferences;

    private double numOfTurbineOne,
            oneTurbineOutputOne,
            totalTurbineOutputOne;

    public FragmentOne() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, null, false);
        nameTV = (TextView) view.findViewById(R.id.nameTV);
        phnTV = (TextView) view.findViewById(R.id.phnTV);
        landSizeTV = (TextView) view.findViewById(R.id.landSizeTV);
        numOfTurbine = (TextView) view.findViewById(R.id.numOfTurbine);

        oneTurbineOutput = (TextView) view.findViewById(R.id.oneTurbineOutput);
        totalTurbineOutput = (TextView) view.findViewById(R.id.totalTurbineOutput);
        latLonTV = (TextView) view.findViewById(R.id.latLonTV);

        sharedpreferences = this.getActivity().getSharedPreferences(key_url.TurbineDetails, Context.MODE_PRIVATE);

        double a = getDouble(sharedpreferences,key_url.numOfTurbineOne ,numOfTurbineOne);
        double b = getDouble(sharedpreferences,key_url.oneTurbineOutputOne ,oneTurbineOutputOne);
        double c = getDouble(sharedpreferences,key_url.totalTurbineOutputOne ,totalTurbineOutputOne);
        nameTV.setText("hasan");
        phnTV.setText("d");
        landSizeTV.setText("1");
        numOfTurbine.setText(String.valueOf(a));
        totalTurbineOutput.setText(String.valueOf(String.valueOf(b)));
        oneTurbineOutput.setText(String.valueOf(String.valueOf(c)));

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

}
