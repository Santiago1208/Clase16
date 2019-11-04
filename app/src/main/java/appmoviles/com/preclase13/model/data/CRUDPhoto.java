package appmoviles.com.preclase13.model.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;

import appmoviles.com.preclase13.app.AlbumApp;
import appmoviles.com.preclase13.model.driver.DBDriver;
import appmoviles.com.preclase13.model.entity.Album;
import appmoviles.com.preclase13.model.entity.Photo;


public class CRUDPhoto {


    public static void insertPhoto(Album album, Photo photo){
        DBDriver driver = DBDriver.getInstance(AlbumApp.getAppContext());
        SQLiteDatabase db = driver.getWritableDatabase();

        String sql = "INSERT INTO $TABLE($ID,$NAME,$DESC,$COMPLETE,$FK) VALUES('$VID','$VNAME','$VDESC',$VCOMPLETE,'$VFK')";
        sql = sql
                .replace("$TABLE", DBDriver.TABLE_PHOTO)
                .replace("$ID", DBDriver.PHOTO_ID)
                .replace("$NAME", DBDriver.PHOTO_NAME)
                .replace("$DESC", DBDriver.PHOTO_DESC)
                .replace("$COMPLETE", DBDriver.PHOTO_VIEWS)
                .replace("$FK", DBDriver.FK_ALMBUM_PHOTO)

                .replace("$VID", photo.getId())
                .replace("$VNAME", photo.getName())
                .replace("$VDESC", photo.getDescription())
                .replace("$VCOMPLETE", ""+photo.getViews())
                .replace("$VFK", album.getId());
        db.execSQL(sql);
        db.close();
    }


    public static HashMap<String, Photo> getAllPhotosOfAlbum(Album album){
        DBDriver driver = DBDriver.getInstance(AlbumApp.getAppContext());
        SQLiteDatabase db = driver.getReadableDatabase();
        HashMap<String, Photo> group = new HashMap<>();

        String sql = "SELECT * FROM $TABLE WHERE $FID = '$VFID'";
        sql = sql
                .replace("$TABLE", DBDriver.TABLE_PHOTO)
                .replace("$FID", DBDriver.FK_ALMBUM_PHOTO)
                .replace("$VFID",album.getId());
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do{
                String id = cursor.getString(cursor.getColumnIndex(DBDriver.PHOTO_ID));
                String name = cursor.getString(cursor.getColumnIndex(DBDriver.PHOTO_NAME));
                String desc = cursor.getString(cursor.getColumnIndex(DBDriver.PHOTO_DESC));
                int views = cursor.getInt(cursor.getColumnIndex(DBDriver.PHOTO_VIEWS));
                String albumid = cursor.getString(cursor.getColumnIndex(DBDriver.FK_ALMBUM_PHOTO));
                Photo task = new Photo(id, name, desc, views, albumid);
                group.put(id,task);
            }while (cursor.moveToNext());
        }

        db.close();
        return group;
    }


    public static void detelePhoto(Photo photo) {
        DBDriver driver = DBDriver.getInstance(AlbumApp.getAppContext());
        SQLiteDatabase db = driver.getWritableDatabase();
        String sql = "DELETE FROM $TABLE WHERE $ID = '$FID'";
        sql = sql
                .replace("$TABLE", DBDriver.TABLE_PHOTO)
                .replace("$ID", DBDriver.PHOTO_ID)
                .replace("$FID",photo.getId());
        db.execSQL(sql);
        db.close();
    }

    public static void increaseViews(Photo photo) {
        DBDriver driver = DBDriver.getInstance(AlbumApp.getAppContext());
        SQLiteDatabase db = driver.getWritableDatabase();
        String sql = "UPDATE $TABLE SET $COMPLETE=$VCOMPLETE WHERE $ID = '$FID'";
        sql = sql
                .replace("$TABLE", DBDriver.TABLE_PHOTO)
                .replace("$COMPLETE", DBDriver.PHOTO_VIEWS)
                .replace("$VCOMPLETE", ""+(photo.getViews()+1))
                .replace("$ID", DBDriver.PHOTO_ID)
                .replace("$FID",photo.getId());
        db.execSQL(sql);
        db.close();
    }

    public static void deleteAllPhotos(){
        DBDriver driver = DBDriver.getInstance(AlbumApp.getAppContext());
        SQLiteDatabase db = driver.getWritableDatabase();
        String sql = "DELETE FROM $TABLE";
        sql = sql.replace("$TABLE", DBDriver.TABLE_PHOTO);
        db.execSQL(sql);
        db.close();
    }
}
