package com.example.foong.cellcount.display;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foong.cellcount.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (C) 2016 - 2017 Steven Foong Ghin Yew <stevenfgy@yahoo.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

public class CustomToast {
    private Toast message;
    private Context context;
    private View toastView;

    @BindView(R.id.toastTxt) TextView msgView;
    @BindView(R.id.toastImg) ImageView imgView;


    public CustomToast(Context context){
        this.context    = context;
        LayoutInflater toastInflater = LayoutInflater.from(context);
        toastView       = toastInflater.inflate(R.layout.message_toast_view, null);
        //Typeface setting can be added here
        ButterKnife.bind(toastView);
    }

    public void showMessage(String textMessage, int icon){
        if(message != null){
            message.cancel();
        }
        message = new Toast(context);
        message.setDuration(Toast.LENGTH_LONG);
        imgView.setImageResource(icon);
        msgView.setText(textMessage);
        message.setView(toastView);
        message.show();
    }
}
