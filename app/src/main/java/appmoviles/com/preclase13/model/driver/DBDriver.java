package appmoviles.com.preclase13.model.driver;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBDriver extends SQLiteOpenHelper {

    private static DBDriver instance;

    public static synchronized DBDriver getInstance(Context context){
        if(instance == null){
            instance = new DBDriver(context);
        }
        return instance;
    }


    public static final String DBNAME = "albumappfinal";
    public static final int DBVERSION = 15;


    //TABLE TASKLIST
    public static final String TABLE_ALBUM = "album";
    public static final String ALBUM_ID = "id";
    public static final String ALBUM_NAME = "name";
    public static final String ALBUM_DATE = "date";

    //TABLE TASK
    public static final String TABLE_PHOTO = "photo";
    public static final String PHOTO_ID = "id";
    public static final String PHOTO_NAME = "name";
    public static final String PHOTO_DESC = "description";
    public static final String PHOTO_VIEWS = "likes";
    public static final String FK_ALMBUM_PHOTO = "almbumid";

    private DBDriver(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //CREAR TABLAS
        String CREATE_TABLE_TASKLIST = "CREATE TABLE $TABLE($ID TEXT PRIMARY KEY, $NAME TEXT, $DATE INTEGER)";

        CREATE_TABLE_TASKLIST = CREATE_TABLE_TASKLIST
                .replace("$TABLE", TABLE_ALBUM)
                .replace("$ID", ALBUM_ID)
                .replace("$NAME", ALBUM_NAME)
                .replace("$DATE", ALBUM_DATE);

        //TABLE TASK
        String CREATE_TABLE_TASK = "CREATE TABLE $TABLE($ID TEXT PRIMARY KEY, $NAME TEXT, $DESC TEXT, $COMPLETE INTEGER, $FK TEXT, FOREIGN KEY ($FK) REFERENCES $FTABLE($FID))";

        CREATE_TABLE_TASK = CREATE_TABLE_TASK
                .replace("$TABLE", TABLE_PHOTO)
                .replace("$ID", PHOTO_ID)
                .replace("$NAME", PHOTO_NAME)
                .replace("$DESC", PHOTO_DESC)
                .replace("$COMPLETE", PHOTO_VIEWS)
                .replace("$FK", FK_ALMBUM_PHOTO)
                .replace("$FTABLE", TABLE_ALBUM)
                .replace("$FID", ALBUM_ID);

        db.execSQL(CREATE_TABLE_TASKLIST);
        db.execSQL(CREATE_TABLE_TASK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALBUM);
        onCreate(db);
    }


}
