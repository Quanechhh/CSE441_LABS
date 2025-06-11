package com.example.lab_14_02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SongAdapter extends ArrayAdapter<Song> {

    private Context mContext;
    private int mResource;
    private List<Song> mSongs;

    public SongAdapter(@NonNull Context context, int resource, @NonNull List<Song> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.mSongs = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the song object for this position
        Song song = getItem(position);

        // ViewHolder pattern for efficient list view scrolling
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.txtMaSo = convertView.findViewById(R.id.txtmaso);
            holder.txtTieuDe = convertView.findViewById(R.id.txtTieuDe);
            holder.btnLike = convertView.findViewById(R.id.btnLike);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (song != null) {
            holder.txtMaSo.setText(song.getMaSo());
            holder.txtTieuDe.setText(song.getTieuDe());

            // Set heart icon based on liked status
            if (song.isLiked()) {
                holder.btnLike.setImageResource(R.drawable.heart_filled);
            } else {
                holder.btnLike.setImageResource(R.drawable.heart_outline);
            }

            // Set OnClickListener for the like button
            holder.btnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    song.setLiked(!song.isLiked()); // Toggle liked status
                    notifyDataSetChanged(); // Notify adapter to re-render the view
                    String message = song.getTieuDe() + (song.isLiked() ? " đã được thích!" : " đã bỏ thích.");
                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

                    // Optional: You might want to update a 'liked songs' list here
                    // e.g., if this adapter is for all songs, you'd update a separate
                    // likedSongsList and notify its adapter.
                }
            });
        }

        return convertView;
    }

    // ViewHolder inner class to hold references to views
    static class ViewHolder {
        TextView txtMaSo;
        TextView txtTieuDe;
        ImageButton btnLike;
    }

    // Method to filter the list based on search query for Tab 1
    public void filter(String query) {
        mSongs.clear();
        notifyDataSetChanged();
    }
}
