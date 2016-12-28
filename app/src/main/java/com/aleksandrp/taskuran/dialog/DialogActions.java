package com.aleksandrp.taskuran.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.aleksandrp.taskuran.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by AleksandrP on 28.12.2016.
 */

public class DialogActions extends AlertDialog {

    private Context mContext;
    private ActionDialogListener mListener;

    public DialogActions(Context mContext) {
        super(mContext);
        if (mContext instanceof ActionDialogListener) {
            mListener = (ActionDialogListener) mContext;
        }
        this.mContext = mContext;

        // Init dialog view
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_action, null);
        ButterKnife.bind(this, view);


        this.setView(view);
    }


    @OnClick(R.id.bt_favorite)
    public void favorit() {
        sendMess("Favorite");
    }

    @OnClick(R.id.bt_permalink)
    public void permalink() {
        sendMess("Permalink");
    }

    @OnClick(R.id.bt_delete)
    public void delete() {
        sendMess("Delete");
    }

    private void sendMess(String mFavorite) {
        if (mListener != null) {
            mListener.action(mFavorite);
        }
        dismiss();
    }

    public interface ActionDialogListener {
        void action(String textAction);
    }
}
