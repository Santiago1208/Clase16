package appmoviles.com.preclase13;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import appmoviles.com.preclase13.control.viewcontrollers.MainActivityController;
import appmoviles.com.preclase13.model.data.CRUDAlbum;
import appmoviles.com.preclase13.model.data.CRUDPhoto;
import appmoviles.com.preclase13.model.entity.Album;
import appmoviles.com.preclase13.model.entity.Photo;
import appmoviles.com.preclase13.model.entity.User;
import appmoviles.com.preclase13.util.HTTPSWebUtilDomi;

public class MainActivity extends AppCompatActivity {

    private ListView LVAlbum;
    private Button addAlbumBtn;
    private Button friendsBtn;
    private Button signOutBtn;
    private Button syncBtn;
    private RelativeLayout controlPanel;

    MainActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LVAlbum = findViewById(R.id.LVAlbum);
        addAlbumBtn = findViewById(R.id.addAlbumBtn);
        friendsBtn = findViewById(R.id.friendsBtn);
        controlPanel = findViewById(R.id.controlPanel);
        signOutBtn = findViewById(R.id.signOutBtn);
        syncBtn = findViewById(R.id.syncBtn);

        controller = new MainActivityController(this);
        controller.init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        controller.refreshTaskList();
    }

    public ListView getLVAlbum() {
        return LVAlbum;
    }

    public Button getAddAlbumBtn() {
        return addAlbumBtn;
    }

    public Button getFriendsBtn() {
        return friendsBtn;
    }

    public Button getSignOutBtn() {
        return signOutBtn;
    }

    public Button getSyncBtn() {
        return syncBtn;
    }

    public RelativeLayout getControlPanel() {
        return controlPanel;
    }
}
