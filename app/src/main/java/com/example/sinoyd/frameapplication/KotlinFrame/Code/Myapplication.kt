package com.example.sinoyd.jiaxingywapplication

import android.app.Application
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.SystemUtil
import org.xutils.x
import org.xutils.DbManager
import java.io.File


/**
 * Created by Sinoyd on 2018/1/4.
 */
class Myapplication : Application() {

    private var daoConfig: DbManager.DaoConfig? = null
    fun getDaoConfig(): DbManager.DaoConfig? {
        return daoConfig
    }


    var db: DbManager? = null
    override fun onCreate() {
        super.onCreate()
        x.Ext.init(this)
        x.Ext.setDebug(false) // 是否输出debug日志，开启debug会影响性能
        //初始化Sqlite
        ininsqlit()
        //初始化百度地图
//        SDKInitializer.initialize(this)



    }


    private fun ininsqlit() {
        daoConfig = DbManager.DaoConfig()
                .setDbName("sh12.db")//设置数据库的名称，默认是xutils.db
                .setAllowTransaction(true)//设置是否允许事务，默认true
                .setDbDir(File("/sdcard")) // 设置数据库的存放路径, 默认存储在app的私有目录(数据库默认存储在/data/data/你的应用程序/database/xxx.db)
                .setDbVersion(1)//设置数据库的版本号
                //设置数据库打开的监听
                .setDbOpenListener { db ->
                    // 开启WAL, 对写入加速提升巨大
                    db.database.enableWriteAheadLogging()
                }
                //设置数据库更新的监听
                .setDbUpgradeListener { db, oldVersion, newVersion ->
                    // TODO: ...
                    // db.addColumn(...);
                    // db.dropTable(...);
                    // ...
                    // or
                    // db.dropDb();
                }
    }

}