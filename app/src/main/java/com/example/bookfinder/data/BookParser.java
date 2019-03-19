package com.example.bookfinder.data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BookParser {
    public static Book parseFromJson(String json) {
        Book book = new Book();
        try {
            JSONObject jobject = new JSONObject(json);
            JSONArray jarray = jobject.getJSONArray("items");
            JSONObject jbook = jarray.getJSONObject(0);
            book.setId(jbook.getString("id"));
            setId(book,jbook);
            JSONObject jvolumeInfo = jbook.getJSONObject("volumeInfo");
            setTitle(book,jvolumeInfo);
            setAuthor(book,jvolumeInfo);
            setPublisher(book,jvolumeInfo);
            setPublishedDate(book,jvolumeInfo);
            setCategory(book,jvolumeInfo);
            setThumbnail(book,jvolumeInfo);
            setPreviewLink(book,jvolumeInfo);
            book.setPagesCount(jvolumeInfo.getInt("pageCount"));
            Log.d("BOOKTAG", book.toString());
        } catch (JSONException e) {
            Log.d("exception", e.getMessage());
            e.printStackTrace();
        }
        return book;
    }

    private static void setId(Book book, JSONObject jobject) {
        try {
            book.setId(jobject.getString("id"));
        } catch (Exception e) {
            Log.d("JSONException", "not found field ");
        }
    }


    private static void setTitle(Book book, JSONObject jobject) {
        try {
            book.setTitle(jobject.getString("title"));
        } catch (Exception e) {
            Log.d("JSONException", "not found field ");
            book.setTitle("Not found");
        }
    }

    private static void setAuthor(Book book, JSONObject jobject) {
        try {
            book.setAuthor(jobject.getJSONArray("authors").getString(0));
        } catch (Exception e) {
            Log.d("JSONException", "not found field ");
            book.setAuthor("Not found");
        }
    }

    private static void setPublisher(Book book, JSONObject jobject) {
        try {
            book.setPublisher(jobject.getString("publisher"));
        } catch (Exception e) {
            Log.d("JSONException", "not found field ");
            book.setPublisher("Not found");
        }
    }

    private static void setPublishedDate(Book book, JSONObject jobject) {
        try {
            book.setPublishedDate(jobject.getString("publishedDate"));
        } catch (Exception e) {
            Log.d("JSONException", "not found field ");
            book.setPublishedDate("Not found");
        }
    }
    private static void setCategory(Book book, JSONObject jobject) {
        try {
            book.setCategory(jobject.getJSONArray("categories").getString(0));
        } catch (Exception e) {
            Log.d("JSONException", "not found field ");
            book.setCategory("Not found");
        }
    }

    private static void setThumbnail(Book book, JSONObject jobject) {
        try {
            book.setThumbnail(jobject.getJSONObject("imageLinks").getString("thumbnail"));
        } catch (Exception e) {
            Log.d("JSONException", "not found field ");
            book.setThumbnail("https://www.labaleine.fr/sites/baleine/files/image-not-found.jpg");
        }
    }

    private static void setPreviewLink(Book book, JSONObject jobject) {
        try {
            book.setPreviewLink(jobject.getString("previewLink"));
        } catch (Exception e) {
            Log.d("JSONException", "not found field ");
            book.setPreviewLink("Not available");
        }
    }

}
