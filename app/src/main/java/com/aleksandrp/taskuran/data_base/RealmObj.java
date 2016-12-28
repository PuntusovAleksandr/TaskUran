package com.aleksandrp.taskuran.data_base;

import android.content.Context;
import android.os.Handler;

import com.aleksandrp.taskuran.App;
import com.aleksandrp.taskuran.model.FileModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by AleksandrP on 28.12.2016.
 */

public class RealmObj {

    private static final long VERSION_DB = 1;
    private static RealmObj sRealmObj;
    private Context context;
    private Realm realm;


    public synchronized static RealmObj getInstance() {
        if (sRealmObj == null) {
            sRealmObj = new RealmObj();
        }
        return sRealmObj;
    }

    /**
     * for creating (or change) data base, need reopen Realm
     * This method need calling after save data in Shared preference
     */
    public static void stopRealm() {
        if (sRealmObj != null) {
            sRealmObj.closeRealm(App.getContext());
        }
    }

    private void closeRealm(Context context) {
        if (realm != null) {
            realm.close();
            realm = null;
            setRealmData(context);
        }
    }


    private RealmObj() {
        this.context = App.getContext();
        if (realm == null) {
            setRealmData(context);
        }
    }

    private void setRealmData(Context context) {
        String nameDB = RealmObj.class.getName();
        realm = Realm.getInstance(
                new RealmConfiguration.Builder(context)
                        .name(nameDB)
                        .deleteRealmIfMigrationNeeded()
                        .schemaVersion(VERSION_DB)
                        .build()
        );
    }

//    ===============================================================
//    START GET
//    ===============================================================

    public void getAllFiles(ListenerRealm mListener) {
        RealmResults<FileModel> all = realm.where(FileModel.class).findAll();
        List<FileModel> results = all;
        mListener.getAllFileModel(results);
    }

    private boolean firstStart = false;

    public void addFileModelFirstStart(final ListenerRealm mListener, final FileModel mModel) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(mModel);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                System.out.println("ok save");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                System.out.println("error " + error.getMessage());
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!firstStart) {
                    firstStart = true;
                    getAllFiles(mListener);
                }
            }
        }, 3000);
    }

//    ====================================================
//            interfaces
//    ====================================================

    public interface ListenerRealm {
        void getAllFileModel(List<FileModel> mResults);
    }


}
