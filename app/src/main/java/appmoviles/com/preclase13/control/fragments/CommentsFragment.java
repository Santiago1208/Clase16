package appmoviles.com.preclase13.control.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

import appmoviles.com.preclase13.R;
import appmoviles.com.preclase13.model.entity.Photo;

public class CommentsFragment extends DialogFragment {

    private EditText commentEt;
    private Button commentBtn;
    private ListView commentList;
    private ArrayAdapter<String> commentsAdapter;
    private ArrayList<String> arrayComments;
    private Photo photo;
    FirebaseDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comments, null);
        commentEt = view.findViewById(R.id.comment_et);
        commentBtn = view.findViewById(R.id.comment_btn);
        commentList = view.findViewById(R.id.comment_list);
        db = FirebaseDatabase.getInstance();

        arrayComments = new ArrayList<>();
        commentsAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1,arrayComments);
        commentList.setAdapter(commentsAdapter);



        commentBtn.setOnClickListener((v)->{
            String comentario = commentEt.getText().toString();
            db.getReference()
                    .child("comments")
                    .child(photo.getId())
                    .push()
                    .setValue(comentario);

            hideSoftKeyboard(v);
        });

        db.getReference().child("comments").child(photo.getId())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        arrayComments.clear();
                        for(DataSnapshot child : dataSnapshot.getChildren()){
                            String comment = child.getValue(String.class);
                            arrayComments.add(comment);
                        }
                        commentsAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        //addChildEventListener()

        return view;
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getActivity().getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
}
