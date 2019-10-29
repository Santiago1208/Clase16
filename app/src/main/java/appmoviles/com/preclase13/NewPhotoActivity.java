package appmoviles.com.preclase13;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.util.UUID;

import appmoviles.com.preclase13.model.data.CRUDPhoto;
import appmoviles.com.preclase13.model.entity.Album;
import appmoviles.com.preclase13.model.entity.Photo;
import appmoviles.com.preclase13.util.UtilDomi;


public class NewPhotoActivity extends AppCompatActivity {


    private Album album;
    private TextView photoIdTv;
    private EditText photoNameEt;
    private EditText photoDescNameEt;
    private TextView photoFkTv;
    private Button takePhotoBTN;
    private Button openGalBTN;
    private ImageView pictureTaked;
    private Button photoCreateBTN;

    //Se generan desde el principio
    private String id;
    //1...
    private File photoFile;
    private static final int CAMERA_CALLBACK_ID = 11;
    //1...

    //2...
    private static final int GALLERY_CALLBACK_ID = 12;
    //2...


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_photo);
        album = (Album) getIntent().getExtras().getSerializable("album");

        pictureTaked = findViewById(R.id.pictureTaked);
        photoCreateBTN = findViewById(R.id.photoCreateBTN);
        takePhotoBTN = findViewById(R.id.takePhotoBTN);
        openGalBTN = findViewById(R.id.openGalBTN);

        photoIdTv = findViewById(R.id.photoIdTv);
        photoNameEt = findViewById(R.id.photoNameEt);
        photoDescNameEt = findViewById(R.id.photoDescNameEt);
        photoFkTv = findViewById(R.id.photoFkTv);

        id = UUID.randomUUID().toString();
        photoIdTv.setText("ID: "+id);
        photoFkTv.setText("The photo will be added to the album "+ album.getName());

        photoCreateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Photo task = new Photo(id, photoNameEt.getText().toString(), photoDescNameEt.getText().toString(), 0, album.getId());
                CRUDPhoto.insertPhoto(album, task);

                finish();
            }
        });

        //1...
        takePhotoBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                photoFile = new File(getExternalFilesDir(null) + "/" + id + ".png");
                Uri uri = FileProvider.getUriForFile(NewPhotoActivity.this, getPackageName(), photoFile);
                i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(i, CAMERA_CALLBACK_ID);
            }
        });
        //1...

        //2...
        openGalBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i, GALLERY_CALLBACK_ID);
            }
        });
        //2...



    }

    //3...
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //4...
        if (requestCode == CAMERA_CALLBACK_ID && resultCode == RESULT_OK) {
            Bitmap imagen = BitmapFactory.decodeFile(photoFile.toString());
            pictureTaked.setImageBitmap(imagen);
        }
        //4...

        //4...
        if (requestCode == GALLERY_CALLBACK_ID && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            photoFile = new File(UtilDomi.getPath(this, uri));
            //Bitmap imagen = BitmapFactory.decodeFile(photoFile.toString());
            //pictureTaked.setImageBitmap(imagen);

            //5...
            File dest = new File(getExternalFilesDir(null) + "/" + id + ".png");
            UtilDomi.copyFileUsingStream(photoFile, dest);
            Bitmap imagen = BitmapFactory.decodeFile(dest.toString());
            pictureTaked.setImageBitmap(imagen);
            //5...
        }
        //4...
    }
    //3...
}
