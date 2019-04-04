package ehb.be.comictourbrussels.Room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity
public class Comic implements Serializable {

    @NonNull @PrimaryKey
    private String id;
    private double lat;
    private double lon;
    private String personage;
    private String author;
    private String ImgID;
    private String jaar;
    private Boolean visited;

    @Ignore
    private Comic() {
    }


    public Comic(String id, double lat, double lon, String personage, String author, String ImgID, String jaar, Boolean visited) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.personage = personage;
        this.author = author;
        this.ImgID = ImgID;
        this.jaar = jaar;
        this.visited = visited;

    }

    public Boolean getVisited() {
        return visited;
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    public String getJaar() { return jaar; }

    public void setJaar(String year) { this.jaar = jaar; }

    public String getImgID() {
        return ImgID;
    }

    public void setImgID(String imgID) {
        ImgID = imgID;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

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
                "id='" + id + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", personage='" + personage + '\'' +
                ", author='" + author + '\'' +
                ", ImgID='" + ImgID + '\'' +
                ", jaar='" + jaar + '\'' +
                ", visited=" + visited +
                '}';
    }
}
