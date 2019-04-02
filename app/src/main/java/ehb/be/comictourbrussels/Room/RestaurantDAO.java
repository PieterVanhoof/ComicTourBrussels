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
        Restaurants.add(new Restaurant(new LatLng(50.841041, 4.338798), "BarBQ café", "Mmmmmh Bacon!"));
        Restaurants.add(new Restaurant(new LatLng(50.842434, 4.345808), "Comme Chez Soi", "Tof Restaurant!"));
        Restaurants.add(new Restaurant(new LatLng(50.849602, 4.347221), "Fin De Siècle", "Bangers & Mash"));
        Restaurants.add(new Restaurant(new LatLng(50.848705, 4.353101), "L'Ana Thème", "Gastronomie"));


        return Restaurants;
    }
}
