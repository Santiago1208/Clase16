package appmoviles.com.preclase13;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

import appmoviles.com.preclase13.control.adapters.PhotoAdapter;
import appmoviles.com.preclase13.model.entity.Album;
import appmoviles.com.preclase13.model.entity.Friend;
import appmoviles.com.preclase13.model.entity.Photo;

public class FriendListActivity extends AppCompatActivity {

    private ListView friendList;
    private ArrayAdapter<Friend> friendArrayAdapter;
    private ArrayList<Friend> arrayFriends;


    private ListView albumList;
    private ArrayAdapter<Album> albumArrayAdapter;
    private ArrayList<Album> albumArrayList;

    private ListView photoList;
    private PhotoAdapter photoAdapter;
    private Button backButton;
    private TextView loadMore;

    FirebaseDatabase db;

    private Friend friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        db = FirebaseDatabase.getInstance();

        friendList = findViewById(R.id.friendList);
        albumList = findViewById(R.id.albumList);
        photoList = findViewById(R.id.photoList);
        loadMore = findViewById(R.id.load_more);

        arrayFriends = new ArrayList<>();
        friendArrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                arrayFriends
        );
        friendList.setAdapter(friendArrayAdapter);

        albumArrayList = new ArrayList<>();
        albumArrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                albumArrayList
        );
        albumList.setAdapter(albumArrayAdapter);

        photoAdapter = new PhotoAdapter();
        photoList.setAdapter(photoAdapter);
        backButton = findViewById(R.id.back_button);

        db.getReference().child("friends")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        arrayFriends.clear();
                        for (DataSnapshot friends : dataSnapshot.getChildren()) {
                            Friend f = friends.getValue(Friend.class);
                            arrayFriends.add(f);
                        }
                        friendArrayAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        friendList.setOnItemClickListener(
                (adapterView, view, i, l) -> {
                    friend = arrayFriends.get(i);

                    friendList.setVisibility(View.GONE);
                    albumList.setVisibility(View.VISIBLE);

                    db.getReference().child("albums")
                            .child(friend.getUid())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot album : dataSnapshot.getChildren()) {
                                        Album alb = album.getValue(Album.class);
                                        albumArrayList.add(alb);
                                    }
                                    albumArrayAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                }
        );

        albumList.setOnItemClickListener(
                (adapterView, view, i, l) -> {
                    Album album = albumArrayList.get(i);
                    albumList.setVisibility(View.GONE);
                    photoList.setVisibility(View.VISIBLE);
                    loadMore.setVisibility(View.VISIBLE);


                    db.getReference()
                            .child("photos")
                            .child(album.getId())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for(DataSnapshot child : dataSnapshot.getChildren()){
                                        Photo photo = child.getValue(Photo.class);
                                        photoAdapter.addPhoto(photo);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                }
        );

        backButton.setOnClickListener((v) -> {
            if (photoList.getVisibility() == View.VISIBLE) {
                photoList.setVisibility(View.GONE);
                loadMore.setVisibility(View.GONE);
                albumList.setVisibility(View.VISIBLE);
            } else if (albumList.getVisibility() == View.VISIBLE) {
                albumList.setVisibility(View.GONE);
                friendList.setVisibility(View.VISIBLE);
            } else if (friendList.getVisibility() == View.VISIBLE) {
                finish();
            }
        });


        loadMore.setOnClickListener(
                (v) -> {

                }
        );

    }

    public Friend getFriend() {
        return friend;
    }
}
