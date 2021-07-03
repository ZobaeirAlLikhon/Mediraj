package com.example.mediraj.localdb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "surgical_services")
public class SurgicalService {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name="item_id")
    private int item_id;
    @ColumnInfo(name = "item_title")
    private String item_title;
    @ColumnInfo(name="item_unit")
    private String item_unit;
    @ColumnInfo(name = "item_qty")
    private String item_qty;
    @ColumnInfo(name = "item_price")
    private int item_price;
    @ColumnInfo(name = "item_subtotal")
    private int item_subtotal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public String getItem_unit() {
        return item_unit;
    }

    public void setItem_unit(String item_unit) {
        this.item_unit = item_unit;
    }

    public String getItem_qty() {
        return item_qty;
    }

    public void setItem_qty(String item_qty) {
        this.item_qty = item_qty;
    }

    public int getItem_price() {
        return item_price;
    }

    public void setItem_price(int item_price) {
        this.item_price = item_price;
    }

    public int getItem_subtotal() {
        return item_subtotal;
    }

    public void setItem_subtotal(int item_subtotal) {
        this.item_subtotal = item_subtotal;
    }
}
