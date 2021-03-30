package com.customs.bag.ui.adapters;


import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.customs.bag.R;
import com.customs.bag.data.model.ChatMessages;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {


    private List<ChatMessages> dataList = new ArrayList<>();
    private String id;
    private AdRequest adRequest;

    public ChatAdapter(AdRequest adRequest) {
        this.adRequest = adRequest;
    }

    public void setDataList(List<ChatMessages> list, String id) {
        dataList = list;

        this.id = id;
        Log.e("TestChatListSize", list.size() + "");
        Collections.sort(dataList, (o1, o2) -> o1.getTimeToSort().compareTo(o2.getTimeToSort()));

        // if (DataManger.getInstance().getUserMode() == DataManger.Normal) {
        //     for (int i = 0; i < list.size(); i++) {
        //         if (i % 3 == 0 && i > 1) {
        //             ChatMessages messages = new ChatMessages();
        //             messages.setSenderId("000");
        //             messages.setId("000");
        //             messages.setName("000");
        //             messages.setMessages("ca-app-pub-5608130680601924/5479900739");
        //             messages.setTime(Calendar.getInstance().getTimeInMillis());
        //             if (!dataList.get(i-1).getId().equals("000")){
        //                 dataList.add(i, messages);
        //
        //             }
        //         }
        //     }
        // }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.msg_sender_layout, null);
        if (viewType == other) {
            view = View.inflate(parent.getContext(), R.layout.msg_reciver_layout, null);
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChatMessages messages = dataList.get(position);
        holder.msg.setText(messages.getMessageText());
        Date date = new Date(messages.getTime());
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String stringTime = android.text.format.DateFormat.format("yyyy-MM-dd   hh:mm a", date).toString();
        holder.time.setText(stringTime);
        holder.adView.setVisibility(View.GONE);
        holder.mView.setVisibility(View.VISIBLE);
        if (messages.getSenderId().equals("000")) {
            holder.adView.setVisibility(View.VISIBLE);
            holder.mView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    final int me = 0;
    final int other = 1;

    @Override
    public int getItemViewType(int position) {
        int type = other;
        if (dataList.get(position).getSenderId().equals(id)) {
            type = me;
        }
        return type;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView msg;//, textNum;
        TextView time;//, textNum;
        AdView adView;
        View mView;

        MyViewHolder(@NonNull View view) {
            super(view);
            msg = view.findViewById(R.id.masge);
            time = view.findViewById(R.id.time);
            adView = view.findViewById(R.id.chatAds);
            adView.loadAd(adRequest);
            mView = view.findViewById(R.id.container);
        }

    }


}
