package ehb.be.comictourbrussels.Room;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Restaurant implements Serializable {

    private LatLng latLng;
    private String naam;
    private String beschrijving;

    public Restaurant(LatLng latLng, String naam, String beschrijving) {
        this.latLng = latLng;
        this.naam = naam;
        this.beschrijving = beschrijving;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }
}
