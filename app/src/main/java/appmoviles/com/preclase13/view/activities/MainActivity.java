package appmoviles.com.preclase13.view.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import appmoviles.com.preclase13.R;
import appmoviles.com.preclase13.control.viewcontrollers.mainactivity.MainActivityController;

public class MainActivity extends AppCompatActivity {

    private ListView LVAlbum;
    private Button addAlbumBtn;
    private Button friendsBtn;
    private Button signOutBtn;
    private Button syncBtn;
    private RelativeLayout controlPanel;
    private MainActivityController controller;

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
