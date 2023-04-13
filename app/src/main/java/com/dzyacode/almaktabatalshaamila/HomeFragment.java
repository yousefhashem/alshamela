package com.dzyacode.almaktabatalshaamila;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dzyacode.almaktabatalshaamila.adapters.DownloadedAdapter;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    File directory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        // 1. Create a list of file objects that represent the files in the directory.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            directory = new File(Environment.DIRECTORY_DOWNLOADS, "/elshamela/");
        } else {
            directory = new File(Environment.getExternalStorageDirectory() + "/elshamela/");
        }

        List<File> fileList = Arrays.asList(Objects.requireNonNull(directory.listFiles()));

        // 2. Create a RecyclerView and its corresponding adapter.
        RecyclerView recyclerView = root.findViewById(R.id.idRVBooksDown);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DownloadedAdapter adapter = new DownloadedAdapter(getContext(), fileList);

        // 3. Pass the list of files to the adapter.
        adapter.setFiles(fileList);

        // 5. Set the adapter on the RecyclerView.
        recyclerView.setAdapter(adapter);

        return root;
    }
}