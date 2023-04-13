package com.dzyacode.almaktabatalshaamila;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dzyacode.almaktabatalshaamila.adapters.BookAdapter;
import com.dzyacode.almaktabatalshaamila.models.Book;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class BookActivity extends AppCompatActivity {

    private RecyclerView bookRV;
    private ArrayList<Book> bookArrayList;
    private BookAdapter bookAdapter;
    private FirebaseFirestore db;
    private GifImageView loading;

    private String catName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        if (!isNetworkConnected()) {
            startActivity(new Intent(BookActivity.this, ConnectionFailedActivity.class));
        } else {
            catName = getIntent().getStringExtra("catName");

            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putString("catName", catName);
            myEdit.apply();
        }

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .setPersistenceEnabled(true)
                .build();
        FirebaseFirestore.getInstance().setFirestoreSettings(settings);

        bookRV = findViewById(R.id.idRVBooks);
        loading = findViewById(R.id.loading);
        db = FirebaseFirestore.getInstance();

        loading.setVisibility(View.VISIBLE);

        bookArrayList = new ArrayList<>();
        bookRV.setHasFixedSize(true);
        bookRV.setLayoutManager(new LinearLayoutManager(BookActivity.this));

        bookAdapter = new BookAdapter(bookArrayList, BookActivity.this);
        bookRV.setAdapter(bookAdapter);

        db.collection("Books").document(catName).collection("all").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            Book c = d.toObject(Book.class);
                            bookArrayList.add(c);
                            loading.setVisibility(View.GONE);
                        }
                        bookAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(BookActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BookActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    }

                });

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}