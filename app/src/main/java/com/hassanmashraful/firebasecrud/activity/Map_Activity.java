package com.hassanmashraful.firebasecrud.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hassanmashraful.firebasecrud.Location;
import com.hassanmashraful.firebasecrud.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Hassan M.Ashraful on 3/16/2017.
 */

public class Map_Activity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference mList;
    private ArrayList<Location> futureLocation = new ArrayList<>();
    private FirebaseDatabase mFirebaseInstance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Wind Mill in BD");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                    .getColor(R.color.colorPrimary)));
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFirebaseInstance = FirebaseDatabase.getInstance();

        mList = mFirebaseInstance.getReference("windfirm/future");



    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

       /* gettingData();


        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(27.975700, -82.76094);
        mMap.addMarker(new MarkerOptions().position(new LatLng(28.34796, -82.74556)).title("Yoo bitches").snippet("We r here").icon(BitmapDescriptorFactory.fromResource(R.drawable.wlll)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(27.98978, -82.77591)).title("Cool babe").snippet("We r here").icon(BitmapDescriptorFactory.fromResource(R.drawable.wlll)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(27.93368, -82.74567)).title("Dada eshe gelam").snippet("We r here").icon(BitmapDescriptorFactory.fromResource(R.drawable.wlll)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(27.91790, -82.76057)).title("Chudir vai").snippet("We r here").icon(BitmapDescriptorFactory.fromResource(R.drawable.wlll)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(27.96978, -82.73024)).title("Coffe").snippet("We r here").icon(BitmapDescriptorFactory.fromResource(R.drawable.wlll)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(28.01287, -82.75684)).title("XXX").snippet("We r here").icon(BitmapDescriptorFactory.fromResource(R.drawable.wlll)));
*/

       mList.addListenerForSingleValueEvent(new ValueEventListener() {
           double location_left, location_right;
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               for (DataSnapshot child : dataSnapshot.getChildren()) {
                   String rightLocation = child.child("lat").getValue().toString();
                   String leftLocation = child.child("lon").getValue().toString();

                    location_left = Double.parseDouble(leftLocation);
                    location_right = Double.parseDouble(rightLocation);
                   //String party_title = child.child("party/party_title").getValue().toString();
                   //LatLng cod = new LatLng(location_left, location_right);
                   mMap.addMarker(new MarkerOptions().position(new LatLng(location_right, location_left)).title("Yoo bitches").snippet("We r here").icon(BitmapDescriptorFactory.fromResource(R.drawable.wlll)));
                   //mMap.addMarker(new MarkerOptions().position(cod).title(party_title));
                   //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location_right, location_left)));
               }
               LatLng position = new LatLng(88.0433, 92.66942);
               mMap.moveCamera(CameraUpdateFactory.newLatLng(position));

               /*MapController controller = mMap.getController();
               controller.zoomToSpan(
                       (maxLat - minLat),
                       (maxLong - minLong));

               controller.animateTo(new GeoPoint(
                       (maxLat + minLat)/2,
                       (maxLong + minLong)/2 ));*/
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.02, -82.80654),7));

    }

    private void positionCamera(LatLng origin, LatLng dest){
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(origin);
        if(dest != null) builder.include(dest);
        LatLngBounds bounds = builder.build();
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 30));
    }

    public void gettingData(){
        mList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Get map of users in datasnapshot
                //loc = dataSnapshot.getValue().toString();
                //apiTV.setText(dataSnapshot.getValue().toString());
                //Log.v("#$$$%%$$#", loc);
                //Get map of users in datasnapshot
                collectPhoneNumbers((Map<String,Object>) dataSnapshot.getValue());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
