package com.example.myunsplashtestapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.myunsplashtestapplication.Models.Photo;
import com.example.myunsplashtestapplication.Utils.GlideApp;
import com.example.myunsplashtestapplication.Utils.RealmController;
import com.example.myunsplashtestapplication.Webservices.ApiInterface;
import com.example.myunsplashtestapplication.Webservices.ServiceGenerator;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullScreenPhotoActivity extends AppCompatActivity {

    @BindView(R.id.activity_fullscreen_photo_photo)
    ImageView fullscreenPhoto;
    @BindView(R.id.activity_fullscreen_photo_username)
    TextView username;
    @BindView(R.id.activity_fullscreen_photo_fab_favorite)
    FloatingActionButton fabFavorite;
    @BindView(R.id.fullScreenPhotoAvatar)
    CircleImageView userAvatar;

    @BindDrawable(R.drawable.ic_check_favotite)
    Drawable icFavorite;
    @BindDrawable(R.drawable.ic_check_favorited)
    Drawable icFavorited;

    private Bitmap photoBitmap;
    private Unbinder unbinder;
    private Photo photo;

    private RealmController realmController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_photo);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        unbinder = ButterKnife.bind(this);

        Intent intent = getIntent();
        String photoId = intent.getStringExtra("photoId");
        getPhoto(photoId);

        realmController = new RealmController();
        if (realmController.isPhotoExist(photoId)) {
            fabFavorite.setImageDrawable(icFavorited);
        }
    }

    private void getPhoto(String id) {
//       ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
       ApiInterface apiInterface = ServiceGenerator.getApiClient().create(ApiInterface.class);
        Call<Photo> call = apiInterface.getPhoto(id);
        call.enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Call<Photo> call, Response<Photo> response) {
                if (response.isSuccessful()) {
                    photo = response.body();
                    updateUI(photo);
                }
            }
            @Override
            public void onFailure(Call<Photo> call, Throwable t) {

            }
        });
    }
    public void updateUI(Photo photo) {
        try {
            username.setText(photo.getUser().getUsername());
            GlideApp.with(FullScreenPhotoActivity.this)
                    .load(photo.getUser().getProfileImage().getSmall())
                    .into(userAvatar);
            GlideApp.with(FullScreenPhotoActivity.this)
                    .asBitmap()
                    .load(photo.getUrl().getRegular())
                    .centerCrop()
                    .into(new SimpleTarget<Bitmap>() {
                              @Override
                              public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                  fullscreenPhoto.setImageBitmap(resource);
                                  photoBitmap = resource;
                              }
                          }
                    );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.activity_fullscreen_photo_fab_favorite)
    public void setFabFavorite() {
        if (realmController.isPhotoExist(photo.getId())) {
            realmController.deletePhoto(photo);
            fabFavorite.setImageDrawable(icFavorite);
            Toast.makeText(FullScreenPhotoActivity.this, "Удалено из избранных ", Toast.LENGTH_SHORT).show();
        } else {
            realmController.savePhoto(photo);
            fabFavorite.setImageDrawable(icFavorited);
            Toast.makeText(FullScreenPhotoActivity.this, "Добавлено в избранное ", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
