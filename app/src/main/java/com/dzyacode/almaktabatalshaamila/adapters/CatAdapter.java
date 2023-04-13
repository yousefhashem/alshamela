package com.dzyacode.almaktabatalshaamila.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.dzyacode.almaktabatalshaamila.BookActivity;
import com.dzyacode.almaktabatalshaamila.models.Cat;
import com.dzyacode.almaktabatalshaamila.R;

import java.util.ArrayList;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.ViewHolder> {

    private ArrayList<Cat> catArrayList;
    private Context context;

    public CatAdapter(ArrayList<Cat> catArrayList, Context context) {
        this.catArrayList = catArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.book_show, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CatAdapter.ViewHolder holder, int position) {

        Cat cat = catArrayList.get(position);
        holder.bookName.setText(cat.getCatName());
        holder.bookNum.setText(cat.getNum());

        holder.bookClick.setOnClickListener(task -> context.startActivity(new Intent(context, BookActivity.class)
                .putExtra("catName", cat.getCatName())));
    }

    @Override
    public int getItemCount() {
        return catArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView bookName, bookNum;
        private ConstraintLayout bookClick;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bookName = itemView.findViewById(R.id.book_name);
            bookNum = itemView.findViewById(R.id.book_num);
            bookClick = itemView.findViewById(R.id.book_click);

        }
    }
}
