package com.dzyacode.almaktabatalshaamila;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dzyacode.almaktabatalshaamila.adapters.CatAdapter;
import com.dzyacode.almaktabatalshaamila.models.Cat;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class DownloadFragment extends Fragment {

    public DownloadFragment() {
        // require a empty public constructor
    }

    private RecyclerView catRV;
    private ArrayList<Cat> catArrayList;
    private CatAdapter catRVAdapter;
    private FirebaseFirestore db;
    private GifImageView loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_download, container, false);

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .setPersistenceEnabled(true)
                .build();
        FirebaseFirestore.getInstance().setFirestoreSettings(settings);

        catRV = root.findViewById(R.id.idRVCat);
        loading = root.findViewById(R.id.loading);
        db = FirebaseFirestore.getInstance();

        loading.setVisibility(View.VISIBLE);

        catArrayList = new ArrayList<>();
        catRV.setHasFixedSize(true);
        catRV.setLayoutManager(new LinearLayoutManager(getContext()));

        catRVAdapter = new CatAdapter(catArrayList, getContext());
        catRV.setAdapter(catRVAdapter);

        db.collection("Cat").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            Cat c = d.toObject(Cat.class);
                            catArrayList.add(c);
                            loading.setVisibility(View.GONE);
                        }
                        catRVAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "No data found in Database", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    }

                });

        return root;
    }
}