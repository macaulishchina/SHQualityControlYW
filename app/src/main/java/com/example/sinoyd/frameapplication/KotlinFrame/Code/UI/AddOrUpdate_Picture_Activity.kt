package com.example.sinoyd.frameapplication.KotlinFrame.Code.UI

import android.annotation.TargetApi
import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import com.example.sinoyd.frameapplication.KotlinFrame.Code.Adatper.PictureAddAdapter
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db.FormTask
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db.FormTaskPicture
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Action.FrmUploadAction
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.DateUtil
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.ToastUtil
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.View.FrmActionSheet
import com.example.sinoyd.frameapplication.KotlinFrame.UI.BaseActivity
import com.example.sinoyd.frameapplication.KotlinFrame.Uitl.FileUtil
import com.example.sinoyd.frameapplication.KotlinFrame.Uitl.FileUtil.getImagePath
import com.example.sinoyd.frameapplication.R
import com.example.sinoyd.frameapplication.R.id.*
import com.example.sinoyd.jiaxingywapplication.Myapplication
import com.sinoyd.environmentsz.Kotlin.getTime2String
import com.sinoyd.environmentsz.Kotlin.getToday
import kotlinx.android.synthetic.main.activity_add_picture.*
import kotlinx.android.synthetic.main.view_add_picture.*
import org.jetbrains.anko.onClick
import org.xutils.DbManager
import org.xutils.db.sqlite.WhereBuilder
import org.xutils.x
import java.io.File
import java.util.*

class AddOrUpdate_Picture_Activity : BaseActivity(), FrmActionSheet.MenuItemClickListener {

    companion object {
        val  PICTURE_CATE_1 = "站房环境"
        val  PICTURE_CATE_2 = "仪器数据"
        val  PICTURE_CATE_0 = "所有"
        const val PROVIDER_AUTHORITY = "com.example.sinoyd.frameapplication.fileprovider"
    }


    val mPictureTemplete: FormTaskPicture = FormTaskPicture()
    var mPictures: MutableList<FormTaskPicture>? = mutableListOf()
    var mTaskGuid: String? = null

    //数据库
    var myapplication: Myapplication = Myapplication()
    var db: DbManager? = null

