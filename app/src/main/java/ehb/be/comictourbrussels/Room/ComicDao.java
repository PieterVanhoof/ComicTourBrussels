package ehb.be.comictourbrussels.Room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import ehb.be.comictourbrussels.Room.Comic;

@Dao
public interface ComicDao {

    //Comic
    @Insert
    void insertComic(Comic jComic);


    @Query("SELECT * FROM Comic")
    List<Comic> selectAllComic();

    @Query("SELECT * FROM Comic WHERE id = :id")
    Comic selectComicByID(long id);

    //Wc's
    @Insert
    void insertWc(WC jWC);

    @Query("SELECT * FROM WC")
    List<WC> selectAllWC();


}
