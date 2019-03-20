package ehb.be.comictourbrussels.Room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(version = 1,entities = {Comic.class},exportSchema = false)
public abstract class ComicDatabase extends RoomDatabase {

    private static ComicDatabase instance;

    public static ComicDatabase getInstance(Context context){
        if(instance == null) {
            instance = createDatabase(context);
        }
        return instance;
    }

    private static ComicDatabase createDatabase (Context context){
        return Room.databaseBuilder(context, ComicDatabase.class,"comic.db").allowMainThreadQueries().build();
    }

    public abstract ComicDao getComicDAO();


}
