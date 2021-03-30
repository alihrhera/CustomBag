package com.customs.bag.data.online;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.customs.bag.data.model.Post;
import com.customs.bag.util.DataManger;
import com.customs.bag.util.call_back.OnListLoad;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ApiCall {
    private final static ApiCall api = new ApiCall();

    private ApiCall() {
    }

    public static synchronized ApiCall getInstance() {
        return api;
    }


    public void getAllPosts(OnListLoad onListLoad) {

        AndroidNetworking.get(DataManger.Url + "getAllPoste.php")
                .setTag("getAllPosts ")
                .setPriority(Priority.HIGH)
                .build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    List<Post> posts = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        Post post = new Post();
                        post.setId(object.getInt("id"));
                        post.setTitle(object.getString("title"));
                        post.setContent(object.getString("content"));
                        post.setTime(object.getLong("time"));
                        post.setImage(object.getString("image"));
                        posts.add(post);
                    }
                    onListLoad.onLoad(posts);
                } catch (Exception e) {
                    Log.e("get All Post", "Error - >" + e.getMessage());
                    onListLoad.onLoad(null);

                }

            }

            @Override
            public void onError(ANError anError) {
                onListLoad.onLoad(null);
                Log.e("get All Post", "Error - >" + anError.getMessage());


            }
        });


    }


}
