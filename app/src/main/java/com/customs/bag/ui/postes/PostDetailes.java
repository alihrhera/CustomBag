package com.customs.bag.ui.postes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.customs.bag.util.DataManger;
import com.customs.bag.R;
import com.customs.bag.data.model.Post;
import com.squareup.picasso.Picasso;

import java.util.Date;


public class PostDetailes extends Fragment {


    public PostDetailes() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.frag_post_detalis, container, false);

        ImageView postImage=root.findViewById(R.id.postImage);
        TextView  postTitle=root.findViewById(R.id.postTitle);
        TextView  postData=root.findViewById(R.id.postDate);
        TextView  postContent=root.findViewById(R.id.postContent);
        Post post= DataManger.getInstance().getPostToMove();
        postData.setText(new Date(post.getTime()).toString());
        postTitle.setText(post.getTitle());
        postContent.setText(post.getContent());
        Picasso.get().load(post.getOnlineUrl()).fit().centerCrop().into(postImage);

        return root;
    }









}