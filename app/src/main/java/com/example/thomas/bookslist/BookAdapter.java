package com.example.thomas.bookslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Item> {

    private int LAYOUT_RESOURCE_ID = R.layout.item_book;
    private List<Item> bookList;

    public BookAdapter(Context context) {
        super(context, 0);
    }

    public void setBookList (List<Item> bookList) {
        super.clear();
        super.addAll(bookList);
        this.bookList = bookList;
    }

    public View getView(int position, View convertView , ViewGroup parent)
    {
        if ( convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(LAYOUT_RESOURCE_ID,null);
        }

        TextView title = (TextView) convertView.findViewById(R.id.title);
        ImageView thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);

        final Item currentItem = bookList.get(position);

        title.setText(currentItem.getVolumeInfo().getTitle());

        String imgURL = (currentItem.getVolumeInfo().getImageLinks() != null) ?
                currentItem.getVolumeInfo().getImageLinks().getSmallThumbnail() :
                "https://http.cat/404";
        Picasso.with(getContext())
                .load(imgURL)
                .fit()
                .into(thumbnail);



        return convertView;
    }
}
