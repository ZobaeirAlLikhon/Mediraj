package com.example.mediraj.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductConfirmation {

    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("response")
    @Expose
    public Integer response;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("data")
    @Expose
    public Data data;
    public class Data {
        @SerializedName("order")
        @Expose
        public Order order;
        @SerializedName("orderItems")
        @Expose
        public List<OrderItem> orderItems = null;
        public class Order {
            @SerializedName("id")
            @Expose
            public Integer id;
            @SerializedName("order_no")
            @Expose
            public String orderNo;
            @SerializedName("user_id")
            @Expose
            public Integer userId;
            @SerializedName("contact_name")
            @Expose
            public String contactName;
            @SerializedName("contact_mobile")
            @Expose
            public String contactMobile;
            @SerializedName("contact_address")
            @Expose
            public String contactAddress;
            @SerializedName("order_status")
            @Expose
            public String orderStatus;
            @SerializedName("confirmed_date")
            @Expose
            public Object confirmedDate;
            @SerializedName("cancelled_date")
            @Expose
            public Object cancelledDate;
            @SerializedName("cancelled_reason")
            @Expose
            public Object cancelledReason;
            @SerializedName("completed_date")
            @Expose
            public Object completedDate;
            @SerializedName("net_amount")
            @Expose
            public Object netAmount;
            @SerializedName("vat_amount")
            @Expose
            public Object vatAmount;
            @SerializedName("service_charge")
            @Expose
            public Object serviceCharge;
            @SerializedName("misc_cost_amount")
            @Expose
            public Object miscCostAmount;
            @SerializedName("misc_cost_details")
            @Expose
            public Object miscCostDetails;
            @SerializedName("total_amount")
            @Expose
            public Integer totalAmount;
            @SerializedName("rating")
            @Expose
            public Object rating;
            @SerializedName("review")
            @Expose
            public Object review;
            @SerializedName("dates")
            @Expose
            public String dates;
            @SerializedName("months")
            @Expose
            public String months;
            @SerializedName("years")
            @Expose
            public Integer years;
            @SerializedName("created_at")
            @Expose
            public String createdAt;
            @SerializedName("updated_at")
            @Expose
            public String updatedAt;
        }
        public class OrderItem {

            @SerializedName("id")
            @Expose
            public Integer id;
            @SerializedName("order_id")
            @Expose
            public Integer orderId;
            @SerializedName("item_id")
            @Expose
            public Integer itemId;
            @SerializedName("item_unit")
            @Expose
            public String itemUnit;
            @SerializedName("item_quantity")
            @Expose
            public Integer itemQuantity;
            @SerializedName("item_price")
            @Expose
            public Integer itemPrice;
            @SerializedName("item_subtotal_amount")
            @Expose
            public Integer itemSubtotalAmount;
            @SerializedName("created_at")
            @Expose
            public String createdAt;
            @SerializedName("updated_at")
            @Expose
            public Object updatedAt;

        }

    }
}