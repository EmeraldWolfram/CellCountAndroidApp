package com.example.foong.cellcount.presenter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.foong.cellcount.MainActivity;
import com.example.foong.cellcount.database.CountRecord;
import com.example.foong.cellcount.display.RecordViewHolder;
import com.example.foong.cellcount.interfacer.DatabaseMvp;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by FOONG on 7/2/2017.
 */

public class DataPresenter implements DatabaseMvp.MvpMPresenter, DatabaseMvp.MvpVPresenter{

    private Paint p;
    private Bitmap deleteIcon;
    private DatabaseMvp.MvpView taskView;
    private DatabaseMvp.MvpModel taskModel;
    private CountRecord tempRecord;
    private int tempPosition;
    private Snackbar snackbar;

    private ArrayList<CountRecord> records;

    public DataPresenter(DatabaseMvp.MvpView taskView, Bitmap icon){
        this.p          = new Paint();
        this.deleteIcon = icon;
        this.taskView   = taskView;
    }

    public void setTaskModel(DatabaseMvp.MvpModel taskModel) {
        this.taskModel = taskModel;
    }

    @Override
    public void onResume() {
        this.records    = taskModel.retrieveRecords();
    }

    @Override
    public void onPause() {
        if(snackbar != null)
            snackbar.dismiss();
    }

    @Override
    public void onNewRecordPressed() {
        taskView.navActivity(MainActivity.class);
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    @Override
    public void onBindViewHolder(RecordViewHolder holder, int position) {
        CountRecord record  = records.get(position);
        Integer viableCount = record.getViableCount();
        Integer nonViaCount = record.getNonViableCount();
        Integer numOfSquare = record.getNumOfSquare();
        Double dFactor      = record.getdFactor();

        double viability    = (double)viableCount / (double) (viableCount + nonViaCount);
        double cellPSq      = (double)viableCount / (double) numOfSquare;
        double concentration= (double) viableCount * dFactor * 10000 / (double) numOfSquare;

        holder.setName(record.getRecordName());
        holder.setViability(Double.valueOf(viability).toString());
        holder.setCellPerSquare(Double.valueOf(cellPSq).toString());
        holder.setdFactor(dFactor.toString());
        holder.setConcentration(Double.valueOf(concentration).toString());
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

            View itemView = viewHolder.itemView;
            float height = (float) itemView.getBottom() - (float) itemView.getTop();
            float width = height / 3;

            if(dX < 0){
                p.setColor(Color.parseColor("#D32F2F"));
                RectF background = new RectF(
                        (float) itemView.getRight() + dX,
                        (float) itemView.getTop(),
                        (float) itemView.getRight(),
                        (float) itemView.getBottom());
                RectF icon_dest = new RectF(
                        (float) itemView.getRight()  - 2*width,
                        (float) itemView.getTop()    + width,
                        (float) itemView.getRight()  - width,
                        (float) itemView.getBottom() - width);
                canvas.drawRect(background,p);
                canvas.drawBitmap(deleteIcon, null, icon_dest, p);
            }
        }
    }

    @Override
    public void onSwiped(View refView, RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        if (direction == ItemTouchHelper.LEFT){
            tempRecord   = records.remove(position);
            taskView.removeRecord(position, records.size());
            taskModel.removeItem(tempRecord);
            tempPosition    = position;
            String msg  = String.format(Locale.ENGLISH, "%s is removed",
                    tempRecord.getRecordName());

                snackbar = Snackbar.make(refView, msg, Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("UNDO", this);
                snackbar.show();
        }
    }

    @Override
    public void onClick(View v) {
        taskModel.addItem(tempRecord);
        records.add(tempPosition, tempRecord);
        taskView.insertRecord(tempPosition);
    }
}
