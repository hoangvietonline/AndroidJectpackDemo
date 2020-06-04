package com.example.androidjectpackdemo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.androidjectpackdemo.model.Book;
import com.example.androidjectpackdemo.model.Category;
import com.example.androidjectpackdemo.model.EBookShopRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private EBookShopRepository mEBookShopRepository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        mEBookShopRepository = new EBookShopRepository(application);
    }

    public LiveData<List<Book>> getBookList(int categoryId) {
        return mEBookShopRepository.getBooks(categoryId);
    }

    public LiveData<List<Category>> getCategoryList() {
        return mEBookShopRepository.getAllCategory();
    }

    public void insertBook(Book book) {
        mEBookShopRepository.insertBook(book);
    }

    public void deleteBook(Book book) {
        mEBookShopRepository.deleteBook(book);
    }

    public void updateBook(Book book) {
        mEBookShopRepository.updateBook(book);
    }
}
