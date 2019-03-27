package ehb.be.comictourbrussels.Room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ehb.be.comictourbrussels.Room.Comic;

@Dao
public interface ComicDao {

    @Insert
    void insertComic(Comic jComic);

    @Update
    void updateComic(Comic jComic);


    @Query("SELECT * FROM Comic")
    List<Comic> selectAllComic();

    @Query("SELECT * FROM Comic WHERE id = :id")
    Comic selectComicByID(long id);

}
