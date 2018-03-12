package com.santoshkumarsingh.chatwithmat.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.santoshkumarsingh.chatwithmat.Adapters.ContactRecyclerAdapter;
import com.santoshkumarsingh.chatwithmat.Models.ContactModel;
import com.santoshkumarsingh.chatwithmat.R;
import com.santoshkumarsingh.chatwithmat.Utilities.ContactList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ContactActivity extends AppCompatActivity {

    // Request code for READ_CONTACTS. It can be any number > 0.
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ContactRecyclerAdapter contactRecyclerAdapter;

    private List<ContactModel> contacts;
    private ContactList contactList;

    private CompositeDisposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        recyclerView = findViewById(R.id.contact_recyclerView);
        progressBar = findViewById(R.id.progress_bar);
        contacts = new ArrayList<>();
        disposable = new CompositeDisposable();

        Load_AudioFiles();

    }

    private void initRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void Load_AudioFiles() {
        disposable.add(getContacts()
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        initRecyclerView();
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        showContacts();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        setDataIntoAdapter(contacts);
                        progressBar.setVisibility(View.GONE);
                    }
                })
                .subscribeWith(new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull Integer integer) {
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.e("Error::ContactActivity", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("OnComplete:: ", "Completed");

                    }
                }));
    }

    private void setDataIntoAdapter(List<ContactModel> contacts) {
        contactRecyclerAdapter.addList(contacts);
    }

    private Observable<Integer> getContacts() {
        return Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 0;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
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

