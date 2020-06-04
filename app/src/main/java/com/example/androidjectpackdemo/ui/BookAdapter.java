package com.example.androidjectpackdemo.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DiffUtil.DiffResult;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidjectpackdemo.R;
import com.example.androidjectpackdemo.databinding.BookListItemBinding;
import com.example.androidjectpackdemo.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> mBooks;
    private OnClickItemListener mOnClickItemListener;

    public BookAdapter() {
        mBooks = new ArrayList<>();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BookListItemBinding bookListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.book_list_item, parent, false);
        return new BookViewHolder(bookListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.bookListItemBinding.setBook(mBooks.get(position));
    }

    @Override
    public int getItemCount() {
        return mBooks == null ? 0 : mBooks.size();
    }

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.mOnClickItemListener = onClickItemListener;
    }

    public void updateBooks(List<Book> books) {
//        mBooks = books;
//        notifyDataSetChanged();
        DiffResult result = DiffUtil.calculateDiff(new BookDiffUtil(mBooks,books),false);
        mBooks = books;
        result.dispatchUpdatesTo(BookAdapter.this);
    }

    interface OnClickItemListener {
        void onClickItem(Book book);
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        BookListItemBinding bookListItemBinding;

        BookViewHolder(@NonNull BookListItemBinding bookListItemBinding) {
            super(bookListItemBinding.getRoot());
            this.bookListItemBinding = bookListItemBinding;
            bookListItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickedPosition=getAdapterPosition();

                    if(mOnClickItemListener!=null && clickedPosition!=RecyclerView.NO_POSITION) {
                        mOnClickItemListener.onClickItem(mBooks.get(clickedPosition));
                    }
                }
            });
        }
    }
}
