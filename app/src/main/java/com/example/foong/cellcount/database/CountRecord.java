package com.example.foong.cellcount.database;

import java.util.Locale;

/**
 * Created by FOONG on 6/2/2017.
 */

public class CountRecord {

    private String recordName;
    private Integer viableCount;
    private Integer nonViableCount;
    private Integer numOfSquare;
    private Double dFactor;

    private static int recordNo = 0;

    public static final String CR_DB_ID         = "_id";
    public static final String CR_DB_NAME       = "Name";
    public static final String CR_DB_V_COUNT    = "VCount";
    public static final String CR_DB_NV_COUNT   = "NVCount";
    public static final String CR_DB_SQUARE_NUM = "NumOfSquare";
    public static final String CR_DB_D_FACTOR   = "DFactor";


    public CountRecord(){
        this.recordName      = String.format(Locale.ENGLISH, "Record %d", recordNo);
        this.viableCount     = 0;
        this.nonViableCount  = 0;
        this.numOfSquare     = 5;
        this.dFactor         = 0.0;
        CountRecord.recordNo++;
    }

    //====== Setter ================================================================================
    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public void setViableCount(Integer viableCount) {
        this.viableCount = viableCount;
    }

    public void setNonViableCount(Integer nonViableCount) {
        this.nonViableCount = nonViableCount;
    }

    public void setdFactor(Double dFactor) {
        this.dFactor = dFactor;
    }

    public void setNumOfSquare(Integer numOfSquare) {
        this.numOfSquare = numOfSquare;
    }


    //====== Getter ================================================================================
    public String getRecordName() {
        return recordName;
    }

    public Integer getViableCount() {
        return viableCount;
    }

    public Integer getNonViableCount() {
        return nonViableCount;
    }

    public Double getdFactor() {
        return dFactor;
    }

    public Integer getNumOfSquare() {
        return numOfSquare;
    }
}
