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
    private String coordinate;
    private String personage;
    private String author;

    @Ignore
    private Comic() {
    }


    public Comic(String personage, String Cutcoord) {
        this.personage = personage;
        this.coordinate = Cutcoord;
    }

    @Ignore
    public Comic(long id, String coordinate, String personage, String author) {
        this.id = id;
        this.coordinate = coordinate;
        this.personage = personage;
        this.author = author;
    }


    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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


}
