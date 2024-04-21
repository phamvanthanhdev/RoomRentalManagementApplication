package com.example.quanlynhatroapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.quanlynhatroapplication.Adapter.ListCartAdapter;
import com.example.quanlynhatroapplication.Class.Food;

import java.util.ArrayList;
import java.util.List;

public class InvoiceActivity extends AppCompatActivity {
    RecyclerView rcFood;
    List<Food> foodList;
    ListCartAdapter listCartAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        rcFood = findViewById(R.id.rcCart);
        foodList = new ArrayList<>();

        Food f1 = new Food();
        f1.setName("Margherita");
        f1.setLinkImage("https://www.engelvoelkers.com/wp-content/uploads/2014/07/pizza-stock.jpg");
        f1.setPrice(10.99);
        f1.setTimeProcessing(15);
        f1.setScore(4.5);

        Food f2 = new Food();
        f2.setName("Margherita");
        f2.setLinkImage("https://www.engelvoelkers.com/wp-content/uploads/2014/07/pizza-stock.jpg");
        f2.setPrice(10.99);
        f2.setTimeProcessing(15);
        f2.setScore(4.5);

        foodList.add(f1);
        foodList.add(f1);
        foodList.add(f1);


        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        rcFood.setLayoutManager(layoutManager);

        listCartAdapter = new ListCartAdapter(this, foodList);
        rcFood.setAdapter(listCartAdapter);
    }
}