package com.example.kimhun.thewalker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView

class ShopActivity : AppCompatActivity() {

    private lateinit var moneyText : TextView // 2걸음당 1원
    private lateinit var shoesImage : ImageView
    private lateinit var shoesNameText : TextView
    private lateinit var shoesCostText : TextView
    private lateinit var shoesContextText : TextView
    val nowShoes by lazy { (intent.extras["shoes"] ?: 0).toString().toInt() }
    val money by lazy { (intent.extras["money"] ?: 0).toString().toInt() }

    private val shoesArrayList by lazy {
        arrayListOf(ItemsListItem(ContextCompat.getDrawable(this,R.drawable.shoes),"맨 발",0,"신발이 없다... ㅠㅠ\n[걸음당 +10pt]",10),
                    ItemsListItem(ContextCompat.getDrawable(this,R.drawable.shoes),"슬리퍼",10000,"슬리퍼가 더 느릴수도?...비싼건 묻지말자 [걸음당 +20pt]",20),
                    ItemsListItem(ContextCompat.getDrawable(this,R.drawable.shoes),"고무신",30000,"홍길동이 신고다녔다.ㅋㅋㅋ\n[걸음당 +30pt]",30),
                    ItemsListItem(ContextCompat.getDrawable(this,R.drawable.shoes),"운동화", 70000, "그냥 운동화\n[걸음당 +40pt]",40))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        Log.d("shoes","nowShoes is " + nowShoes.toString())

        moneyText = findViewById(R.id.money) as TextView
        shoesImage = findViewById(R.id.now_shoes_img) as ImageView
        shoesNameText = findViewById(R.id.now_shoes_name) as TextView
        shoesCostText = findViewById(R.id.now_shoes_cost) as TextView
        shoesContextText = findViewById(R.id.now_shoes_context) as TextView

        moneyText.text = money.toString() + " 개"
        shoesImage.setImageDrawable(shoesArrayList[nowShoes].itemImage)
        shoesNameText.text = shoesArrayList[nowShoes].itemName
        shoesCostText.text = shoesArrayList[nowShoes].itemCost.toString()
        shoesContextText.text = shoesArrayList[nowShoes].itemContext

        val itemsList = findViewById(R.id.items_list) as ListView
        val adapter = ItemsListViewAdapter(this,shoesArrayList,nowShoes,money)

        itemsList.adapter = adapter
    }
}