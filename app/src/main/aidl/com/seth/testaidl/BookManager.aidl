// BookManager.aidl
package com.seth.testaidl;

// Declare any non-default types here with import statements
import com.seth.testaidl.Book;
import com.seth.testaidl.BookListener;

interface BookManager {
    List<Book> getBooks();
    void addBook(in Book book);
    void registerBookListener(BookListener listener);
    void unregisterBookListener(BookListener listener);

//   //传参时除了Java基本类型以及String，CharSequence之外的类型
//    //都需要在前面加上定向tag，具体加什么量需而定
//    void setBookPrice(in Book book , int price);
//    void setBookName(in Book book , String name);
//    void addBookIn(in Book book);
//    void addBookOut(out Book book);
//    void addBookInout(inout Book book);
}
