package com.dzyacode.almaktabatalshaamila.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dzyacode.almaktabatalshaamila.BookDetails;
import com.dzyacode.almaktabatalshaamila.OpenBook;
import com.dzyacode.almaktabatalshaamila.R;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

public class DownloadedAdapter extends RecyclerView.Adapter<DownloadedAdapter.MyViewHolder> {

    private List<File> fileList;
    private Context context;

    public DownloadedAdapter(Context context, List<File> fileList) {
        this.context = context;
        this.fileList = fileList;
    }

    public void setFiles(List<File> fileList) {
        this.fileList = fileList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_show_one, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        File file = fileList.get(position);

        holder.downloadOne.setVisibility(View.GONE);

        // 4. Override the necessary methods in the adapter to inflate the item layout
        // and bind the file data to the views.
        holder.nameTextView.setText(file.getName());
        holder.sizeTextView.setText(getSize(fileList.get(position).length()));

        holder.itemView.setOnClickListener(task -> context.startActivity(new Intent(context, OpenBook.class)
                .putExtra("bookPath", fileList.get(position).getPath())
        ));
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    public String getSize(long size) {
        if (size <= 0) {
            return "0 KB";
        }
        double d = (double) size / 1024; // convert bytes to kilobytes
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.#");
        return decimalFormat.format(d) + " KB";
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public TextView sizeTextView;
        private ImageView downloadOne;

        public MyViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.book_name_one);
            sizeTextView = view.findViewById(R.id.book_auth_one);
            downloadOne = view.findViewById(R.id.download_one);
        }
    }
}