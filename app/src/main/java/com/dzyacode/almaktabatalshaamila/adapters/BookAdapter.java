package com.dzyacode.almaktabatalshaamila.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.dzyacode.almaktabatalshaamila.models.Book;
import com.dzyacode.almaktabatalshaamila.BookDetails;
import com.dzyacode.almaktabatalshaamila.R;

import java.util.ArrayList;

public class BookAdapter  extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private ArrayList<Book> bookArrayList;
    private Context context;

    public BookAdapter(ArrayList<Book> bookArrayList, Context context) {
        this.bookArrayList = bookArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.book_show_one, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.ViewHolder holder, int position) {
        Book book = bookArrayList.get(position);
        holder.bookName.setText(book.getBook_name());
        holder.bookAuth.setText(book.getBook_author());

        holder.bookClick.setOnClickListener(task -> context.startActivity(new Intent(context, BookDetails.class)
                .putExtra("bookAuthor", book.getBook_author())
                .putExtra("bookDescription", book.getBook_description())
                .putExtra("bookFirstBatch", book.getBook_first_batch())
                .putExtra("bookName", book.getBook_name())
                .putExtra("bookPages", book.getBook_pages())
                .putExtra("bookPublisher", book.getBook_publisher())
                .putExtra("bookShortDescription", book.getBook_short_description())
                .putExtra("bookCat", book.getBook_category())
                .putExtra("bookUrl", book.getBook_url())
        ));
    }

    @Override
    public int getItemCount() {
        return bookArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView bookName, bookAuth;
        private ImageView downloadBook;
        private ConstraintLayout bookClick;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bookName = itemView.findViewById(R.id.book_name_one);
            bookAuth = itemView.findViewById(R.id.book_auth_one);
            downloadBook = itemView.findViewById(R.id.download_one);
            bookClick = itemView.findViewById(R.id.book_click);

        }
    }
}
