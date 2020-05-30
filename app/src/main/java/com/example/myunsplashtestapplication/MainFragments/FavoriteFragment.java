package com.example.myunsplashtestapplication.MainFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myunsplashtestapplication.Adapters.PhotoAdapter;
import com.example.myunsplashtestapplication.Models.Photo;
import com.example.myunsplashtestapplication.R;
import com.example.myunsplashtestapplication.Utils.RealmController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FavoriteFragment extends Fragment {

    @BindView(R.id.fragment_favorite_notification)
    TextView notification;
    @BindView(R.id.fragmet_favorite_recyclerview)
    RecyclerView recyclerview;

    private PhotoAdapter photoAdapter;
    private List<Photo> photos = new ArrayList<>();

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        unbinder = ButterKnife.bind(this, view);

        //RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(linearLayoutManager);
        photoAdapter = new PhotoAdapter(getActivity(), photos);
        recyclerview.setAdapter(photoAdapter);

        getPhotos();
        return view;
    }

    private void getPhotos (){
        RealmController realmController = new RealmController();
        photos.addAll(realmController.getPhotos());

        if (photos.isEmpty()){
            notification.setVisibility(View.VISIBLE);
            recyclerview.setVisibility(View.GONE);
        } else {
            photoAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

