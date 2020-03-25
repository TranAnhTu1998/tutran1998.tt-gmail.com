package com.example.contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListItemAdapter extends ArrayAdapter<Item> {
    ListItemAdapter(Context context, int textViewResourceId){
        super(context, textViewResourceId);
    }

    ListItemAdapter(Context context, int resource, List<Item> listItem){
        super(context, resource, listItem);
        //productViewPagerList = items;
    }



    /*public ProductViewPager getItem(int position) {
        return productViewPagerList.get(position);
    }*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null){
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.form_item,null);
        }
        Item item =  (Item) getItem(position);
        // call textNumber() not in onCreate() but here
        // it will be million times faster
        ((TextView) view.findViewById(R.id.tv_name_main)).setText(String.format(item.getFistName() + " " + item.getLastName())); // same code
        //Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageView);
        ((TextView) view.findViewById(R.id.tv_number_main)).setText("Telephone :" + String.format(item.getNumber()));

        ((TextView) view.findViewById(R.id.tv_email)).setText("Email :" + String.format(item.getEmail()));

        ((TextView) view.findViewById(R.id.tv_created_at)).setText("Time created : " + String.format(item.getCreatedAt()));

        ((TextView) view.findViewById(R.id.tv_modified_at)).setText("Time modified : " + String.format(item.getModifiedAt()));


        //Xem giới tính
        ImageView imageView = ((ImageView)view.findViewById(R.id.iv_photo_item));
        if(item.isGender()){
            //imageView.setImageResource(R.drawable.ic_launcher_background);
            imageView.setImageResource(R.drawable.icon_men_chandung);
        }
        else {
            ((ImageView)view.findViewById(R.id.iv_photo_item)).setImageResource(R.drawable.icon_femen_chandung);
        }

        //ImageView imageView = view.findViewById(R.id.imageViewGraphic);
        //Picasso.with(getContext()).load(product.getUrlImage()).into(imageView);
        /*if(product % 2 == 0){
            viewProduct.setBackgroundColor(Color.WHITE);
            //((TextView) viewProduct.findViewById(R.id.number)).setTextColor(Color.WHITE);

        }
        else {
            viewProduct.setBackgroundColor(Color.GRAY);
        }*/
        return view;
    }
}
