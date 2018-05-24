package com.example.thomas.bookslist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BooksList extends AppCompatActivity {

    Button btn;
    ListView listView;
    EditText editText;
    List<Item> booksList;
    private BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_list);

        btn = (Button) findViewById(R.id.searchBtn);
        editText = (EditText) findViewById(R.id.search);

        listView = (ListView) findViewById(R.id.booksList);

        bookAdapter = new BookAdapter(BooksList.this);
        listView.setAdapter(bookAdapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String requestString = "";
                String searchText = editText.getText().toString().replace(" ", "+");
                if (searchText.equals("")) {
                    requestString += "''";
                } else {
                    requestString += searchText;
                }

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BookService.BASEURL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                BookService bookService = retrofit.create(BookService.class);
                Call<Book> books = bookService.listBooks(requestString);
                books.enqueue(new Callback<Book>() {
                    @Override
                    public void onResponse(Call<Book> call, Response<Book> response) {
                        Book myBooks = response.body();
                        booksList = myBooks.getItems();

                        bookAdapter.setBookList(booksList);
                        listView.deferNotifyDataSetChanged();
                        bookAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<Book> call, Throwable t) {
                        //Handle failure
                        String TAG = "onFailure: ";
                        Log.e(TAG, t.getMessage());
                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Item currentBook = booksList.get(position);
                        Intent intent = new Intent(BooksList.this, BookDetail.class);
                        Bundle b = new Bundle();
                        b.putSerializable("currentBook", currentBook);
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                });
            }
        });

    }
}
