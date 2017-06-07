package com.wmk.wb.model.bean;

import android.view.View;

import java.util.ArrayList;

/**
 * Created by wmk on 2017/5/19.
 */

public class Pic_List_Info {
    public int position;
    public ArrayList<String> larg_url;
    public View view;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ArrayList<String> getLarg_url() {
        return larg_url;
    }

    public void setLarg_url(ArrayList<String> larg_url) {
        this.larg_url = larg_url;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
