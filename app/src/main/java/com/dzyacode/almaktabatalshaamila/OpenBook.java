package com.dzyacode.almaktabatalshaamila;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import com.folioreader.FolioReader;
import com.folioreader.model.locators.ReadLocator;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class OpenBook extends AppCompatActivity {

    PDFView pdfView;
    String bookPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_book);

        pdfView = findViewById(R.id.pdfView);

        bookPath = getIntent().getStringExtra("bookPath");

        if (bookPath.substring(bookPath.lastIndexOf(".") + 1).equalsIgnoreCase("pdf")) {
            new RetrievePDFFromUrl().execute(bookPath);
        } else {
            openEpub(bookPath);
        }

    }

    public void openEpub(String bookPath) {
        System.out.println("++++++++++++++++++++++++++++++++ - - - - - -  " + bookPath);
        FolioReader folioReader = FolioReader.get();
        // Call the openBook method with the path of the EPUB file
        folioReader.openBook(bookPath);
        //folioReader.openBook(R.raw.test);
    }

    // create an async task class for loading pdf file from URL.
    class RetrievePDFFromUrl extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            // for getting our PDF.
            InputStream inputStream = null;
            try {
                // open a FileInputStream to the local file
                File file = new File(strings[0]);
                inputStream = new FileInputStream(file);
            } catch (IOException e) {
                // handle errors
                e.printStackTrace();
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            // after the execution of our async
            // task we are loading our pdf in our pdf view.
            if (inputStream != null) {
                pdfView.fromStream(inputStream).enableSwipe(true).load();
            }
        }
    }


}