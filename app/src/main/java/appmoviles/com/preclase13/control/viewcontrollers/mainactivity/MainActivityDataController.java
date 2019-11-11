package appmoviles.com.preclase13.control.viewcontrollers.mainactivity;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import appmoviles.com.preclase13.app.AlbumApp;
import appmoviles.com.preclase13.model.data.CRUDAlbum;
import appmoviles.com.preclase13.model.data.CRUDPhoto;
import appmoviles.com.preclase13.model.entity.Album;
import appmoviles.com.preclase13.model.entity.Photo;
import appmoviles.com.preclase13.model.entity.User;
import appmoviles.com.preclase13.model.remote.DatabaseConstants;

public class MainActivityDataController {
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private FirebaseStorage storage;
    private MainActivityCallbacks observer;

    public void init() {
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        checkSignedIn();
        downloadOwnUser();
        downloadAllDataForSync();
    }

    private void checkSignedIn() {
        if (auth.getCurrentUser() == null) {
            observer.onUserNoAuth();
            return;
        }
    }

    private void downloadAllDataForSync() {
        db.getReference().child(DatabaseConstants.EMBEDDING)
                .child(auth.getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        CRUDPhoto.deleteAllPhotos();
                        CRUDAlbum.deleteAllAlbums();
                        //El for de los albumes
                        for (DataSnapshot album : dataSnapshot.getChildren()) {
                            Album nAlbum = album.getValue(Album.class);
                            CRUDAlbum.insertAlbum(nAlbum);
                            //El for de las fotos
                            for (String photoKey : nAlbum.getPhotos().keySet()) {
                                Photo photo = nAlbum.getPhotos().get(photoKey);
                                CRUDPhoto.insertPhoto(nAlbum, photo);
                            }
                        }
                        observer.onAllDataDownloaded();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void downloadOwnUser() {
        db.getReference().child(DatabaseConstants.USERS)
                .child(auth.getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Response
                        User user = dataSnapshot.getValue(User.class);
                        observer.onUserDownloaded(user);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void uploadPhotos() {
        try {
            File folder = AlbumApp.getAppContext().getExternalFilesDir(null);
            String[] files = folder.list();
            for (int i = 0; i < files.length; i++) {
                Log.e(">>>", files[i]);//Solo muestra el nombre del archivo
                File imageFile = new File(folder.toString() + "/" + files[i]);
                FileInputStream fis = new FileInputStream(imageFile);
                if (storage
                        .getReference()
                        .child(DatabaseConstants.PHOTOS)
                        .child(files[i])
                        .putStream(fis).isSuccessful()) {
                    continue;
                }

            }

        } catch (FileNotFoundException ex) {

        }
    }

    private void syncTables() {
        db.getReference().child(DatabaseConstants.ALBUMS)
                .child(auth.getCurrentUser().getUid())
                .setValue(null);

        HashMap<String, Album> albums = CRUDAlbum.getAllAlbums();
        for (String keyAlbum : albums.keySet()) {
            Album nAlbum = albums.get(keyAlbum);
            nAlbum.setUserID(auth.getCurrentUser().getUid());

            db.getReference().child(DatabaseConstants.ALBUMS)
                    .child(auth.getCurrentUser().getUid())
                    .child(nAlbum.getId())
                    .setValue(nAlbum);

            db.getReference().child(DatabaseConstants.PHOTOS)
                    .child(nAlbum.getId())
                    .setValue(null);

            HashMap<String, Photo> photos =
                    CRUDPhoto.getAllPhotosOfAlbum(nAlbum);
            for (String photoKey : photos.keySet()) {
                Photo nPhoto = photos.get(photoKey);
                db.getReference()
                        .child(DatabaseConstants.PHOTOS)
                        .child(nAlbum.getId())
                        .child(nPhoto.getId())
                        .setValue(nPhoto);
            }

        }
    }

    private void syncEmbedding() {
        HashMap<String, Album> completeInfo =
                CRUDAlbum.getCompleteAlbums();
        db.getReference().child(DatabaseConstants.EMBEDDING)
                .child(auth.getCurrentUser().getUid())
                .setValue(completeInfo);
    }

    public void signOut() {
        auth.signOut();
        observer.onUserSignOut();
    }

    public void synchronizeData() {
        syncEmbedding();
        syncTables();
        uploadPhotos();
    }

    public void setObserver(MainActivityCallbacks observer) {
        this.observer = observer;
    }
}
