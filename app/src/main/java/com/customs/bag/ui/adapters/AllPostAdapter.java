package com.customs.bag.ui.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.customs.bag.R;
import com.customs.bag.util.call_back.OnItemClick;
import com.customs.bag.data.model.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AllPostAdapter extends RecyclerView.Adapter<AllPostAdapter.ViewHolder> {
    private List<Post> dataList = new ArrayList<>();
    public void setDataList(List<Post> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }
    OnItemClick click;

    public void setClick(OnItemClick click) {
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.row_post, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = dataList.get(position);
        holder.title.setText(post.getTitle());
        holder.date.setText(new Date(post.getTime()).toString());
        try {
            Picasso.get().load(post.getOnlineUrl()).fit().centerCrop().into(holder.photo);
            Log.e("imgae",post.getOnlineUrl());

        }catch (Exception e){}
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (click!=null){
                    click.onClick(post);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView title,date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photo=itemView.findViewById(R.id.postImage);
            title=itemView.findViewById(R.id.postTitle);
            date=itemView.findViewById(R.id.postDate);

        }
    }
}
