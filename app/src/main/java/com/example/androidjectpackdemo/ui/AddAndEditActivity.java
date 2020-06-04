package com.example.androidjectpackdemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.androidjectpackdemo.R;
import com.example.androidjectpackdemo.databinding.ActivityAddAndEditBinding;
import com.example.androidjectpackdemo.model.Book;

public class AddAndEditActivity extends AppCompatActivity {
    public static final String BOOK_NAME = "bookName";
    private Book mBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_edit);
        com.example.androidjectpackdemo.databinding.ActivityAddAndEditBinding
                mActivityAddAndEditBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_add_and_edit);
        AddAndEditHandlers mHandlers = new AddAndEditHandlers();
        mActivityAddAndEditBinding.setClickHandlers(mHandlers);
        mBook = new Book();
        Intent intent = getIntent();
        if (intent.hasExtra(BOOK_NAME)) {
            mBook = intent.getParcelableExtra(BOOK_NAME);
        }
        mActivityAddAndEditBinding.setBook(mBook);
    }

    public class AddAndEditHandlers {
        public void onSubmit(View view) {
            Intent intent = new Intent();
            intent.putExtra(BOOK_NAME, mBook);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
