package com.example.pickerballmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide; // dùng Glide để load ảnh
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {
    private List<Player> players;
    private Context context;
    private DatabaseReference ref;

    public PlayerAdapter(Context context, List<Player> players) {
        this.context = context;
        this.players = players;
        ref = FirebaseDatabase.getInstance().getReference("players");
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_player, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player p = players.get(position);
        holder.txtName.setText(p.username);
        holder.txtCode.setText("Mã: " + p.member_code);
        holder.txtBirthday.setText("Ngày sinh: " + p.birthday);
        holder.txtHometown.setText("Quê: " + p.hometown);
        holder.txtResidence.setText("Nơi ở: " + p.residence);
        holder.txtRating.setText("Đơn: " + p.rating_single + " | Đôi: " + p.rating_double);

        if (p.avatar != null && !p.avatar.isEmpty()) {
            Glide.with(context).load(p.avatar).into(holder.imgAvatar);
        } else {
            holder.imgAvatar.setImageResource(android.R.drawable.sym_def_app_icon); // icon mặc định
            // ảnh mặc định nếu không có
        }

        holder.btnDelete.setOnClickListener(v -> {
            ref.child(p.member_code).removeValue();
        });

        holder.btnEdit.setOnClickListener(v -> {
            // TODO: Mở Dialog sửa thông tin
        });
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public static class PlayerViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtCode, txtBirthday, txtHometown, txtResidence, txtRating;
        Button btnEdit, btnDelete;
        ImageView imgAvatar;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtCode = itemView.findViewById(R.id.txtCode);
            txtBirthday = itemView.findViewById(R.id.txtBirthday);
            txtHometown = itemView.findViewById(R.id.txtHometown);
            txtResidence = itemView.findViewById(R.id.txtResidence);
            txtRating = itemView.findViewById(R.id.txtRating);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
        }
    }
}
