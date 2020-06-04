package com.example.androidjectpackdemo.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EBookShopRepository {
    private BookDAO mBookDAO;
    private CategoryDAO mCategoryDAO;
    private BooksDatabase mBooksDatabase;
    private Executor mExecutor;

    public EBookShopRepository(Application application) {
        mBooksDatabase = BooksDatabase.getInstance(application);
        mBookDAO = mBooksDatabase.bookDAO();
        mCategoryDAO = mBooksDatabase.categoryDAO();
        mExecutor = Executors.newFixedThreadPool(5);
    }

    public LiveData<List<Book>> getBooks(int categoryId) {
        return mBookDAO.getBooks(categoryId);
    }

    public LiveData<List<Category>> getAllCategory() {
        return mCategoryDAO.getAllCategories();
    }

    public void insertBook(Book book) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            mBookDAO.insert(book);
        });
    }

    public void deleteBook(Book book) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            mBookDAO.delete(book);
        });
    }

    public void updateBook(Book book) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            mBookDAO.update(book);
        });
    }
}
