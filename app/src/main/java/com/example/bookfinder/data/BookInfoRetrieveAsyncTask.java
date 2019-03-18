package com.example.bookfinder.data;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class BookInfoRetrieveAsyncTask extends AsyncTask<String,Integer,Book> {
    private DataRetrievedListener listener;

    public BookInfoRetrieveAsyncTask(DataRetrievedListener listener) {
        this.listener = listener;
    }

    @Override
    protected Book doInBackground(String... strings) {
        String bareCode = strings[0];

        if(bareCode.isEmpty()){

            return null;
        }
        Book book = new Book();
        //TODO call the WS and fetch the book information
        book.setAuthor("Amine Aouffen");
        book.setCategory("Science");
        book.setId("xHgheTuz73");
        book.setIsbn("098323872563672");
        book.setPagesCount(522);
        book.setPreviewLink("http://books.google.com/android-book");
        book.setPublishedDate("2019-03-17");
        book.setPublisher("APress");
        book.setTitle("The Android Book");
        book.setThumbnail("https://books.google.com/books/content?id=fFtNPgAACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api");
        try{
            Thread.sleep(2000);
        }catch (Exception ex){
            Log.d("err", "doInBackground: Insomnia !");
        }
        return book;
    }

    @Override
    protected void onPostExecute(Book book) {
        super.onPostExecute(book);
        listener.onCompletedTask(book);
    }
}
