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
    private String imageId;

    @Ignore
    private Comic() {
    }

    public Comic(String personage, String imageId) {
        this.personage = personage;
        this.imageId = imageId;
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


    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
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
