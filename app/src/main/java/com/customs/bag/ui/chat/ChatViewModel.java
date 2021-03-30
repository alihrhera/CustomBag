package com.customs.bag.ui.chat;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.customs.bag.data.model.ChatMessages;
import com.customs.bag.data.online.ApiCall;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatViewModel extends AndroidViewModel {
    private final MutableLiveData<List<ChatMessages>> chatLiveData = new MutableLiveData<>();
    private final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("CustomBag/Chat");

    public ChatViewModel(@NonNull Application application) {
        super(application);

        myRef.limitToLast(50).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<ChatMessages> list = new ArrayList<>();
                if (snapshot.exists()) {
                    for (DataSnapshot s : snapshot.getChildren()) {
                        ChatMessages chatMessage = s.getValue(ChatMessages.class);
                        if (!list.contains(chatMessage)) {
                            list.add(chatMessage);
                        }
                    }
                }
                chatLiveData.postValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public LiveData<List<ChatMessages>> getChatLiveData() {
        return chatLiveData;
    }

    public void sendMessage(ChatMessages message) {
        myRef.child(message.getId()).setValue(message).addOnSuccessListener(aVoid -> {
            ApiCall.getInstance().notifyUsers(message.getMessageText());
        });

    }

}
