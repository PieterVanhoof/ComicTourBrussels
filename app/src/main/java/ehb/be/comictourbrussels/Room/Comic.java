package ehb.be.comictourbrussels.Room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

@Entity
public class Comic implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private double lat;
    private double lon;
    private String personage;
    private String author;
    //private String imageId;

    @Ignore
    private Comic() {
    }

    public Comic(String personage) {
        this.personage = personage;
    }

    @Ignore
    public Comic(long id, double lat, double lon, String personage, String author) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.personage = personage;
        this.author = author;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getPersonage() {
        return personage;
    }

    public void setPersonage(String personage) {
        this.personage = personage;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Comic{" +
                "id=" + id +
                ", lat=" + lat +
                ", lon=" + lon +
                ", personage='" + personage + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
