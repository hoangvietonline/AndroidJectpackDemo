package com.example.androidjectpackdemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidjectpackdemo.R;
import com.example.androidjectpackdemo.databinding.ActivityMainBinding;
import com.example.androidjectpackdemo.model.Book;
import com.example.androidjectpackdemo.model.Category;
import com.example.androidjectpackdemo.viewmodel.MainActivityViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public static final int EDIT_REQUEST_CODE = 1;
    public static final int ADD_REQUEST_CODE = 2;
    @BindView(R.id.spinner)
    AppCompatSpinner mSpinner;
    @BindView(R.id.rvBooks)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private ArrayList<Category> mCategoriesList;
    private ArrayList<Book> mBookList;
    private MainActivityClickHandlers mHandlers;
    private MainActivityViewModel mViewModel;
    private ActivityMainBinding mActivityMainBinding;
    private Category mSelectCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolBar();
        initView();
    }
    private void initToolBar(){
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null){
            actionBar.setDisplayShowTitleEnabled(true);
        }
    }
    private void initView() {
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mHandlers = new MainActivityClickHandlers();
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mActivityMainBinding.setHanderClick(mHandlers);
        mViewModel.getCategoryList().observe(this, categories -> {
            mCategoriesList = (ArrayList<Category>) categories;
            loadSpinnerCategory();
        });
    }

    private void loadSpinnerCategory() {
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, mCategoriesList);
        categoryArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        mActivityMainBinding.setSpinnerAdapter(categoryArrayAdapter);
    }

    private void loadBookListByCategory(int categoryId) {
        mViewModel.getBookList(categoryId).observe(this, books -> {
            mBookList = (ArrayList<Book>) books;
            createRecyclerView(mBookList);
        });
    }

    private void createRecyclerView(ArrayList<Book> books) {
        mRecyclerView = mActivityMainBinding.secondaryLayout.rvBooks;
        BookAdapter bookAdapter = new BookAdapter();
        mRecyclerView.setAdapter(bookAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(
                this, RecyclerView.VERTICAL, false
        ));
        mRecyclerView.setHasFixedSize(true);
        bookAdapter.updateBooks(books);
        bookAdapter.setOnClickItemListener(book -> {
            Intent intent = new Intent(MainActivity.this, AddAndEditActivity.class);
            intent.putExtra(AddAndEditActivity.BOOK_NAME, book);
            startActivityForResult(intent, EDIT_REQUEST_CODE);
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.START) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Book book = mBookList.get(viewHolder.getAdapterPosition());
                mViewModel.deleteBook(book);
            }
        }).attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Book book = data.getParcelableExtra(AddAndEditActivity.BOOK_NAME);
            if (book != null) {
                book.setCategoryId(mSelectCategory.getId());
                mViewModel.updateBook(book);
            }
        }
        if (requestCode == ADD_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Book book = data.getParcelableExtra(AddAndEditActivity.BOOK_NAME);
            if (book != null) {
                book.setCategoryId(mSelectCategory.getId());
                mViewModel.insertBook(book);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    public class MainActivityClickHandlers {
        public void onClickFabHandler(View view) {
            Intent intent = new Intent(MainActivity.this, AddAndEditActivity.class);
            startActivityForResult(intent, ADD_REQUEST_CODE);
        }

        public void onSelectItem(AdapterView<?> parent, View view, int pos, long id) {
            mSelectCategory = (Category) parent.getItemAtPosition(pos);
            loadBookListByCategory(mSelectCategory.getId());
        }
    }
}
