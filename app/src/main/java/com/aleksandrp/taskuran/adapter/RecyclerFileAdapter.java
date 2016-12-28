package com.aleksandrp.taskuran.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aleksandrp.taskuran.R;
import com.aleksandrp.taskuran.model.FileModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.aleksandrp.taskuran.utils.FileType.ETC;
import static com.aleksandrp.taskuran.utils.FileType.IMAGE;
import static com.aleksandrp.taskuran.utils.FileType.MOVIE;
import static com.aleksandrp.taskuran.utils.FileType.MUSIC;
import static com.aleksandrp.taskuran.utils.FileType.PDF;

/**
 * Created by AleksandrP on 28.12.2016.
 */

public class RecyclerFileAdapter extends RecyclerView.Adapter<RecyclerFileAdapter.ViewHolder> {

    private List<FileModel> mFileModels;
    private Context mContext;
    private ListenerAdapter mListenerAdapter;

    public RecyclerFileAdapter(Context mContext) {

        this.mContext = mContext;
        mFileModels = new ArrayList<>();
        if (mContext instanceof ListenerAdapter) {
            mListenerAdapter = (ListenerAdapter) mContext;
        }
    }

    public void addAll(List<FileModel> tempLocationList) {
        removeAll();
        mFileModels.addAll(tempLocationList);
        notifyDataSetChanged();
    }

    public void removeAll() {
        mFileModels.clear();
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerFileAdapter.ViewHolder holder, int position) {
        if (mFileModels.size() > 0) {
            final FileModel model = mFileModels.get(position);

            SimpleDateFormat format = new SimpleDateFormat("MMM d, yyyy");
            String date = mContext.getString(R.string.modified) +" " +
                    format.format(new Date(model.getModDate()));
            holder.tv_title.setText(model.getFilename());
            holder.tv_time.setText(date);

            int blue, orange, folder, resIcon = R.drawable.ic_ets;

            switch (model.getFileType()) {
                case IMAGE:
                    resIcon = R.drawable.ic_file;
                    break;
                case PDF:
                    resIcon = R.drawable.ic_folder_;
                    break;
                case MOVIE:
                    resIcon = R.drawable.ic_movie;
                    break;
                case MUSIC:
                    resIcon = R.drawable.ic_terrain;
                    break;
                case ETC:
                    resIcon = R.drawable.ic_ets;
                    break;
            }

            if (model.isFolder()) {
                folder = View.VISIBLE;
            } else {
                folder = View.GONE;
            }

            if (model.isBlue()) {
                blue = View.VISIBLE;
            } else {
                blue = View.GONE;
            }
            if (model.isOrange()) {
                orange = View.VISIBLE;
            } else {
                orange = View.GONE;
            }

            holder.view_short.setVisibility(folder);
            holder.view_blue.setVisibility(blue);
            holder.view_orange.setVisibility(orange);

            holder.iv_image.setImageResource(resIcon);

            holder.ll_item_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListenerAdapter != null) {
                        mListenerAdapter.clickShort(model.isFolder());
                    }
                }
            });

            holder.ll_item_main.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mListenerAdapter != null) {
                        mListenerAdapter.clickLong(model.getId());
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (mFileModels != null && !mFileModels.isEmpty()) {
            size = mFileModels.size();
        }
        return size;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.ll_item_main)
        LinearLayout ll_item_main;

        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_time)
        TextView tv_time;

        @Bind(R.id.iv_image)
        ImageView iv_image;

        @Bind(R.id.view_bar_indicator_blue)
        View view_blue;
        @Bind(R.id.view_bar_indicator_orange)
        View view_orange;
        @Bind(R.id.view_bar_indicator_short)
        View view_short;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface ListenerAdapter {
        void clickLong(long mId);

        void clickShort(boolean mIsFolder);
    }

}
