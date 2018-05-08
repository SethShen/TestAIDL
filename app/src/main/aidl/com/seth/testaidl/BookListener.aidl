// BookListener.aidl
package com.seth.testaidl;
import com.seth.testaidl.Book;

// Declare any non-default types here with import statements

interface BookListener {
    void NewBookReceiverd(in Book book);

}