    var file: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_picture)

        db = x.getDb(myapplication.getDaoConfig())

        mTaskGuid = intent.getStringExtra("RowGuid")

        if (mTaskGuid != null) {
            Log.i("hyd", "successfully launch AddOrUpdate_Picture_Activity with guid: ${intent.getStringExtra("RowGuid")}")
            initView()
        } else {
            ToastUtil.showShort(this, "错误：无法获取到任务")
            finish()
        }
    }

    private fun initView() {
        loadData()
        setOnClickListener()
        setView()
    }

    private fun loadData() {
        mPictures = db!!.selector(FormTaskPicture::class.java).where("TaskGuid", "=", mTaskGuid).findAll()
        if (mPictures == null) mPictures = mutableListOf()
        val formTask = db!!.selector(FormTask::class.java).where("RowGuid", "=", mTaskGuid).findFirst()
        if (formTask == null) {
            ToastUtil.showShort(this, "错误：无法获取到任务")
            finish()
        } else {
            mPictureTemplete.pointId = formTask.pointId
            mPictureTemplete.picture = null
            mPictureTemplete.cate = "未分类"
            mPictureTemplete.taskCode = formTask.taskCode
            mPictureTemplete.taskGuid = formTask.rowGuid
            mPictureTemplete.username = formTask.username
        }
    }

    private fun setView() {
        val pictures1 = mutableListOf<FormTaskPicture>()
        val pictures2 = mutableListOf<FormTaskPicture>()
        /**
         * 不同分类使用不同的适配器，依据cate区分
         */

        for(item in mPictures!!){
            when(item.cate){
                PICTURE_CATE_1 ->{
                    //Log.i("hyd","add picture ${item.localCachePath} to cate"+PICTURE_CATE_1)
                    pictures1.add(item)
                }
                PICTURE_CATE_2 ->{
                    //Log.i("hyd","add picture ${item.localCachePath} to cate"+PICTURE_CATE_2)
                    pictures2.add(item)
                }
                else ->{
                    //Log.i("hyd","add picture ${item.localCachePath} to cate none")
                }
            }
        }

        lv_picture_container_1.adapter = PictureAddAdapter(this,pictures1)
        lv_picture_container_2.adapter = PictureAddAdapter(this,pictures2)
    }


    private fun setOnClickListener() {
        tv_save.onClick {
            try {
                savePictures()
                ToastUtil.showShort(this, "保存成功")
                Log.i("hyd", "保存图片成功")
            } catch (e: Exception) {
                ToastUtil.showShort(this, "保存失败")
                Log.i("hyd", "保存图片失败")
            }
        }
        tv_exit.onClick {
            tv_save.performClick()
            finish()
        }
        /**
         * 硬编码：以布局为依据，只有两个分类
         */
        btn_add_1.onClick {
            launchAddPictureEvent(PICTURE_CATE_1)
        }
        btn_add_2.onClick {
            launchAddPictureEvent(PICTURE_CATE_2)
        }

    }

    private fun launchAddPictureEvent(picCate: String) {
        mPictureTemplete.cate = picCate
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
                FrmUploadAction.openPic(this, "image/jpeg")
            1 -> {
                //拍照
                file = File(externalCacheDir, "images"+File.separator+DateUtil.convertDate(Date(), "yyyyMMddHHmmss") + ".jpg")
                FrmUploadAction.openCamera(this, file,PROVIDER_AUTHORITY)
            }
            else -> {
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("hyd","resultCode$resultCode,resultDate=${data!!.data}")
        when (requestCode) {
            FrmUploadAction.OpenCamera_REQUESTCODE ->
                if (resultCode == Activity.RESULT_OK) {
                    addPicture(file!!.absolutePath)
                    Log.i("hyd","从相机获得照片：完整路径为=${file!!.absolutePath}")
                }
            FrmUploadAction.OpenPhoto_REQUESTCODE ->
                if (resultCode == Activity.RESULT_OK) {
                    val path = getImagePath(data!!)
                    addPicture(path)
                    Log.i("hyd","从相册获得照片：完整路径为=$path")
                }
        }
        setView()
    }

    /**
     * 保存图片
     */
    fun savePictures(){
        db!!.delete(FormTaskPicture::class.java, WhereBuilder.b("TaskGuid","=",mTaskGuid))
        for(item in mPictures!!){
            item.rowGuid = UUID.randomUUID().toString()
            item.takeTime = Date().getToday("yyyy/MM/dd HH:mm:ss")
        }
        db!!.saveOrUpdate(mPictures)
    }


    /**
     * 删除图片并刷新
     */
    fun deletePicture(path :String?){
        if (TextUtils.isEmpty(path)){
            ToastUtil.showShort(this,"删除图片错误->文件不存在")
        }else{
            val it = mPictures!!.iterator()
            while (it.hasNext()){
                val temp = it.next()
                if(temp.localCachePath == path){
                    it.remove()
                }
            }
        }
        setView()
    }

    /**
     * 添加图片并刷新
     * 依据路径进行重复检查
     */
    fun addPicture(path: String?) {
        if(TextUtils.isEmpty(path)){
            ToastUtil.showShort(this,"添加图片失败->文件不存在")
        }else if(!path!!.toLowerCase().endsWith(".jpeg") && !path.toLowerCase().endsWith(".jpg")){
            ToastUtil.showShort(this,"文件类型非法")
        }else {
            /*
            检查是否重复添加图片
             */
            var exists = false
            for(item in mPictures!!){
                if(item.localCachePath == path) {
                    exists = true
                    break
                }
            }
            if(exists){
                ToastUtil.showShort(this,"您已经添加这张图片")
            }else {
                val picture = FormTaskPicture()
                picture.cate = mPictureTemplete.cate
                picture.localCachePath = path
                picture.rowGuid = UUID.randomUUID().toString()
                picture.pointId = mPictureTemplete.pointId
                picture.taskCode = mPictureTemplete.taskCode
                picture.username = mPictureTemplete.username
                picture.taskGuid = mPictureTemplete.taskGuid
                this.mPictures!!.add(picture)
            }
        }
        setView()
    }

    /**
     * 从data中解析出图片路径
     * 例子：file:///storage/emulated/0/DCIM/Camera/1528269552327.jpg
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun getImagePath(data: Intent):String?{
        val uri = data.data
        var path:String? = null
        if (Build.VERSION.SDK_INT > 19) {
            //4.4以上系统使用这个方法处理图片
            path = FileUtil.getImagePath(this, uri, null)
            path = "file://$path"

        } else if (Build.VERSION.SDK_INT == 19){
            path = data.data.toString()
        }

        return path
    }

    /**
     * 压缩图片到 size KB以下大小
     */
    private fun compressPicture(size: Int) {
//        top.zibin.luban.Luban.with(this)
//                .filter { path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif")) }
//                .ignoreBy(size)
//                .setCompressListener(object : OnCompressListener {
//                    override fun onSuccess(file: File?) {
//
//                    }
//
//                    override fun onError(e: Throwable?) {
//                    }
//
//                    override fun onStart() {
//                    }
//
//                })
//                .launch()
    }


}
