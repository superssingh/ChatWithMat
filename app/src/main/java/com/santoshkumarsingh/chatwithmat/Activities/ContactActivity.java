package com.santoshkumarsingh.chatwithmat.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.santoshkumarsingh.chatwithmat.Adapters.ContactRecyclerAdapter;
import com.santoshkumarsingh.chatwithmat.Models.ContactModel;
import com.santoshkumarsingh.chatwithmat.R;
import com.santoshkumarsingh.chatwithmat.Utilities.ContactList;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ContactRecyclerAdapter contactRecyclerAdapter;

    private List<ContactModel> contacts;
    private ContactList contactList;

    // Request code for READ_CONTACTS. It can be any number > 0.
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        contacts = new ArrayList<>();
        initRecyclerView();
        showContacts();
    }

    private void initRecyclerView() {
        recyclerView=(RecyclerView)findViewById(R.id.contact_recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            contactList = new ContactList();
            contacts = contactList.getContacts(this);
            if (contacts!=null) {
                // add messages into recyclerview
                contactRecyclerAdapter= new ContactRecyclerAdapter(contacts);
                recyclerView.setAdapter(contactRecyclerAdapter);
                Toast.makeText(getApplicationContext(),"Contact found",Toast.LENGTH_LONG).show();

            }else{
                Toast.makeText(getApplicationContext(),"No Contact found",Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot show the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

}

