package appmoviles.com.preclase13.control.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.util.ArrayList;

import appmoviles.com.preclase13.R;
import appmoviles.com.preclase13.control.fragments.CommentsFragment;
import appmoviles.com.preclase13.model.entity.Photo;


public class PhotoAdapter extends BaseAdapter {

    ArrayList<Photo> photos;


    public PhotoAdapter() {
        photos = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object getItem(int i) {
        return photos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        AppCompatActivity ref = (AppCompatActivity) viewGroup.getContext();

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View rowView = inflater.inflate(R.layout.photo_row, null);
        ImageView rowImage = rowView.findViewById(R.id.row_image);
        TextView rowName = rowView.findViewById(R.id.row_name);
        TextView rowViews = rowView.findViewById(R.id.row_views);
        TextView rowDesc = rowView.findViewById(R.id.row_description);
        Button commentsPhotoBtn = rowView.findViewById(R.id.commentsPhotoBtn);

        rowName.setText(photos.get(i).getName());
        rowViews.setText("Views: " + photos.get(i).getViews());
        rowDesc.setText(photos.get(i).getDescription());


        File file = new File(viewGroup.getContext().getExternalFilesDir(null) + "/" + photos.get(i).getId() + ".png");
        if (file.exists()) {
            Bitmap imagen = BitmapFactory.decodeFile(file.toString());
            rowImage.setImageBitmap(imagen);
        }else{
            loadImage(i, rowImage);
        }


        commentsPhotoBtn.setOnClickListener((v) -> {
            CommentsFragment fragment = new CommentsFragment();
            fragment.setPhoto(photos.get(i));
            fragment.show(ref.getSupportFragmentManager(), "comments");
        });

        return rowView;

    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
        notifyDataSetChanged();
    }

    public void clear() {
        photos.clear();
    }

    public void loadImage(int i, ImageView rowImage) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storage.getReference().child("photos")
                .child(photos.get(i).getId() + ".png")
                .getDownloadUrl().addOnSuccessListener(uri -> {
            Log.e(">>>", uri.toString());
            Glide.with(rowImage).load(uri.toString())
                    .into(rowImage);
        });
    }

}
