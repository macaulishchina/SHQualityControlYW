package com.example.sinoyd.frameapplication.KotlinFrame.Code.Adatper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.sinoyd.frameapplication.KotlinFrame.Code.db.FormTaskPicture;
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Adapter.BaseAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 作者： hyd
 * 创建时间： 2018/6/29
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Code.Adatper
 */

public class PictureAddAdapter extends BaseAdapter {

    private List<FormTaskPicture> mPictures;

    public PictureAddAdapter(@NotNull Context context, @NotNull List<FormTaskPicture> data) {
        super(context, data);
        mPictures = data;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }


}
