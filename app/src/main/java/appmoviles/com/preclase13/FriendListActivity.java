package appmoviles.com.preclase13;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.FirebaseDatabase;

public class FriendListActivity extends AppCompatActivity {

    private ListView friendList;
    private ListView albumList;
    private ListView photoList;

    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        db = FirebaseDatabase.getInstance();

        friendList = findViewById(R.id.friendList);
        albumList = findViewById(R.id.albumList);
        photoList = findViewById(R.id.photoList);
    }
}
