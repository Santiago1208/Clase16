package appmoviles.com.preclase13;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

import appmoviles.com.preclase13.control.adapters.PhotoAdapter;
import appmoviles.com.preclase13.model.data.CRUDPhoto;
import appmoviles.com.preclase13.model.entity.Album;
import appmoviles.com.preclase13.model.entity.Photo;


public class PhotoListActivity extends AppCompatActivity {

    private TextView titlePhotolist;
    private Album album;
    private Button addPhotoBtn;
    private ListView photoLV;
    private PhotoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);

        titlePhotolist = findViewById(R.id.title_album);
        addPhotoBtn = findViewById(R.id.addPhotoBtn);
        photoLV = findViewById(R.id.photoLV);

        adapter = new PhotoAdapter();
        photoLV.setAdapter(adapter);


        album = (Album) getIntent().getExtras().getSerializable("album");
        titlePhotolist.setText(album.getName());


        addPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PhotoListActivity.this, NewPhotoActivity.class);
                i.putExtra("album", album);
                startActivity(i);
            }
        });

        photoLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PhotoListActivity.this)
                        .setTitle("Eliminar")
                        .setMessage("¿Desea eliminar la foto?")
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                CRUDPhoto.detelePhoto((Photo) adapter.getItem(pos));
                                refreshTasks();
                                dialogInterface.dismiss();
                            }
                        });

                builder.show();
                return true;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshTasks();
    }

    private void refreshTasks() {
        HashMap<String, Photo> group = CRUDPhoto.getAllPhotosOfAlbum(album);
        adapter.clear();
        for(String key : group.keySet()){
            adapter.addPhoto(group.get(key));
        }
    }

}
