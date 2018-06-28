package com.codepriest.com.journalapp.Utils;

import android.content.Context;

import com.codepriest.com.journalapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by codepriest on 27/06/2018.
 */

public class FireBaseConfiguration {
    private static DatabaseReference ref;

    public static DatabaseReference getFireBaseDataBaseReferenceForUser(Context c) {
        if (ref == null) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            ref = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".", "").replace("@", "")).child(c.getResources().getString(R.string.entity_name));
            ref.keepSynced(true);
        }

        return ref;

    }

    public static void Reset() {
        ref = null;
    }
}
