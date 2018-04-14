package com.example.foong.cellcount;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.foong.cellcount.database.DatabaseLoader;
import com.example.foong.cellcount.display.ButtonWrapper;
import com.example.foong.cellcount.display.PopUpException;
import com.example.foong.cellcount.display.PopUpManager;
import com.example.foong.cellcount.display.SwipeAnimator;
import com.example.foong.cellcount.interfacer.MainMvp;
import com.example.foong.cellcount.model.MainModel;
import com.example.foong.cellcount.presenter.MainPresenter;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainMvp.MvpView{

    @BindView(R.id.viableView)       TextView viableView;
    @BindView(R.id.nonViableView)    TextView nonViableView;
    @BindView(R.id.percent)          TextView percentView;
    @BindView(R.id.cellPerSq)        TextView cellPerSquareView;
    @BindView(R.id.dilutionFactor)   TextView dilutionFactorView;
    @BindView(R.id.concentration)    TextView concentrationView;
    @BindView(R.id.displayBar)       RelativeLayout dataTray;
    private MainMvp.MvpVPresenter taskPresenter;
    private PopUpManager popUpManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initMvp();
        initView();
    }

    @Override
    protected void onResume() {
        taskPresenter.onResume();
        super.onResume();
    }

    private void initMvp(){
        popUpManager            = new PopUpManager(this);
        MainPresenter presenter = new MainPresenter(this);
        MainModel model = new MainModel(presenter,
                PreferenceManager.getDefaultSharedPreferences(this), new DatabaseLoader(this));
        presenter.setTaskModel(model);
        taskPresenter           = presenter;
    }

    private void initView(){
        dataTray.setOnTouchListener(new SwipeAnimator(this, dataTray, this));
        viableView.setText("0");
        nonViableView.setText("0");
        percentView.setText("0 %");
        cellPerSquareView.setText("0");
        dilutionFactorView.setText("-");
        concentrationView.setText("-");
    }

    public void onViable(View view){
        taskPresenter.onViable();
    }

    public void onNonViable(View view){
        taskPresenter.onNonViable();
    }

    public void onSave(View view) {
        taskPresenter.onSave();
    }

    @Override
    public void setCellPerSquare(String cellPerSquare) {
        cellPerSquareView.setText(cellPerSquare);
    }

    @Override
    public void setConcentration(String concentration) {
        concentrationView.setText(concentration);
    }

    @Override
    public void setDilutionFactor(String dilutionFactor) {
        dilutionFactorView.setText(dilutionFactor);
    }

    @Override
    public void setNonViableCount(String count) {
        nonViableView.setText(count);
    }

    @Override
    public void setPercentage(String percent) {
        percentView.setText(percent);
    }

    @Override
    public void setViableCount(String count) {
        viableView.setText(count);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.action_settings:
                Intent setting = new Intent(this, SettingActivity.class);
                startActivity(setting);
                return true;
            case R.id.action_navigation:
                Intent navigate = new Intent(this, PieActivity.class);
                startActivity(navigate);
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSwipedLeft() {
        taskPresenter.onReset();
    }

    @Override
    public void onSwipedRight() {
        taskPresenter.onReset();
    }

    @Override
    public void displayMessage(PopUpException exc) {
        popUpManager.displayException(exc);
    }

    @Override
    public void promptInsertName() {
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(this);
        dialog.setTitle("Enter Sample Name:");
        final View view   = getLayoutInflater().inflate(R.layout.name_prompt, null);
        dialog.setView(view);
        dialog.setCancelable(true);
        dialog.setNeutralButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText input  = (EditText) view.findViewById(R.id.nameInput);
                        try{
                            String inputName    = input.getText().toString();
                            taskPresenter.onSave(inputName);
                        } catch (NullPointerException err){
                            taskPresenter.onSave(null);
                        }
                    }
                }
        );

        android.app.AlertDialog alert = dialog.create();
        alert.show();
    }
}
