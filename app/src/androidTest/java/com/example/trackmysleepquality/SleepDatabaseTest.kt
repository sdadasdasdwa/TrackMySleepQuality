package com.example.trackmysleepquality

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.trackmysleepquality.databse.SleepDatabase
import com.example.trackmysleepquality.databse.SleepDatabaseDao
import com.example.trackmysleepquality.databse.SleepNight
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

//@RunWith 注解用于标识测试运行程序，即设置和执行测试的程序。
@RunWith(AndroidJUnit4::class)
class SleepDatabaseTest {

    private lateinit var sleepDao : SleepDatabaseDao
    private lateinit var db : SleepDatabase

    //系统会执行带有 @Before 注解的函数，这个函数会使用 SleepDatabaseDao 创建一个内存中 SleepDatabase。
    //“内存中”表示此数据库不会保存到文件系统中，在测试运行完毕后会被删除。
    @Before
    fun createDb(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears
        // when the process is killed
        //在构建内存中数据库时，代码会调用另一个测试专用方法 allowMainThreadQueries。默认情况下，
        // 如果您尝试在主线程上运行查询，会遇到错误。此方法可让您在主线程上运行测试，这个操作只应在测试期间执行。
        db = Room.inMemoryDatabaseBuilder(context, SleepDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        sleepDao = db.sleepDatabase
    }

    //测试完成后，系统会执行带有 @After 注解的函数，以关闭数据库。
    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }

    //在带有 @Test 注解的测试方法中，您可以创建、插入和检索 SleepNight，还可以断言它们是相同的。
    @Test
    @Throws(Exception::class)
    fun insertAndGetNight(){
        val night = SleepNight()
        sleepDao.insert(night)
        val tonight = sleepDao.getTonight()
        assertEquals(tonight?.sleepQuality,-1)
    }


}