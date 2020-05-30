package com.example.myunsplashtestapplication.Adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myunsplashtestapplication.FullScreenPhotoActivity;
import com.example.myunsplashtestapplication.Models.Photo;
import com.example.myunsplashtestapplication.R;
import com.example.myunsplashtestapplication.Utils.GlideApp;
import com.example.myunsplashtestapplication.Utils.SquareImage;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CollectionsAdapter extends RecyclerView.Adapter<CollectionsAdapter.PhotoAdapterHolder> {

    private Context context;
    private List<Photo> photos;

    public CollectionsAdapter(Context context, List<Photo> photos) {
        this.context = context;
        this.photos = photos;
    }

    @NonNull
    @Override
    public PhotoAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_photo_holder, parent, false);
        return new PhotoAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoAdapterHolder holder, int position) {
        Photo photo = photos.get(position);
        holder.item_photo_username.setText(photo.getUser().getUsername());
        GlideApp
                .with(context)
                .load(photo.getUrl().getRegular())
                .placeholder(R.drawable.placeholder)
                .override(600, 600)
                .into(holder.item_photo_photo);
        GlideApp
                .with(context)
                .load(photo.getUser().getProfileImage().getSmall())
                .into(holder.item_photo_user_avatar);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class PhotoAdapterHolder extends RecyclerView.ViewHolder {
        CircleImageView item_photo_user_avatar;
        TextView item_photo_username;
        SquareImage item_photo_photo;

        public PhotoAdapterHolder(@NonNull View itemView) {
            super(itemView);
            item_photo_user_avatar = itemView.findViewById(R.id.item_photo_user_avatar);
            item_photo_username = itemView.findViewById(R.id.item_photo_username);
            item_photo_photo = itemView.findViewById(R.id.item_photo_photo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, FullScreenPhotoActivity.class);
                    int position = getAdapterPosition();
                    String photoId = photos.get(position).getId();
                    intent.putExtra("photoId", photoId);
                    context.startActivity(intent);
                }
            });
        }
    }
}