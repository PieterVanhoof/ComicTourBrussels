package ehb.be.comictourbrussels.Room;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class RestaurantDAO {
    private static final RestaurantDAO ourInstance = new RestaurantDAO();

    public static RestaurantDAO getInstance() {
        return ourInstance;
    }

    private RestaurantDAO() {
    }

    //resto's
    private ArrayList<Restaurant> Restaurants;

    public ArrayList<Restaurant> getRestaurants() {
        Restaurants = new ArrayList<>();

        Restaurants.add(new Restaurant(new LatLng(50.845171, 4.346929), "Moeder Lambic fontainas","Tof Restaurant!"));
        Restaurants.add(new Restaurant(new LatLng(50.847610, 4.350156), "Balls & Glory!","Tof Restaurant!"));


        return Restaurants;
    }
}
