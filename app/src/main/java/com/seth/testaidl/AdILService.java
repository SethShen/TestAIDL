package com.seth.testaidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by hspcadmin on 2018/5/7.
 */

public class AdILService extends Service {
    public final String TAG = this.getClass().getSimpleName();

    private List<Book> mBooks = new ArrayList<>();
    private CopyOnWriteArrayList<BookListener> mListeners = new CopyOnWriteArrayList<>();

    private final BookManager.Stub bookManager = new BookManager.Stub() {
        @Override
        public List<Book> getBooks() throws RemoteException {
            synchronized (this){
                Log.e(TAG, "invoking getBooks() method , now the list is : " + mBooks.toString());
                if (mBooks != null) {
                    return mBooks;
                }
            }
            return new ArrayList<>();
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            synchronized (this) {
                if (mBooks == null) {
                    mBooks = new ArrayList<>();
                }
                if (book == null) {
                    Log.e(TAG, "Book is null in In");
                    book = new Book();
                }
                //尝试修改book的参数，主要是为了观察其到客户端的反馈
                //book.setPrice(2333);
                if (!mBooks.contains(book)) {
                    mBooks.add(book);
                    newBookReceivered(book);
                }
                //打印mBooks列表，观察客户端传过来的值
                Log.e(TAG, "invoking addBooks() method , now the list is : " + mBooks.toString());
            }
        }

        @Override
        public void registerBookListener(BookListener listener) throws RemoteException {
            if(mListeners.contains(listener)){
                Log.w(TAG,"add book failed,already exist");
                return;
            }else{
                mListeners.add(listener);
                Log.w(TAG,"Successfully added;");
            }
        }

        @Override
        public void unregisterBookListener(BookListener listener) throws RemoteException {
            if (mListeners.remove(listener)){
                Log.w(TAG,"Successfully deleted;");
            }else{
                Log.w(TAG,"delete book failed,is not exist");
            }
        }
    };

    private void newBookReceivered(Book book) {
        Iterator<BookListener> listenerIterator = mListeners.iterator();
        while (listenerIterator.hasNext()){
            try {
                listenerIterator.next().NewBookReceiverd(book);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(getClass().getSimpleName(), String.format("on bind,intent = %s", intent.toString()));
        return bookManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Book book = new Book();
        book.setName("Android开发艺术探索");
        book.setPrice(28);
        mBooks.add(book);
    }
}
