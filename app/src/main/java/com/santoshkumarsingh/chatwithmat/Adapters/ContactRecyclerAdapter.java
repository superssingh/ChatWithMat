package com.santoshkumarsingh.chatwithmat.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.santoshkumarsingh.chatwithmat.Models.ContactModel;
import com.santoshkumarsingh.chatwithmat.R;

import java.util.List;

/**
 * Created by santoshsingh on 30/01/18.
 */

public class ContactRecyclerAdapter extends RecyclerView.Adapter<ContactRecyclerAdapter.CustomViewHolder> {

    private List<ContactModel> contactModelList;

    public ContactRecyclerAdapter(List<ContactModel> contactModelList) {
        this.contactModelList=contactModelList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        ContactModel contactModel=contactModelList.get(position);

        holder.contactName.setText(contactModel.getName());
        holder.contactNumber.setText(contactModel.getMobileNumber());
//        holder.contactImage.setImageBitmap(contactModel.getPhoto());
        Glide.with(holder.itemView.getContext()).load(contactModel.getPhoto()).into(holder.contactImage);

    }

    @Override
    public int getItemCount() {
        return contactModelList.size();
    }

    public void add(ContactModel contactModel) {
        contactModelList.add(contactModel);
    }


    public void clear() {
        contactModelList.clear();
        notifyDataSetChanged();
    }

    public void addList(List<ContactModel> contactModelList) {
        this.contactModelList=contactModelList;
        notifyDataSetChanged();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        ImageView contactImage;
        TextView contactName, contactNumber;

        public CustomViewHolder(View itemView) {
            super(itemView);
            contactImage = (ImageView) itemView.findViewById(R.id.contactImage);
            contactName= (TextView) itemView.findViewById(R.id.contact_name);
            contactNumber= (TextView) itemView.findViewById(R.id.contact_number);
        }

    }
}
