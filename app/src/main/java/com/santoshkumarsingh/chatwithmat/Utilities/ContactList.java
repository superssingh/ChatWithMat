package com.santoshkumarsingh.chatwithmat.Utilities;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberUtils;

import com.santoshkumarsingh.chatwithmat.Models.ContactModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santoshsingh on 02/03/18.
 */

public class ContactList {

    public List<ContactModel> getContacts(Context ctx) {
        String lastnumber = "";
        List<ContactModel> list = new ArrayList<>();

        ContentResolver contentResolver = ctx.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC ");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {

                    Cursor cursorInfo = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);

                    while (cursorInfo.moveToNext()) {
                        boolean duplicate = false;
                        String number = cursorInfo.getString(cursorInfo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        if (PhoneNumberUtils.compare(lastnumber, number)) {
                            duplicate = true;
                        }

                        if (!duplicate) {
                            ContactModel info = new ContactModel();
                            info.id = id;
                            info.name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                            info.number = number;
                            info.photoURI = cursorInfo.getString(cursorInfo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

                            list.add(info);
                            lastnumber = number;
                        }
                    }

                    cursorInfo.close();
                }
            }
            cursor.close();
        }

        return list;
    }
}
