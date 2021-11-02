package com.example.mediraj.localdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mediraj.model.checkout.Checkout;

import java.util.List;

@Dao
public interface DiagnosticServiceDao {

    @Query("SELECT * FROM diagnosticService")
    List<DiagnosticService> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertInfo(DiagnosticService diagnosticService);

    @Query("DELETE FROM diagnosticservice")
    abstract void deleteAllData();

    @Query("DELETE FROM diagnosticservice WHERE id = :id")
    abstract void deleteByIdOne(long id);

    @Query("DELETE FROM diagnosticservice WHERE id = :id")
    abstract void deleteById(int id);

    @Query("UPDATE diagnosticservice SET item_qty =:quantity ,item_subtotal=:total WHERE id =:id")
    void updateUser(int id,int quantity,int total);

    @Query("SELECT item_id,item_unit,item_qty,item_price,item_subtotal FROM diagnosticService")
    List<Checkout> getCheckoutData();

    @Query("delete from diagnosticservice where id in (:idList)")
    void deleteByIdList(List<Long> idList);

}
