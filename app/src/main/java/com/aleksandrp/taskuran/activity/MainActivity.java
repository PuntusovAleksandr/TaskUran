package com.aleksandrp.taskuran.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.aleksandrp.taskuran.R;
import com.aleksandrp.taskuran.adapter.RecyclerFileAdapter;
import com.aleksandrp.taskuran.data_base.RealmObj;
import com.aleksandrp.taskuran.dialog.DialogActions;
import com.aleksandrp.taskuran.model.FileModel;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.aleksandrp.taskuran.App.getContext;
import static com.aleksandrp.taskuran.utils.FileType.ETC;
import static com.aleksandrp.taskuran.utils.FileType.IMAGE;
import static com.aleksandrp.taskuran.utils.FileType.MOVIE;
import static com.aleksandrp.taskuran.utils.FileType.MUSIC;
import static com.aleksandrp.taskuran.utils.FileType.PDF;

public class MainActivity extends AppCompatActivity implements
        RecyclerFileAdapter.ListenerAdapter,
        DialogActions.ActionDialogListener,
        RealmObj.ListenerRealm {

    @Bind(R.id.rv_list_files)
    RecyclerView mRecyclerView;

    private RecyclerFileAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerFileAdapter(this);
        mRecyclerView.setAdapter(adapter);

        RealmObj.getInstance().getAllFiles(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void updateAdapter(List<FileModel> mModels) {
        mRecyclerView.setAdapter(adapter);
        adapter.addAll(mModels);
    }

    // for firs start
    private void makeFirstStart() {
        for (int i = 1; i < 10; i++) {
            FileModel model = new FileModel();
            model.setId(i);
            model.setFilename("Filename " + (i + 1));
            model.setFolder(i % 3 == 0 ? true : false);
            model.setModDate(new Date().getTime() + 1000);
            model.setOrange(i % 2 == 0 ? true : false);
            model.setBlue(i % 3 == 0 ? true : false);
            model.setFileType(createFileType(i));
            RealmObj.getInstance().addFileModelFirstStart(this, model);
        }
    }

    private String createFileType(int mI) {
        switch (mI % 7) {
            case 0:
                return IMAGE;
            case 1:
                return PDF;
            case 2:
                return MOVIE;
            case 3:
                return MUSIC;
            default:
                return ETC;
        }
    }


//    ================================================
//            from ListenerAdapter
//    ================================================

    @Override
    public void clickLong(long mId) {
        new DialogActions(this).show();
    }

    @Override
    public void clickShort(boolean mIsFolder) {
        if (mIsFolder) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else Toast.makeText(this, "It is file!", Toast.LENGTH_SHORT).show();
    }

//    ================================================
//            from ActionDialogListener
//    ================================================

    @Override
    public void action(String textAction) {
        Toast.makeText(this, textAction, Toast.LENGTH_SHORT).show();
    }

    //    ================================================
//            from ListenerRealm
//    ================================================
    @Override
    public void getAllFileModel(List<FileModel> mResults) {
        if (mResults.size() > 0) {
            updateAdapter(mResults);
        } else {
            makeFirstStart();
        }
    }

}
