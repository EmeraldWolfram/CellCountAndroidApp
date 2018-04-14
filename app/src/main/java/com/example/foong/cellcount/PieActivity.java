package com.example.foong.cellcount;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import az.plainpie.PieView;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PieActivity extends AppCompatActivity {

    @BindView(R.id.pie) PieView pieView;
    @BindColor(R.color.colorAccent) int percentColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie);
        ButterKnife.bind(this);

        pieView.setPercentageBackgroundColor(percentColor);
        pieView.setInnerText("Viability:\n60%");
    }
}
