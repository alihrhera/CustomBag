package com.customs.bag.ui.chat;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.customs.bag.R;
import com.customs.bag.util.DataManger;
import com.customs.bag.data.model.ChatMessages;
import com.customs.bag.ui.MainActivity;
import com.customs.bag.ui.adapters.ChatAdapter;
import com.google.android.gms.ads.AdRequest;

import java.util.Calendar;

public class ChatFragment extends Fragment {
    public ChatFragment() {
        // Required empty public constructor
    }

    private EditText getMsg;
    private String myId;
    private String name;
    private ChatAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ChatViewModel model = ((MainActivity) getActivity()).getChatViewModel();
        DataManger.getInstance().setChatOpen(true);
        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        RecyclerView allChat = root.findViewById(R.id.chat);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        allChat.setLayoutManager(layoutManager);
        Context context = getContext();
        AdRequest adRequest = new AdRequest.Builder().build();
        adapter = new ChatAdapter(adRequest);
        allChat.setAdapter(adapter);
        assert context != null;
        myId = DataManger.getInstance().getPhone();
        name = DataManger.getInstance().getName();
        ImageButton send = root.findViewById(R.id.sendMsg);
        getMsg = root.findViewById(R.id.getMsg);
        model.getChatLiveData().observe(this, chatMessages -> {
            adapter.setDataList(chatMessages, myId);
            if (chatMessages.size() > 0) {
                allChat.scrollToPosition(chatMessages.size() - 1);
            }
        });
        send.setOnClickListener(v -> {
            if (getMsg.getText().length() > 0) {
                if (!getMsg.getText().toString().equals(" ")) {
                    long time = Calendar.getInstance().getTimeInMillis();
                    ChatMessages ms = new ChatMessages();
                    ms.setSenderId(myId);
                    ms.setMessages(getMsg.getText().toString());
                    ms.setTime(time);
                    ms.setName(name);
                    ms.setId(time + "");
                    getMsg.setText("");
                    model.sendMessage(ms);
                }
            }
        });

//        allChat.addOnScrollListener(new RecyclerPages(layoutManager) {
//            @Override
//            public void onLoadMore(int current_page) {
//                Toast.makeText(getContext(), current_page+"", Toast.LENGTH_SHORT).show();
//
//            }
//        });


        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        DataManger.getInstance().setChatOpen(false);
    }
}