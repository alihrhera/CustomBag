package com.customs.bag.ui.postes;

import android.app.Application;
import android.util.Log;
import com.customs.bag.util.DataManger;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.customs.bag.data.online.ApiCall;
import com.customs.bag.data.model.Post;

import java.io.File;
import java.util.List;

public class PostsViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Post>> postLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> uploadLiveData = new MutableLiveData<>();

    public PostsViewModel(@NonNull Application application) {
        super(application);
        // call get all Posts Here

        ApiCall.getInstance().getAllPosts(objects -> {
            if (objects != null) {
                List<Post> postList = (List<Post>) objects;
                postLiveData.postValue(postList);
            }
        });


    }

    public LiveData<List<Post>> getPostLiveData() {
        return postLiveData;
    }

    public void addNewPost(Post message) {
        // call add new post from api Here

    }


    public LiveData<Integer> getUploadLiveData() {
        return uploadLiveData;
    }

    private void uploadPostWithPhoto(Post post) {
        File image = new File(post.getImage());
        AndroidNetworking.upload(DataManger.Url + "uploadePostImage.php")
                .addMultipartFile("image", image)
                .addMultipartParameter("title", post.getTitle())
                .addMultipartParameter("content", post.getContent())
                .addMultipartParameter("time", post.getTime() + "")
                .setTag("upload_Post")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener((bytesUploaded, totalBytes) -> {
                    float val = (float) bytesUploaded / totalBytes * 100;
                    uploadLiveData.postValue((int) val);
//                    progressDialog.setProgress((int) val);
                })
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Test", response.toString());
                        if (response.equals("done")) {
                            uploadLiveData.postValue(200);
                            return;
                        }
                        uploadLiveData.postValue(400);
                    }

                    public void onError(ANError anError) {
                        uploadLiveData.postValue(400);
                    }
                });
    }

}
