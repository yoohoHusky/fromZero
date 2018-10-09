package com.example.yooho.husky.myapplication.aidldemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.yooho.husky.myapplication.R;
import com.example.yooho.zerostart.BookManager;
import com.example.yooho.zerostart.myaidl.Book;

import java.util.List;


/**
 * Created by haoou on 2018/8/30.
 */

public class AIDLActivity extends AppCompatActivity {
    private BookManager mBookManager;
    private boolean mBound = false;
    private List<Book> mBooks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_aidl);

        MyListener myListener = new MyListener();
        findViewById(R.id.btn_aidl_connect).setOnClickListener(myListener);
        findViewById(R.id.btn_aidl_disconnect).setOnClickListener(myListener);
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBookManager = BookManager.Stub.asInterface(service);
            mBound = true;
            if (mBookManager != null) {
                try {
                    mBooks = mBookManager.getBooks();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    private void bindService() {
        Intent intent = new Intent();
        intent.setAction("com.yooho.aidl");
        intent.setPackage("com.example.yooho.zerostart");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_aidl_connect) {
                if (mBound == false || mBookManager == null) {
                    bindService();
                    return;
                }
                Book book = new Book();
                book.setName("学习aidl");
                book.setPrice(30);
                try {
                    mBookManager.addBook(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            } else if (v.getId() == R.id.btn_aidl_disconnect) {
                try {
                    List<Book> books = mBookManager.getBooks();
                    for (Book k : books) {
                        Log.e("SS", "client " + books.toString());
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mBound) {
            bindService();
        }
    }
}
