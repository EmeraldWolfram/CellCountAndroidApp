package com.example.foong.cellcount.interfacer;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.foong.cellcount.database.CountRecord;
import com.example.foong.cellcount.display.RecordViewHolder;

import java.util.ArrayList;

/**
 * Created by FOONG on 7/2/2017.
 */

public interface DatabaseMvp {
    interface MvpView extends ActivityView {
        void onNewRecord(View view);
        void navActivity(Class<?> cls);
        void removeRecord(int position, int newSize);
        void insertRecord(int position);
    }

    interface MvpVPresenter extends View.OnClickListener{
        void onNewRecordPressed();
        void onResume();
        void onPause();

        void onBindViewHolder(RecordViewHolder holder, int position);
        int getItemCount();
        boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                       RecyclerView.ViewHolder target);
        void onSwiped(View refView, RecyclerView.ViewHolder viewHolder, int direction);
        void onChildDraw(Canvas canvas, RecyclerView recyclerView,
                         RecyclerView.ViewHolder viewHolder, float dX, float dY,
                         int actionState, boolean isCurrentlyActive);
    }

    interface MvpMPresenter {}

    interface MvpModel {
        void addItem(CountRecord cr);
        void removeItem(CountRecord cr);
        ArrayList<CountRecord> retrieveRecords();
    }
}
