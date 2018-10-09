package com.example.yooho.zerostart.myaidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.yooho.zerostart.BookManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haoou on 2018/8/30.
 */

public class AIDLService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return mBookManager;
    }

    private List<Book> mBooks = new ArrayList<>();

    private final BookManager.Stub mBookManager = new BookManager.Stub() {
        @Override
        public List<Book> getBooks() throws RemoteException {
            if (mBooks != null) {
                return mBooks;
            }
            return new ArrayList<>();
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            if (mBooks == null) {
                mBooks = new ArrayList<>();
            }
            mBooks.add(book);
            book.setPrice(2333);
            Log.e("SS", "service execute addBook()");
            for (Book book1 : mBooks) {
                Log.e("SS", book1.toString());
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("SS", "service onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("SS", "service onCreate  class:" + intent.getClass() + "  action:" +
            intent.getClass());
        return super.onStartCommand(intent, flags, startId);
    }
}
