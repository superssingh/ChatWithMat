package com.santoshkumarsingh.chatwithmat.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.santoshkumarsingh.chatwithmat.Models.FriendlyMessage;
import com.santoshkumarsingh.chatwithmat.R;

import java.util.List;

/**
 * Created by santoshsingh on 30/01/18.
 */

public class MessageRecyclerAdapter extends RecyclerView.Adapter<MessageRecyclerAdapter.CustomViewHolder> {

    private List<FriendlyMessage> friendlyMessages;
    private String userId;
    private LinearLayout.LayoutParams params;

    public MessageRecyclerAdapter(List<FriendlyMessage> friendlyMessages) {
        this.friendlyMessages = friendlyMessages;
        params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        FriendlyMessage message = friendlyMessages.get(position);

        if (userId==message.getName()){
            params.gravity=Gravity.RIGHT;
//            params.setMargins(30,0,10,5);
        }else{
            params.gravity=Gravity.LEFT;
//            params.setMargins(10,0,30,5);
        }

        holder.messageLayout.setLayoutParams(params);
        setMessageInLayout(message, holder);
    }

    private void setMessageInLayout(FriendlyMessage message, CustomViewHolder holder) {
        boolean isPhoto = message.getPhotoUrl() != null;
        if (isPhoto) {
            holder.messageTextView.setVisibility(View.GONE);
            holder.photoImageView.setVisibility(View.VISIBLE);
            Glide.with(holder.photoImageView.getContext())
                    .load(message.getPhotoUrl())
                    .into(holder.photoImageView);
        } else {
            holder.messageTextView.setVisibility(View.VISIBLE);
            holder.photoImageView.setVisibility(View.GONE);
            holder.messageTextView.setText(message.getText());
        }

        holder.authorTextView.setText(message.getName());

    }

    @Override
    public int getItemCount() {
        return friendlyMessages.size();
    }

    public void add(FriendlyMessage friendlyMessage) {
        friendlyMessages.add(friendlyMessage);
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void clear() {
        friendlyMessages.clear();
        userId = "";
        notifyDataSetChanged();
    }

    public void addList(List<FriendlyMessage> friendlyMessages) {
        this.friendlyMessages=friendlyMessages;
        notifyDataSetChanged();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        LinearLayout messageLayout;
        ImageView photoImageView;
        TextView messageTextView, authorTextView;

        public CustomViewHolder(View itemView) {
            super(itemView);
            messageLayout = (LinearLayout) itemView.findViewById(R.id.messageLayout);
            photoImageView = (ImageView) itemView.findViewById(R.id.photoImageView);
            messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
            authorTextView = (TextView) itemView.findViewById(R.id.nameTextView);
        }

    }
}
