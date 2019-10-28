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

import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

import appmoviles.com.preclase13.R;

public class CommentsFragment extends DialogFragment {

    private EditText commentEt;
    private Button commentBtn;
    private ListView commentList;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> commentArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comments, null);
        commentEt = view.findViewById(R.id.comment_et);
        commentBtn = view.findViewById(R.id.comment_btn);
        commentList = view.findViewById(R.id.comment_list);
        commentArray = new ArrayList<>();

        commentArray.add("Admin\nEste es un comentario");
        commentArray.add("Admin\nEste es otro comentario");
        commentArray.add("Admin\nProbando la lista");
        commentArray.add("Admin\nEste es un comentario");
        commentArray.add("Admin\nEste es otro comentario");
        commentArray.add("Admin\nProbando la lista");
        commentArray.add("Admin\nEste es un comentario");
        commentArray.add("Admin\nEste es otro comentario");
        commentArray.add("Admin\nProbando la lista");
        commentArray.add("Admin\nEste es un comentario");
        commentArray.add("Admin\nEste es otro comentario");
        commentArray.add("Admin\nProbando la lista");
        commentArray.add("Admin\nEste es un comentario");
        commentArray.add("Admin\nEste es otro comentario");
        commentArray.add("Admin\nProbando la lista");
        commentArray.add("Admin\nEste es un comentario");
        commentArray.add("Admin\nEste es otro comentario");
        commentArray.add("Admin\nProbando la lista");

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, commentArray);
        commentList.setAdapter(adapter);

        commentBtn.setOnClickListener((v)->{
            hideSoftKeyboard(v);
        });

        return view;
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getActivity().getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
