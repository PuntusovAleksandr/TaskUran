package com.aleksandrp.taskuran.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by AleksandrP on 28.12.2016.
 */

public class FileModel extends RealmObject {

    @PrimaryKey
    private long id;

    private String filename;

    private boolean isFolder;

    private long modDate;

    private String fileType;

    private boolean isOrange;

    private boolean isBlue;

    public FileModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long mId) {
        id = mId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String mFilename) {
        filename = mFilename;
    }

    public boolean isFolder() {
        return isFolder;
    }

    public void setFolder(boolean mFolder) {
        isFolder = mFolder;
    }

    public long getModDate() {
        return modDate;
    }

    public void setModDate(long mModDate) {
        modDate = mModDate;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String mFileType) {
        fileType = mFileType;
    }

    public boolean isOrange() {
        return isOrange;
    }

    public void setOrange(boolean mOrange) {
        isOrange = mOrange;
    }

    public boolean isBlue() {
        return isBlue;
    }

    public void setBlue(boolean mBlue) {
        isBlue = mBlue;
    }
}
