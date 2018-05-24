package com.example.thomas.bookslist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class BookDetail extends AppCompatActivity {

    ImageView coverImg;
    TextView title;
    TextView subtitle;
    TextView publication;
    TextView author;
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        coverImg = (ImageView) findViewById(R.id.coverImg);
        title = (TextView) findViewById(R.id.title);
        subtitle = (TextView) findViewById(R.id.subtitle);
        publication = (TextView) findViewById(R.id.publication);
        author = (TextView) findViewById(R.id.author);
        description = (TextView) findViewById(R.id.description);


        Intent intent = getIntent();
        Item currentBook = (Item) intent.getSerializableExtra("currentBook");


        String imgURL = (currentBook.getVolumeInfo().getImageLinks() != null) ?
                currentBook.getVolumeInfo().getImageLinks().getSmallThumbnail() :
                "https://http.cat/404";
        Picasso.with(this)
                .load(imgURL)
                .fit()
                .into(coverImg);

        title.setText(currentBook.getVolumeInfo().getTitle());
        subtitle.setText(currentBook.getVolumeInfo().getSubtitle());
        publication.setText(currentBook.getVolumeInfo().getPublishedDate());
        String authors = "";
        Boolean isSet = false;
        for (String author : currentBook.getVolumeInfo().getAuthors()) {
            authors += author + ",";
            isSet = true;
        }
        if (isSet) {
            authors = authors.substring(0, authors.length() - 2);
            author.setText(authors);
        }
        description.setText(currentBook.getVolumeInfo().getDescription());

    }
}
