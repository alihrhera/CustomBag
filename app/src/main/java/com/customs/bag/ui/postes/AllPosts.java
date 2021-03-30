package com.customs.bag.ui.postes;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.customs.bag.R;
import com.customs.bag.util.DataManger;
import com.customs.bag.data.model.Post;
import com.customs.bag.ui.MainActivity;
import com.customs.bag.ui.adapters.AllPostAdapter;

import java.util.List;
import java.util.Objects;


public class AllPosts extends Fragment {


    public AllPosts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_all_post, container, false);
        root.findViewById(R.id.addNewPost).setOnClickListener(view -> {
            ((MainActivity) Objects.requireNonNull(getActivity())).attachFragment(new AddPost());

        });
        AllPostAdapter adapter = new AllPostAdapter();
        ProgressDialog progressDialog;
        RecyclerView SHOW_ALL = root.findViewById(R.id.SHOW_ALL);
        SHOW_ALL.setLayoutManager(new LinearLayoutManager(getContext()));
        SHOW_ALL.setAdapter(adapter);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.show();
        PostsViewModel model = ((MainActivity) getActivity()).getPostsViewModel();
        model.getPostLiveData().observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                adapter.setDataList(posts);
                progressDialog.dismiss();
            }
        });

        adapter.setClick(obj -> {
            Post post = (Post) obj;
            DataManger.getInstance().setPostToMove(post);
            ((MainActivity) getActivity()).attachFragment(new PostDetailes());
        });

        return root;
    }


}