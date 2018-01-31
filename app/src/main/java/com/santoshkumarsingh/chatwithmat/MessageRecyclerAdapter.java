package com.santoshkumarsingh.chatwithmat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by santoshsingh on 30/01/18.
 */

public class MessageRecyclerAdapter extends RecyclerView.Adapter<MessageRecyclerAdapter.CustomViewHolder> {

    private List<FriendlyMessage> friendlyMessages;

    public MessageRecyclerAdapter(List<FriendlyMessage> friendlyMessages) {
        this.friendlyMessages = friendlyMessages;
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

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        ImageView photoImageView;
        TextView messageTextView, authorTextView;

        public CustomViewHolder(View itemView) {
            super(itemView);
            photoImageView = (ImageView) itemView.findViewById(R.id.photoImageView);
            messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
            authorTextView = (TextView) itemView.findViewById(R.id.nameTextView);
        }

    }

}
