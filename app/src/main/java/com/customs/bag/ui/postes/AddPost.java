package com.customs.bag.ui.postes;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.customs.bag.R;
import com.customs.bag.util.DataManger;
import com.customs.bag.data.model.Post;
import com.customs.bag.ui.MainActivity;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.Calendar;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;


public class AddPost extends Fragment {


    public AddPost() {
        // Required empty public constructor
    }

    private Context context;
    private ImageView postImage;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_add_post_detalis, container, false);
        context = getContext();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        postImage = root.findViewById(R.id.postImage);
        EditText postTitle = root.findViewById(R.id.postTitle);
        EditText postContent = root.findViewById(R.id.postContent);
        Button save = root.findViewById(R.id.savePost);
        Post post = new Post();
        post.setContent(postContent.getText().toString());
        post.setTitle(postTitle.getText().toString());
        post.setTime(Calendar.getInstance().getTimeInMillis());
        PostsViewModel model = ((MainActivity) getActivity()).getPostsViewModel();
        model.getUploadLiveData().observe(this, uploadValue -> {
            if (uploadValue < 100) {
                progressDialog.setProgress(uploadValue);
            } else if (uploadValue == 200) {
                ((MainActivity) getActivity()).attachFragment(new AllPosts());
                progressDialog.dismiss();
            } else if (uploadValue == 400) {
                Toast.makeText(context, getString(R.string.error_with_posting), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });

        postImage.setOnClickListener(view -> {

            openImagePiker();


        });

        save.setOnClickListener(view -> {
            if (postTitle.getText().length() < 5) {
                return;
            }
            if (postContent.getText().length() < 10) {
                return;
            }
            post.setContent(postContent.getText().toString());
            post.setTitle(postTitle.getText().toString());
            post.setTime(Calendar.getInstance().getTimeInMillis());
            if (uri != null && selectPhoto) {
                post.setImage(uri.getPath());
                uploadFileToServer(post);
            }
        });
        return root;
    }


    private void uploadFileToServer(Post post) {
        progressDialog.show();
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
                    progressDialog.setProgress((int) val);
                }).getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                Log.e("Test", response.toString());
                if (response.equals("done")) {
                    ((MainActivity) getActivity()).attachFragment(new AllPosts());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onError(ANError anError) {
                Log.e("Test", anError.toString());
                progressDialog.dismiss();
            }
        });
    }


    private boolean checkFilePre() {
        return (ContextCompat.checkSelfPermission(
                context, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED);
    }

    private void openImagePiker() {
        if (checkFilePre()) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Photo"),
                    PICK_USER_IMAGE_CODE);

            return;
        }
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, FILE_PERMISSION_REQUEST_CODE);
    }

    private final int FILE_PERMISSION_REQUEST_CODE = 1000;
    private final int PICK_USER_IMAGE_CODE = 1099;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == FILE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePiker();

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private Uri uri;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (data == null) {
                return;
            } else if (requestCode == PICK_USER_IMAGE_CODE) {
                Uri imageUri = data.getData();
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setCropShape(CropImageView.CropShape.RECTANGLE)
                        .setMaxCropResultSize(2500, 2500)
                        .start(context, this);
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                uri = result.getUri();
                File profileImage = new File(Objects.requireNonNull(uri.getPath()));
                postImage.setPadding(0, 0, 0, 0);
                Picasso.get().load(profileImage).centerCrop().fit()
                        .into(postImage);
                selectPhoto = true;
            }

        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    private boolean selectPhoto;


}