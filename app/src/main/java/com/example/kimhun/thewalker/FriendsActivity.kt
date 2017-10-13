package com.example.kimhun.thewalker

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

class FriendsActivity : Activity() {

    private lateinit var friendsBtn : Button
    private lateinit var friendsID : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        var friendsListView = findViewById(R.id.friendsListView) as ListView
        var adapter = FriendsListViewAdapter()
        friendsID = findViewById(R.id.add_friends) as EditText


        friendsListView.adapter = adapter

        adapter.addItem(1,"KIM",1555123)
        adapter.addItem(2,"Lee",12323)
        adapter.addItem(3,"Park",123)

        friendsBtn = findViewById(R.id.addFriend) as Button
        friendsBtn.setOnClickListener{


        }
    }
}
