package appmoviles.com.preclase13;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class FriendListActivity extends AppCompatActivity {

    private ListView friendList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        friendList = findViewById(R.id.friendList);
    }
}
