package com.customs.bag.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.customs.bag.R;
import com.customs.bag.ui.chat.ChatViewModel;
import com.customs.bag.ui.postes.PostsViewModel;
import com.customs.bag.ui.tarif.SearchViewModel;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    public FragmentManager frManager;
    private ChatViewModel chatViewModel;

    public ChatViewModel getChatViewModel() {
        return chatViewModel;
    }

    private PostsViewModel postsViewModel;
    private SearchViewModel searchViewModel;

    public PostsViewModel getPostsViewModel() {
        return postsViewModel;
    }

    public SearchViewModel getSearchViewModel() {
        return searchViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chatViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(ChatViewModel.class);

        postsViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(PostsViewModel.class);

        searchViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(SearchViewModel.class);

        frManager = getSupportFragmentManager();
        frManager.beginTransaction().replace(R.id.fragContainer, new SplashFragment()).commit();
        MobileAds.initialize(this, initializationStatus -> {
        });


    }

    public void attachFragment(Fragment fragment) {
        if (!(fragment instanceof SplashFragment)) {
            FragmentTransaction transaction = frManager.beginTransaction();
            transaction.addToBackStack(null).replace(R.id.fragContainer, fragment).commit();
        }
    }

    @Override
    public void onBackPressed() {
        frManager.popBackStack();
    }
}
