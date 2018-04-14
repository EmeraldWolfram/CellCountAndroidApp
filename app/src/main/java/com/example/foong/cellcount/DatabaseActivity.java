package com.example.foong.cellcount;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foong.cellcount.database.DatabaseLoader;
import com.example.foong.cellcount.display.PopUpException;
import com.example.foong.cellcount.display.PopUpManager;
import com.example.foong.cellcount.display.RecordViewHolder;
import com.example.foong.cellcount.interfacer.DatabaseMvp;
import com.example.foong.cellcount.model.DataModel;
import com.example.foong.cellcount.presenter.DataPresenter;

import butterknife.BindBitmap;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DatabaseActivity extends AppCompatActivity implements DatabaseMvp.MvpView{

    @BindView(R.id.recordsListView)             RecyclerView recordsView;
    @BindView(R.id.newButton)                   FloatingActionButton newButton;
    @BindBitmap(R.drawable.other_trash_icon)    Bitmap trashIcon;

    private DatabaseAdapter adapter;
    private PopUpManager popUpManager;
    private DatabaseMvp.MvpVPresenter taskPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        ButterKnife.bind(this);
        initMVP();
        initView();
    }

    @Override
    protected void onResume(){
        taskPresenter.onResume();
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        taskPresenter.onPause();
    }

    private void initMVP(){
        popUpManager                = new PopUpManager(this);
        DataPresenter presenter     = new DataPresenter(this, trashIcon);
        DataModel model             = new DataModel(presenter, new DatabaseLoader(this));
        presenter.setTaskModel(model);
        taskPresenter               = presenter;
    }

    private void initView(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter         = new DatabaseAdapter();
        ItemTouchHelper.SimpleCallback callback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
                    @Override
                    public boolean onMove(RecyclerView              recyclerView,
                                          RecyclerView.ViewHolder   viewHolder,
                                          RecyclerView.ViewHolder   target) {
                        return taskPresenter.onMove(recyclerView, viewHolder, target);
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        taskPresenter.onSwiped(newButton, viewHolder, direction);
                    }

                    @Override
                    public void onChildDraw(Canvas canvas, RecyclerView rccView,
                                            RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                            int actionState, boolean isActive) {
                        taskPresenter.onChildDraw(canvas, rccView, viewHolder, dX, dY, actionState, isActive);
                        super.onChildDraw(canvas, rccView, viewHolder, dX, dY, actionState, isActive);
                    }
                };

        recordsView.setHasFixedSize(true);
        recordsView.setLayoutManager(new LinearLayoutManager(this));
        recordsView.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recordsView);

    }


    @Override
    public void onNewRecord(View view) {
        taskPresenter.onNewRecordPressed();
    }

    @Override
    public void displayMessage(PopUpException exc) {
        popUpManager.displayException(exc);
    }

    private class DatabaseAdapter extends RecyclerView.Adapter<RecordViewHolder> {

        @Override
        public RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            View v = LayoutInflater.from(context).inflate(R.layout.record_view, parent, false);
            return new RecordViewHolder(v);
        }

        @Override
        public void onBindViewHolder(RecordViewHolder holder, int position) {
            taskPresenter.onBindViewHolder(holder, position);
        }

        @Override
        public int getItemCount() {
            return taskPresenter.getItemCount();
        }
    }

    @Override
    public void removeRecord(int position, int newSize) {
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, newSize);
    }

    @Override
    public void insertRecord(int position) {
        adapter.notifyItemInserted(position);
    }

    @Override
    public void navActivity(Class<?> cls) {
        Intent next = new Intent(this, MainActivity.class);
        startActivity(next);
    }
}
