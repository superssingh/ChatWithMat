package com.santoshkumarsingh.chatwithmat.Models;

/**
 * Created by santoshsingh on 02/03/18.
 */

public class ContactModel {
    public String id;
    public String name;
    public String number;
    public String photoURI;

    public ContactModel() {
    }

    public ContactModel(String id, String name, String number, String photoURI) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.photoURI = photoURI;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPhotoURI() {
        return photoURI;
    }

    public void setPhotoURI(String photoURI) {
        this.photoURI = photoURI;
    }
}
