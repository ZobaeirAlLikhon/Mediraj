package com.example.mediraj.localdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DiagnosticServiceDao {

    @Query("SELECT * FROM diagnosticService")
    List<DiagnosticService> getAll();

    @Insert()
    void insertInfo(DiagnosticService diagnosticService);

    @Delete
    void deleteInfo(DiagnosticService diagnosticService);

    @Query("DELETE FROM diagnosticservice WHERE item_id = :itemId")
    abstract void deleteById(int itemId);

    @Query("UPDATE diagnosticservice SET item_qty =:quantity ,item_subtotal=:total WHERE id =:id")
    int updateUser(int id,int quantity,int total);
}
