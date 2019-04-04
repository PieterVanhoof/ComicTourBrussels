package ehb.be.comictourbrussels.Room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class WC implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private double lat;
    private double lon;
    private String AdressN;
    private String AdressF;


    public WC() {
    }

    public WC( double lat, double lon, String adressN, String adressF) {

        this.lat = lat;
        this.lon = lon;
        AdressN = adressN;
        AdressF = adressF;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setAdressN(String adressN) {
        AdressN = adressN;
    }

    public String getAdressF() {
        return AdressF;
    }

    public void setAdressF(String adressF) {
        AdressF = adressF;
    }

    public double getLat() {
        return lat;
    }


    public double getLon() {
        return lon;
    }

    public String getAdressN() {
        return AdressN;
    }


    @Override
    public String toString() {
        return "WC{" +
                "id=" + id +
                ", lat=" + lat +
                ", lon=" + lon +
                ", AdressN='" + AdressN + '\'' +
                ", AdressF='" + AdressF + '\'' +
                '}';
    }
}
