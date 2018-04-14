package com.example.foong.cellcount.display;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.foong.cellcount.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by FOONG on 6/2/2017.
 */

public class RecordViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.recordName)          TextView name;
    @BindView(R.id.recordViability)     TextView viability;
    @BindView(R.id.recordCellPerSquare) TextView cellPerSquare;
    @BindView(R.id.recordDilFactor)     TextView dFactor;
    @BindView(R.id.recordConcentration) TextView concentration;


    public RecordViewHolder(View parent){
        super(parent);
        ButterKnife.bind(this, parent);
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setViability(String viability) {
        this.viability.setText(viability + " %");
    }

    public void setCellPerSquare(String cellPerSquare) {
        this.cellPerSquare.setText(cellPerSquare + " cells/sq");
    }

    public void setdFactor(String dFactor) {
        this.dFactor.setText(dFactor);
    }

    public void setConcentration(String concentration) {
        this.concentration.setText(concentration + " viable cells/ml");
    }
}
