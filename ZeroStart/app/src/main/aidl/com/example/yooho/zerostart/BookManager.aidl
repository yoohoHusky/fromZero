// BookManager.aidl
package com.example.yooho.zerostart;

import com.example.yooho.zerostart.myaidl.Book;

// Declare any non-default types here with import statements

interface BookManager {
    List<Book> getBooks();
    void addBook(in Book book);
}
