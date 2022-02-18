package it.unimib.kaisenapp.database;

import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import it.unimib.kaisenapp.utils.Constants;
import kotlin.jvm.Volatile;

@androidx.room.Database(entities = {MovieEntity.class,TvShowEntity.class}, version = Constants.DATABASE_VERSION, exportSchema = false)
public abstract class Database extends RoomDatabase {

    public abstract MovieDao movieDao();
    public abstract TvShowDao tvShowDao();

    @Volatile
    private static volatile Database INSTANCE;

    public static Database getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            Database.class,
                            Constants.NEWS_DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}


