package com.dzyacode.almaktabatalshaamila;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class BookDetails extends AppCompatActivity {

    private static final int REQUEST_PERMISSION = 1;
    private static final String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private String bookAuthor;
    private String bookDescription;
    private String bookFirstBatch;
    private String bookName;
    private String bookPages;
    private String bookPublisher;
    private String bookShortDescription;
    private String bookUrl;
    private String bookCat;

    TextView name, desShort, auth, pub, first, pages, des;
    Button downloadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        name = findViewById(R.id.book_name_de);
        desShort = findViewById(R.id.book_des_short_de);
        auth = findViewById(R.id.book_auth_de);
        pub = findViewById(R.id.book_pub_de);
        first = findViewById(R.id.book_first_de);
        pages = findViewById(R.id.book_pages_de);
        des = findViewById(R.id.book_des_de);
        downloadBtn = findViewById(R.id.download_de);

        bookAuthor = getIntent().getStringExtra("bookAuthor");
        bookDescription = getIntent().getStringExtra("bookDescription");
        bookFirstBatch = getIntent().getStringExtra("bookFirstBatch");
        bookName = getIntent().getStringExtra("bookName");
        bookPages = getIntent().getStringExtra("bookPages");
        bookPublisher = getIntent().getStringExtra("bookPublisher");
        bookShortDescription = getIntent().getStringExtra("bookShortDescription");
        bookUrl = getIntent().getStringExtra("bookUrl");

        name.setText(bookName);
        desShort.setText(bookShortDescription);
        auth.setText(bookAuthor);
        pub.setText(bookPublisher);
        first.setText(bookFirstBatch);
        pages.setText(bookPages);
        des.setText(bookDescription);

        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        bookCat = sh.getString("catName", "");

        File file = new File(Environment.getExternalStorageDirectory(), "example.txt");

        downloadBtn.setOnClickListener(task -> checkPermissions());

    }// END On

    public void checkPermissions() {
        int permission = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION);
        } else {
            download();
        }
    }

    private void download() {
        // URL of the file in Firebase Storage
        String url = bookUrl;

        // Extract the file extension from the URL
        String fileExtension = url.substring(url.lastIndexOf("."), url.indexOf("?"));

        // Check if the file extension is valid
        if (!fileExtension.equals(".pdf") && !fileExtension.equals(".epub")) {
            Toast.makeText(this, "Invalid file format", Toast.LENGTH_SHORT).show();
            return;
        }

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle("Your file");  // Title of the Download Notification
        request.setDescription("Downloading...");  // Description of the Download Notification
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        // Customize the download path here for new Android Vs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "elshamela/" + "/" + bookName + fileExtension);
        } else {
            // Customize the download path here for old Android Vs
            File booksDir = new File(Environment.getExternalStorageDirectory() + "/elshamela/");
            if (!booksDir.exists()) {
                booksDir.mkdirs();
            }
            File outputFile = new File(booksDir, bookName + fileExtension);
            request.setDestinationUri(Uri.fromFile(outputFile));
        }

        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
        downloadDone();
    }


    /*private void download() {
        // URL of the PDF file in Firebase Storage
        String url = bookUrl;

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle("Your PDF file");  // Title of the Download Notification
        request.setDescription("Downloading...");  // Description of the Download Notification
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        // Customize the download path here for new Android Vs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "elshamela/" + "/" + bookName + ".pdf");
        } else {
            // Customize the download path here for old Android Vs
            File booksDir = new File(Environment.getExternalStorageDirectory() + "/elshamela/");
            if (!booksDir.exists()) {
                booksDir.mkdirs();
            }
            File outputFile = new File(booksDir, bookName + ".pdf");
            request.setDestinationUri(Uri.fromFile(outputFile));
        }

        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
        downloadDone();
    }*/



    @SuppressLint("SetTextI18n")
    private void downloadDone() {
        AlertDialog.Builder builder = new AlertDialog.Builder(BookDetails.this);

        View customView = BookDetails.this.getLayoutInflater().inflate(R.layout.close_dialog, null);
        builder.setView(customView);

        TextView txt = customView.findViewById(R.id.textView);
        txt.setText("تم بدأ التحميل");

        Button yesBtn = customView.findViewById(R.id.yes_btn);
        yesBtn.setVisibility(View.GONE);

        Button noBtn = customView.findViewById(R.id.no_btn);
        noBtn.setVisibility(View.GONE);

        builder.create().show();
    }

}