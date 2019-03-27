package ehb.be.comictourbrussels.Room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(version = 1, entities = {Comic.class}, exportSchema = false)
public abstract class VisitedDatabase extends RoomDatabase {

    private static VisitedDatabase instance;

    public static VisitedDatabase getInstance(Context context) {
        if (instance == null) {
            instance = createDatabase(context);
        }
        return instance;
    }

    private static VisitedDatabase createDatabase(Context context) {
        return Room.databaseBuilder(context, VisitedDatabase.class, "visited.db").allowMainThreadQueries().build();
    }

    public abstract ComicDao getComicDAO();
}

