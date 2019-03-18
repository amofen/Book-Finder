package com.example.bookfinder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bookfinder.data.Book;
import com.example.bookfinder.data.ThumbnailRetrievedListener;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

public class BookDetailsActivity extends AppCompatActivity implements ThumbnailRetrievedListener {
    private TextView txtTitle;
    private TextView txtBookId;
    private TextView txtAuthor;
    private TextView txtPublisher;
    private TextView txtPublishedDate;
    private TextView txtIsbn;
    private TextView txtCategory;
    private TextView txtPageCount;
    private TextView txtLink;
    private ImageView imgThumbnail;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        fetchViews();
        Book book = (Book)getIntent().getSerializableExtra("book");
        initViews(book);

    }

    private void fetchViews() {
        imgThumbnail = findViewById(R.id.inf_img);
        imgThumbnail.setVisibility(View.INVISIBLE);
        progressBar = findViewById(R.id.progressBar);
        txtTitle = findViewById(R.id.inf_title);
        txtTitle = findViewById(R.id.inf_title);
        txtBookId = findViewById(R.id.inf_id);
        txtAuthor  = findViewById(R.id.inf_author);
        txtPublisher = findViewById(R.id.inf_publisher);
        txtPublishedDate = findViewById(R.id.inf_pub_date);
        txtIsbn = findViewById(R.id.inf_isbn);
        txtCategory = findViewById(R.id.inf_category);
        txtPageCount = findViewById(R.id.inf_page_count);
        txtLink = findViewById(R.id.inf_link);
    }

    private void initViews(Book book){
        txtTitle.setText(book.getTitle());
        txtBookId.setText(book.getId());
        txtAuthor.setText(book.getAuthor());
        txtPublisher.setText(book.getPublisher());
        txtPublishedDate.setText(book.getPublishedDate());
        txtIsbn.setText(book.getIsbn());
        txtCategory.setText(book.getCategory());
        txtPageCount.setText(Integer.toString(book.getPagesCount()));
        txtLink.setText(book.getPreviewLink());
        FetchThumbnailTask task  = new FetchThumbnailTask(this);
        task.execute(book.getThumbnail());
    }

    @Override
    public void onThumbnailRetrieved(Bitmap bitmap){
        this.imgThumbnail.setImageBitmap(bitmap);
        this.imgThumbnail.setVisibility(View.VISIBLE);
        this.progressBar.setVisibility(View.GONE);

    }


    class FetchThumbnailTask extends AsyncTask<String,Void,Bitmap>{

        private ThumbnailRetrievedListener listener;

        public FetchThumbnailTask(ThumbnailRetrievedListener listener) {
            this.listener = listener;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try
            {
                Bitmap b;
                URL url = new URL(strings[0]);
                InputStream is = new BufferedInputStream(url.openStream());
                b = BitmapFactory.decodeStream(is);
                Thread.sleep(2000);
                return b;
            } catch(Exception e){
                Log.d("exception", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            listener.onThumbnailRetrieved(bitmap);


        }
    }


    }
