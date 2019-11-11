package appmoviles.com.preclase13.control.viewcontrollers;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.HashMap;

import appmoviles.com.preclase13.FriendListActivity;
import appmoviles.com.preclase13.LoginActivity;
import appmoviles.com.preclase13.MainActivity;
import appmoviles.com.preclase13.NewAlbumActivity;
import appmoviles.com.preclase13.PhotoListActivity;
import appmoviles.com.preclase13.model.data.CRUDAlbum;
import appmoviles.com.preclase13.model.entity.Album;
import appmoviles.com.preclase13.model.entity.User;


public class MainActivityController implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnClickListener, MainActivityCallbacks {

    private MainActivity activity;
    private ArrayAdapter<Album> adapter;
    private ArrayList<Album> list;
    private MainActivityDataController dataController;

    public MainActivityController(MainActivity activity) {
        this.activity = activity;
    }

    public void init() {
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, list);
        activity.getLVAlbum().setAdapter(adapter);

        activity.getLVAlbum().setOnItemClickListener(this);
        activity.getLVAlbum().setOnItemLongClickListener(this);
        activity.getAddAlbumBtn().setOnClickListener(this);
        activity.getFriendsBtn().setOnClickListener(this);
        activity.getSignOutBtn().setOnClickListener(this);
        activity.getSyncBtn().setOnClickListener(this);

        requestPermissions();

        dataController = new MainActivityDataController();
        dataController.init();
        dataController.setObserver(this);

    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(activity, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        }, 11);
    }


    private void goToFriendListAcitivity() {
        Intent intent = new Intent(activity, FriendListActivity.class);
        activity.startActivity(intent);
    }

    private void goToNewAlbumActivity() {
        Intent i = new Intent(activity, NewAlbumActivity.class);
        activity.startActivity(i);
    }

    public void refreshTaskList() {
        HashMap<String, Album> group = CRUDAlbum.getAllAlbums();
        list.clear();
        for (String key : group.keySet()) {
            Album nAlbum = group.get(key);
            list.add(nAlbum);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
        if (adapterView.equals(activity.getLVAlbum())) {
            Intent intent = new Intent(activity, PhotoListActivity.class);
            intent.putExtra("album", list.get(pos));
            activity.startActivity(intent);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
        if (adapterView.equals(activity.getLVAlbum())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                    .setTitle("Eliminar")
                    .setMessage("¿Desea eliminar la lista de tareas?")
                    .setNegativeButton("Cancelar", (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                    })
                    .setPositiveButton("Aceptar", (dialogInterface, i) -> {
                        CRUDAlbum.deteleAlbum(list.get(pos));
                        refreshTaskList();
                        dialogInterface.dismiss();
                    });
            builder.show();
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        if (view.equals(activity.getAddAlbumBtn())) {
            goToNewAlbumActivity();
        } else if (view.equals(activity.getFriendsBtn())) {
            goToFriendListAcitivity();
        } else if (view.equals(activity.getSignOutBtn())) {
            dataController.signOut();
        } else if (view.equals(activity.getSyncBtn())) {
            dataController.synchronizeData();
        }
    }

    @Override
    public void onUserDownloaded(User user) {
        Toast.makeText(activity, "Hola " + user.getName(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAllDataDownloaded() {
        refreshTaskList();
    }

    @Override
    public void onUserNoAuth() {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    public void onUserSignOut() {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }
}
