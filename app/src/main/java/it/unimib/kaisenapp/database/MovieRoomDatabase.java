package it.unimib.kaisenapp.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import it.unimib.kaisenapp.Constants;
import it.unimib.kaisenapp.models.MovieDao;
import it.unimib.kaisenapp.models.MovieEntity;
import kotlin.jvm.Volatile;

@Database(entities = {MovieEntity.class}, version = Constants.DATABASE_VERSION, exportSchema = true)
public abstract class MovieRoomDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

    @Volatile
    private static volatile MovieRoomDatabase INSTANCE;
    /*private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);*/

    public static MovieRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MovieRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            MovieRoomDatabase.class,
                            Constants.NEWS_DATABASE_NAME).build();
                }
            }
        }
        return INSTANCE;
    }
}


