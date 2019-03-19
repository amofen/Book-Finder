package com.example.bookfinder.data;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

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
        HttpURLConnection httpConnection;
        try {
            httpConnection = (HttpURLConnection) new URL("https://www.googleapis.com/books/v1/volumes?q=isbn:"+bareCode).openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            String read;
            while ((read=br.readLine()) != null) {
                sb.append(read);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        String jsonBook = sb.toString();
        Log.d("BOOK",jsonBook);
        Book book = BookParser.parseFromJson(jsonBook);
        book.setIsbn(bareCode);
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
