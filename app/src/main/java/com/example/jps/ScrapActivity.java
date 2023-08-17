package com.example.jps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ScrapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrap);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("JPS");

        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataModel : dataSnapshot.getChildren()) {
                            Log.d("firebase", "Value: " + dataModel.getValue());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("firebase", "Error reading data", databaseError.toException());
                    }
                });
    }
}




//databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//        .addValueEventListner(object : ValueEventListner {
//            override fun onDataChange(snapshot: DataSnapShot)
//        {
//            for(dataModel in snapshot.children)
//        {
//            Log.d("add")
//        }
//
//        }
//        override fun onCancelled()
//        }


