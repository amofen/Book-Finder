package com.example.bookfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookfinder.data.Book;

public class BookDetailsActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        fetchViews();
        Book book = (Book)getIntent().getSerializableExtra("book");
        initViews(book);

    }

    private void fetchViews() {
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
        imgThumbnail = findViewById(R.id.inf_img);
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
        //TODO thumbnail insertion here
    }
}
