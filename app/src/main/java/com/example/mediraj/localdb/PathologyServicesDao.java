package com.example.mediraj.localdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mediraj.model.checkout.Checkout;

import java.util.List;

@Dao
public interface PathologyServicesDao {
    @Query("SELECT * FROM pathology")
    List<PathologyServices> getAllPathology();
    @Insert()
    Void insertInfo(PathologyServices pathologyServices);
    @Query("DELETE FROM pathology")
    abstract void deleteAllDataPath();
    @Query("DELETE FROM pathology WHERE item_id = :itemId")
    abstract void deleteByIdPath(int itemId);

    @Query("UPDATE pathology SET item_qty =:quantity ,item_subtotal=:total WHERE id =:id")
    void updateUserPath(int id,int quantity,int total);

    @Query("SELECT item_id,item_unit,item_qty,item_price,item_subtotal FROM pathology")
    List<Checkout> getCheckoutDataPath();
}
