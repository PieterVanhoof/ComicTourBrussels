package ehb.be.comictourbrussels;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

import ehb.be.comictourbrussels.Room.Comic;
import ehb.be.comictourbrussels.Room.ComicDatabase;
import ehb.be.comictourbrussels.Room.Restaurant;
import ehb.be.comictourbrussels.Room.RestaurantDAO;
import ehb.be.comictourbrussels.Room.WC;
import ehb.be.comictourbrussels.Utils.InfoWindowAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, LocationListener {

    private GoogleMap mGoogleMap;
    private final LatLng BRUSSEL = new LatLng(50.858712, 4.347446);
    private final int requestLocation = 2;
    private Activity context;
    private MapView mv;
    private ArrayList<Marker> wcMarkerList, visitedList, todoList, restoList;
    private Button btnWc, btnVisited, btnToDo, btnResto;
    private Location user;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    private final View.OnClickListener restoButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for (Marker resto : restoList) {
                if (resto.isVisible()) {
                    resto.setVisible(false);
                    btnResto.setTextColor(Color.rgb(200, 0, 0));
                } else {
                    resto.setVisible(true);
                    btnResto.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }
            }
        }
    };

    private final View.OnClickListener wcButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            for (Marker wc : wcMarkerList) {
                if (wc.isVisible()) {
                    wc.setVisible(false);
                    btnWc.setTextColor(Color.rgb(200, 0, 0));
                } else {
                    wc.setVisible(true);
                    btnWc.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }
            }
        }
    };

    private final View.OnClickListener todoButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for (Marker todo : todoList) {
                if (todo.isVisible()) {
                    todo.setVisible(false);
                    btnToDo.setTextColor(Color.rgb(200, 0, 0));
                } else {
                    todo.setVisible(true);
                    btnToDo.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }
            }
        }
    };

    private final View.OnClickListener visitedButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for (Marker visited : visitedList) {
                if (visited.isVisible()) {
                    visited.setVisible(false);
                    btnVisited.setTextColor(Color.rgb(200, 0, 0));
                } else {
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
        restoList = new ArrayList<>();

        mv = view.findViewById(R.id.fragment_map);
        mv.onCreate(savedInstanceState);
        mv.getMapAsync(this);

        btnWc = view.findViewById(R.id.btn_toilet);
        btnWc.setOnClickListener(wcButtonOnClickListener);
        btnToDo = view.findViewById(R.id.btn_todo);
        btnToDo.setOnClickListener(todoButtonOnClickListener);
        btnVisited = view.findViewById(R.id.btn_visited);
        btnVisited.setOnClickListener(visitedButtonOnClickListener);
        btnResto = view.findViewById(R.id.btn_resto);
        btnResto.setOnClickListener(restoButtonOnClickListener);


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
        onLocationChanged(user);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        setupCamera();
        startLocationUpdates();
        addMarkers();
        wcMarkers();
        RestoMarkers();
    }


    private void addMarkers() {

        for (Comic comic : ComicDatabase.getInstance(context).getComicDAO().selectAllComic()) {
            String filename = comic.getImgID() + "ComicRoute.jpg";
            String path = context.getFilesDir() + "/" + filename;

            InfoWindowAdapter markerInfoWindow = new InfoWindowAdapter(context);

            mGoogleMap.setInfoWindowAdapter(markerInfoWindow);


            if (comic.getVisited()) {

                Marker m = mGoogleMap.addMarker(new MarkerOptions()
                        .title(comic.getPersonage()).snippet(comic.getAuthor())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_check)).position(new LatLng(comic.getLat(), comic.getLon())));

                m.setTag(path);

                visitedList.add(m);
            } else {

                Marker m = mGoogleMap.addMarker(new MarkerOptions()
                        .title(comic.getPersonage()).snippet(comic.getAuthor())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_todo)).position(new LatLng(comic.getLat(), comic.getLon())));

                m.setTag(path);

                todoList.add(m);
            }


            mGoogleMap.setOnInfoWindowClickListener(this);
        }

    }
    private void wcMarkers (){

        for (WC wc : ComicDatabase.getInstance(context).getComicDAO().selectAllWC()) {

            Marker wcMarker = mGoogleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_wc)).title("WC").snippet(wc.getAdressN())
                    .position(new LatLng(wc.getLat(), wc.getLon())));
            wcMarker.setTag("icon");
            wcMarkerList.add(wcMarker);


        }
    }
    private void RestoMarkers(){
        for (Restaurant restaurant : RestaurantDAO.getInstance().getRestaurants()){
            Marker restoMarker = mGoogleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_resto)).title(restaurant.getNaam()).snippet(restaurant.getBeschrijving()).position(restaurant.getLatLng()));
            restoMarker.setTag("icon");
            restoList.add(restoMarker);
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
                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_todo));
                    todoList.add(marker);
                    visitedList.remove(marker);
                    if(todoList.get(0).isVisible()){
                        marker.setVisible(true);
                    }else{
                        marker.setVisible(false);
                    }
                } else {
                    c.setVisited(true);
                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_check));
                    visitedList.add(marker);
                    todoList.remove(marker);
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


    @Override
    public void onLocationChanged(Location location) {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(context, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null) {
                    user = new Location("user");
                    user.setLatitude(location.getLatitude());
                    user.setLongitude(location.getLongitude());
                }
                for (Comic c : ComicDatabase.getInstance(context).getComicDAO().selectAllComic()){
                    Location locationcomic = new Location("locationcomic");
                    locationcomic.setLatitude(c.getLat());
                    locationcomic.setLongitude(c.getLon());
                    float distance = locationcomic.distanceTo(user);
                    if (distance < 1500)
                        Toast.makeText(context, c.getPersonage()+" "+getString(R.string.txt_nearby), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
