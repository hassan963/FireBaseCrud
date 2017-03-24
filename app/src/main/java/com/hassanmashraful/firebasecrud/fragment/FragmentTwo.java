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

import com.hassanmashraful.firebasecrud.R;
import com.hassanmashraful.firebasecrud.key.key_url;

/**
 * Created by Hassan M.Ashraful on 3/21/2017.
 */

public class FragmentTwo extends Fragment {
    TextView nameTV, phnTV, landSizeTV, numOfTurbine, oneTurbineOutput, totalTurbineOutput, latLonTV;
    private SharedPreferences sharedpreferences;

    double numOfTurbineTwo, oneTurbineOutputTwo, totalTurbineOutputTwo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, null, false);
       /* nameTV = (TextView) view.findViewById(R.id.nameTV);
        phnTV = (TextView) view.findViewById(R.id.phnTV);
        landSizeTV = (TextView) view.findViewById(R.id.landSizeTV);
        numOfTurbine = (TextView) view.findViewById(R.id.numOfTurbine);

        oneTurbineOutput = (TextView) view.findViewById(R.id.oneTurbineOutput);
        totalTurbineOutput = (TextView) view.findViewById(R.id.totalTurbineOutput);
        latLonTV = (TextView) view.findViewById(R.id.latLonTV);*/

        sharedpreferences = this.getActivity().getSharedPreferences(key_url.TurbineDetails, Context.MODE_PRIVATE);

        /*nameTV.setText("hasan");
        phnTV.setText("d");
        landSizeTV.setText("1");*/
        sharedpreferences = this.getActivity().getSharedPreferences(key_url.TurbineDetails, Context.MODE_PRIVATE);

        double a = getDouble(sharedpreferences, key_url.numOfTurbineOne ,numOfTurbineTwo);
        double b = getDouble(sharedpreferences, key_url.oneTurbineOutputOne ,oneTurbineOutputTwo);
        double c = getDouble(sharedpreferences, key_url.totalTurbineOutputOne ,totalTurbineOutputTwo);
        /*nameTV.setText("hasan");
        phnTV.setText("d");
        landSizeTV.setText("1");*/
       /* numOfTurbine.setText(String.valueOf(String.valueOf(a)));
        totalTurbineOutput.setText(String.valueOf(String.valueOf(String.valueOf(b))));
        oneTurbineOutput.setText(String.valueOf(String.valueOf(String.valueOf(c))));*/
        Log.v("@@$%%^$#%$", a+"   "+b+"   "+c);

        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(defaultValue)));
    }
}
