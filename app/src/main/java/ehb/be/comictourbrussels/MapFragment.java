package ehb.be.comictourbrussels;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import ehb.be.comictourbrussels.Room.Comic;
import ehb.be.comictourbrussels.Room.ComicDatabase;
import ehb.be.comictourbrussels.Room.WC;
import ehb.be.comictourbrussels.Utils.InfoWindowAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mGoogleMap;
    private final LatLng BRUSSEL = new LatLng(50.858712, 4.347446);
    private final int requestLocation = 2;
    private Activity context;
    private MapView mv;
    private ArrayList<Marker> wcMarkerList, visitedList, todoList;
    private Button btnWc, btnVisited, btnToDo;


    public static MapFragment newInstance() {
        return new MapFragment();
    }

    private View.OnClickListener wcButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            for (Marker wc : wcMarkerList){
                if(wc.isVisible()){
                    wc.setVisible(false);
                    btnWc.setTextColor(Color.rgb(200,0,0));
                }else{
                    wc.setVisible(true);
                    btnWc.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }
            }
        }
    };

    private View.OnClickListener todoButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for (Marker todo : todoList){
                if(todo.isVisible()){
                    todo.setVisible(false);
                    btnToDo.setTextColor(Color.rgb(200,0,0));
                }else{
                    todo.setVisible(true);
                    btnToDo.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }
            }
        }
    };

    private View.OnClickListener visitedButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for (Marker visited : visitedList){
                if(visited.isVisible()){
                    visited.setVisible(false);
                    btnVisited.setTextColor(Color.rgb(200,0,0));
                }else{
                    visited.setVisible(true);
                    btnVisited.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        visitedList = new ArrayList<>();
        todoList = new ArrayList<>();
        wcMarkerList = new ArrayList<>();


        mv = view.findViewById(R.id.fragment_map);
        mv.onCreate(savedInstanceState);
        mv.getMapAsync(this);

        btnWc = view.findViewById(R.id.btn_toilet);
        btnWc.setOnClickListener(wcButtonOnClickListener);
        btnToDo = view.findViewById(R.id.btn_todo);
        btnToDo.setOnClickListener(todoButtonOnClickListener);
        btnVisited = view.findViewById(R.id.btn_visited);
        btnVisited.setOnClickListener(visitedButtonOnClickListener);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();

    }

    @Override
    public void onResume() {
        super.onResume();
        mv.onResume();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        setupCamera();
        startLocationUpdates();
        addMarkers();
        wcMarkers();

    }


    public void addMarkers() {



        for (Comic comic : ComicDatabase.getInstance(context).getComicDAO().selectAllComic()) {
            String filename = comic.getImgID() + "ComicRoute.jpg";
            String path = context.getFilesDir() + "/" + filename;

            InfoWindowAdapter markerInfoWindow = new InfoWindowAdapter(context);

            mGoogleMap.setInfoWindowAdapter(markerInfoWindow);

            Log.d("TEST VISITED", comic.getVisited() + "");

            Float hue;
            if (comic.getVisited()) {
                hue = BitmapDescriptorFactory.HUE_BLUE;

                Marker m = mGoogleMap.addMarker(new MarkerOptions()
                        .title(comic.getPersonage()).snippet(comic.getAuthor())
                        .icon(BitmapDescriptorFactory.defaultMarker(hue)).position(new LatLng(comic.getLat(), comic.getLon())));

                m.setTag(path);

                visitedList.add(m);
            } else {
                hue = BitmapDescriptorFactory.HUE_RED;

                Marker m = mGoogleMap.addMarker(new MarkerOptions()
                        .title(comic.getPersonage()).snippet(comic.getAuthor())
                        .icon(BitmapDescriptorFactory.defaultMarker(hue)).position(new LatLng(comic.getLat(), comic.getLon())));

                m.setTag(path);

                todoList.add(m);
            }




            mGoogleMap.setOnInfoWindowClickListener(this);
        }

    }
    private void wcMarkers (){
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_wc);

        for (WC wc : ComicDatabase.getInstance(context).getComicDAO().selectAllWC()) {
            mGoogleMap.addMarker(new MarkerOptions().icon(icon).title("WC").snippet(wc.getAdressN())
                    .position(new LatLng(wc.getLat(),wc.getLon())));
            Marker wcMarker = mGoogleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_wc)).title("WC").snippet(wc.getAdressN())
                    .position(new LatLng(wc.getLat(), wc.getLon())));
            wcMarkerList.add(wcMarker);

            //icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_wc))
        }
    }

    private void setupCamera() {

        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(BRUSSEL, 14);
        mGoogleMap.animateCamera(update);

    }

    private void startLocationUpdates() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
                requestPermissions(permissions, requestLocation);


            } else {
                mGoogleMap.setMyLocationEnabled(true);
            }

        } else {
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == requestLocation) {
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_GRANTED) {
                    mGoogleMap.setMyLocationEnabled(true);
                }
            }

        }

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        for (Comic c : ComicDatabase.getInstance(context).getComicDAO().selectAllComic()) {

            if (c.getPersonage().contains(marker.getTitle())) {
                if (c.getVisited()) {
                    c.setVisited(false);
                    marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    todoList.add(marker);
                    visitedList.remove(marker);
                    if(todoList.get(0).isVisible()){
                        marker.setVisible(true);
                    }else{
                        marker.setVisible(false);
                    }
                } else {
                    c.setVisited(true);
                    marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    todoList.remove(marker);
                    visitedList.add(marker);
                    if(visitedList.get(0).isVisible()){
                        marker.setVisible(true);
                    }else{
                        marker.setVisible(false);
                    }
                }
                ComicDatabase.getInstance(context).getComicDAO().updateComic(c);
            }
        }

    }
}
