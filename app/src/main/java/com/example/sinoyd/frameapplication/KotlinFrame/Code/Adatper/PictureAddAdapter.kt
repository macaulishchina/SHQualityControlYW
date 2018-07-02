package com.example.sinoyd.frameapplication.KotlinFrame.Code.Adatper

import android.view.View
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import com.example.sinoyd.frameapplication.KotlinFrame.Code.UI.AddOrUpdate_Picture_Activity
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db.FormTaskPicture
import com.example.sinoyd.frameapplication.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_add_picture.view.*
import org.jetbrains.anko.imageBitmap
import org.jetbrains.anko.onClick
import java.io.File

class PictureAddAdapter(var context: Context,var pictures: MutableList<FormTaskPicture>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: PictureAddAdapter.ViewHolder?
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_add_picture, null)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as PictureAddAdapter.ViewHolder
        }
//        val file = File(pictures[position].localCachePath)
//        if(file.exists()){
//            Log.i("hyd","${file.absolutePath} 存在")
//        }else{
//            Log.i("hyd","${file.absolutePath} 不存在")
//        }
//        holder.imageView.imageBitmap =
//                BitmapFactory.decodeFile(pictures[position].localCachePath)
        Picasso.get().load(pictures[position].localCachePath)
                .placeholder(R.drawable.imageview_holder)
                .error(R.drawable.imageview_error)
                .fit().centerCrop()
                .into(holder.imageView)

        holder.btnDelete.onClick {
            (context as AddOrUpdate_Picture_Activity).deletePicture(pictures[position].localCachePath)
            Log.i("hyd","删除：位置=$position,path=${pictures[position].localCachePath},cate=${pictures[position].cate}")
        }
        holder.imageView.onClick {
            Log.i("hyd","查看：位置=$position,path=${pictures[position].localCachePath},cate=${pictures[position].cate}")
        }
        return view
    }

    override fun getItem(position: Int): Any = pictures[position]

    override fun getItemId(position: Int): Long = pictures[position].id.toLong()

    override fun getCount(): Int = pictures.size

    internal inner class ViewHolder(view: View) {

        var imageView = ImageView(context)
        var btnDelete = Button(context)

        init {
            imageView = view.item_add_picture_image
            btnDelete = view.item_add_picture_btn

        }
    }
}