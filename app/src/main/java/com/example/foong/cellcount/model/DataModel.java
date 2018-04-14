package com.example.foong.cellcount.model;

import android.util.Log;

import com.example.foong.cellcount.database.CountRecord;
import com.example.foong.cellcount.database.DatabaseLoader;
import com.example.foong.cellcount.interfacer.DatabaseMvp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FOONG on 7/2/2017.
 */

public class DataModel implements DatabaseMvp.MvpModel {

    private DatabaseMvp.MvpMPresenter taskPresenter;
    private DatabaseLoader  dbLoader;

    public DataModel(DatabaseMvp.MvpMPresenter taskPresenter, DatabaseLoader dbLoader){
        this.taskPresenter  = taskPresenter;
        this.dbLoader       = dbLoader;
    }

    @Override
    public void removeItem(CountRecord cr) {
        dbLoader.deleteFromDatabase(cr.getRecordName());
    }

    @Override
    public void addItem(CountRecord cr) {
        dbLoader.saveToDatabase(cr);
    }

    @Override
    public ArrayList<CountRecord> retrieveRecords() {
        if(dbLoader.isDatabaseEmpty()){
            Log.d("TAG", "-------------------- DATABASE IS EMPTY ---------------------");
            return new ArrayList<>();
        }
        Log.d("TAG", "-------------------- DATABASE NOT EMPTY ---------------------");
        return (ArrayList<CountRecord>) dbLoader.queryRecordsInDatabase();
    }
}
