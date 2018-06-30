package com.example.sinoyd.frameapplication.KotlinFrame.Code.UI

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.sinoyd.frameapplication.KotlinFrame.Code.Adatper.PictureAddAdapter
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db.FormTaskPicture
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Action.FrmUploadAction
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Mode.FrmAttachments
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.DateUtil
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.LogUtil
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.ToastUtil
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.View.FrmActionSheet
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.View.HorizontalListView
import com.example.sinoyd.frameapplication.KotlinFrame.UI.BaseActivity
import com.example.sinoyd.frameapplication.R
import com.example.sinoyd.frameapplication.R.id.lv_picture_container_1
import com.example.sinoyd.jiaxingywapplication.Myapplication
import kotlinx.android.synthetic.main.activity_add_picture.*
import kotlinx.android.synthetic.main.view_add_picture.*
import org.jetbrains.anko.find
import org.jetbrains.anko.onClick
import org.xutils.DbManager
import org.xutils.x
import java.io.File
import java.io.FileOutputStream
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList


class AddOrUpdate_Picture_Activity : BaseActivity(),FrmActionSheet.MenuItemClickListener{

    var mPicAdapter: PictureAddAdapter? = null
    var mPictures: List<FormTaskPicture>? = null
    val mButtonAddPic: Button? = null
    var mTaskGuid:String? = null
    //数据库
    var myapplication: Myapplication = Myapplication()
    var db: DbManager? = null

    var file: File? = null
    var files: MutableList<FrmAttachments>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_picture)
        
        db = x.getDb(myapplication.getDaoConfig())
        
        mTaskGuid = intent.getStringExtra("rowGuid")

        if(mTaskGuid != null) {
            Log.i("hyd","successfully launch AddOrUpdate_Picture_Activity with guid: ${intent.getStringExtra("rowGuid")}")
            initView()
        }else{
            ToastUtil.showShort(this,"错误：无法获取到任务")
            finish()
        }
    }
    
    private fun initView(){
        loadData()
        setOnClickListener()
        setView()
    }
    
    private fun loadData(){
        mPictures = db!!.selector(FormTaskPicture::class.java).where("RowGuid", "=", mTaskGuid).findAll()
        if(mPictures == null) mPictures = ArrayList()
    }

    private fun setView(){
        mPicAdapter = PictureAddAdapter(this, mPictures!!)
        lv_picture_container_1.adapter = mPicAdapter
        lv_picture_container_2.adapter = mPicAdapter
    }



    private fun setOnClickListener(){
        tv_save.onClick {
            try{
                db!!.saveOrUpdate(mPictures)
                ToastUtil.showShort(this,"保存成功")
                Log.i("hyd","保存图片成功")
            }catch (e:Exception){
                ToastUtil.showShort(this,"保存失败")
                Log.i("hyd","保存图片失败")
            }
        }
        tv_exit.onClick { 
            tv_save.performClick()
            finish()
        }
        /**
         * 硬编码：以布局为依据，只有两个分类，分类名称保存到按钮的tag信息中
         */
        btn_add_1.onClick {
            launchAddPictureEvent(btn_add_1.tag.toString())
        }
        btn_add_2.onClick {
            launchAddPictureEvent(btn_add_2.tag.toString())
        }

    }

    private fun launchAddPictureEvent(picCate : String){
        val menuView = FrmActionSheet(this)
        menuView.setCancelButtonTitle("取消")
        menuView.addItems("本地相册", "拍照")
        menuView.setItemClickListener(this)
        menuView.setCancelableOnTouchMenuOutside(true)
        menuView.showMenu()
    }

    override fun onItemClick(index: Int) {
        when (index) {
            0 ->
                //ToastUtil.showShort(this,"打开本地相册")
                FrmUploadAction.openPic(this)
            1 -> {
                ToastUtil.showShort(this,"打开相机")
                //拍照
                //file = File(folderPath, DateUtil.convertDate(Date(), "yyyyMMddHHmmss") + ".jpg")
                //FrmUploadAction.openCamera(this, file)
            }
            2 -> {
                ToastUtil.showShort(this,"测试")

                //
            }
            else -> {
            }
        }
    }

    //添加附件
    private fun addfile(path: String) {
        file = File(path)
        if (file.exists()) {
            val fileSize = FrmUploadAction.getFileSize(file)//b
            if (fileSize <= 10 * 1024 * 1024) {
                val mtemp = FrmAttachments()
                mtemp.AttachPath = path
                mtemp.AttFileName = file.getName()
                val time = DateUtil.convertDate(Date(file.lastModified()), "yyyy-MM-dd HH:mm:ss")
                mtemp.FileDate = time
                mtemp.DateTime = time
                val b1 = BigDecimal(fileSize)
                val b2 = BigDecimal(1024 * 1024)
                mtemp.size = b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).toDouble().toString() + "MB"
                files.add(mtemp)
                madapter.notifyDataSetChanged()
            } else {
                ToastUtil.showShort(getContext(), "文件大小不可超过10M!")
            }
        } else {
            ToastUtil.showShort(getContext(), "您选择的文件不存在!")
        }
    }

    /**
     * 对图片进行压缩
     * @param srcPath
     * @return
     */
    fun decodeFile(srcPath: String): String {
        val options = BitmapFactory.Options()
        // 获取这个图片的宽和高
        options.inJustDecodeBounds = true
        var bitmap = BitmapFactory.decodeFile(srcPath, options) //此时返回bm为空

        var be = options.outWidth / 720
        if (be < 1) {
            be = 1
        }
        options.inJustDecodeBounds = false
        options.inSampleSize = be

        bitmap = BitmapFactory.decodeFile(srcPath, options)
        val photoName = DateUtil.convertDate(Date(), "yyyyMMddHHmss") + "s.jpg"
        val sfullPath = folderPath + "/" + photoName
        val sfullFile = File(sfullPath)

        try {
            val out = FileOutputStream(sfullFile)
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)) {
                out.flush()
                out.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            //删除拍摄照片
            //IOHelp.deleteFile(new File(srcPath));
        }

        return sfullPath
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            FrmUploadAction.OpenCamera_REQUESTCODE -> if (resultCode == Activity.RESULT_OK) {
                addfile(decodeFile(file.getAbsolutePath()))
            }
            FrmUploadAction.OpenPhoto_REQUESTCODE -> if (resultCode == Activity.RESULT_OK) {
                val uri = data.getData()
                val proj = arrayOf(MediaStore.MediaColumns.DATA)
                val cursor = managedQuery(uri, proj, null, null, null)
                if (cursor != null) {
                    val column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
                    if (cursor.count > 0 && cursor.moveToFirst()) {
                        val path = cursor.getString(column_index)
                        addfile(decodeFile(path))
                    }
                }
            }
        }
    }
}
