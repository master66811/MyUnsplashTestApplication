package com.example.myunsplashtestapplication.MainFragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myunsplashtestapplication.Adapters.CollectionsAdapter;
import com.example.myunsplashtestapplication.Models.Photo;
import com.example.myunsplashtestapplication.R;
import com.example.myunsplashtestapplication.Webservices.ApiInterface;
import com.example.myunsplashtestapplication.Webservices.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoFragmentAlbom extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

   // private final String TAG = PhotoFragmentAlbom.class.getSimpleName();
    @BindView(R.id.progressBarFragmentPhotos)
    ProgressBar progressBarFragmentPhotos;
    @BindView(R.id.recyclerViewFragmentPhotos)
    RecyclerView recyclerViewFragmentPhotos;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private CollectionsAdapter collectionsAdapter;
    private List<Photo> photos = new ArrayList<>();
    private Unbinder unbinder;
    private int refreshLayoutPage = 1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_albom, container, false);
        unbinder = ButterKnife.bind(this, view);
        swipeRefreshLayout.setOnRefreshListener(this);
        //RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewFragmentPhotos.setLayoutManager(linearLayoutManager);

        getPhotos();
        showProgressBar(true);
        return view;
    }

    private void getPhotos() {
        //   ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        ApiInterface apiInterface = ServiceGenerator.getApiClient().create(ApiInterface.class);

        Call<List<Photo>> call = apiInterface.getPhotos(refreshLayoutPage);
        call.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(getContext(), "Ошибка" + response.message(), Toast.LENGTH_SHORT).show();
                 //   Log.e(TAG, "fail" + response.message());
                    return;
                }
                photos.addAll(response.body());
                collectionsAdapter = new CollectionsAdapter(getContext(), photos);
                recyclerViewFragmentPhotos.setAdapter(collectionsAdapter);
                collectionsAdapter.notifyDataSetChanged();
                showProgressBar(false);
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                Toast.makeText(getContext(), "Ошибка" + t.getMessage(), Toast.LENGTH_SHORT).show();
               // Log.e(TAG, "fail" + t.getMessage());
                showProgressBar(false);
            }
        });
    }

    private void showProgressBar(boolean isShow) {
        if (isShow) {
            progressBarFragmentPhotos.setVisibility(View.VISIBLE);
            recyclerViewFragmentPhotos.setVisibility(View.GONE);
        } else {
            progressBarFragmentPhotos.setVisibility(View.GONE);
            recyclerViewFragmentPhotos.setVisibility(View.VISIBLE);
        }
    }
    /**
     * Реализация интерфейса SwipeRefreshLayout
     */
    @Override
    public void onRefresh() {
        if(refreshLayoutPage < 6){
            refreshLayoutPage++;
            photos.clear();
            getPhotos();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }, 500);
         } else {swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getContext(), "Загружено 50 фотографий", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
