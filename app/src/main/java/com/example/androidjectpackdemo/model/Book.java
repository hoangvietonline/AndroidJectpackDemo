package com.example.androidjectpackdemo.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.androidjectpackdemo.BR;

import java.util.Objects;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "books_table", foreignKeys = @ForeignKey(entity = Category.class,
        parentColumns = "id", childColumns = "category_id", onDelete = CASCADE))

public class Book extends BaseObservable implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "book_id")
    private int bookId;

    @ColumnInfo(name = "book_name")
    private String bookName;

    @ColumnInfo(name = "unit_price")
    private String unitPrice;

    @ColumnInfo(name = "category_id")
    private int categoryId;

    public Book() {
    }

    public Book(int bookId, String bookName, String unitPrice, int categoryId) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.unitPrice = unitPrice;
        this.categoryId = categoryId;
    }

    protected Book(Parcel in) {
        bookId = in.readInt();
        bookName = in.readString();
        unitPrice = in.readString();
        categoryId = in.readInt();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Bindable
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
        notifyPropertyChanged(BR.bookId);

    }

    @Bindable
    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
        notifyPropertyChanged(BR.bookName);

    }

    @Bindable
    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
        notifyPropertyChanged(BR.unitPrice);

    }

    @Bindable
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        notifyPropertyChanged(BR.categoryId);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return getBookId() == book.getBookId() &&
                getCategoryId() == book.getCategoryId() &&
                getBookName().equals(book.getBookName()) &&
                getUnitPrice().equals(book.getUnitPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBookId(), getBookName(), getUnitPrice(), getCategoryId());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeString(bookName);
        dest.writeString(unitPrice);
        dest.writeInt(categoryId);
    }
}
