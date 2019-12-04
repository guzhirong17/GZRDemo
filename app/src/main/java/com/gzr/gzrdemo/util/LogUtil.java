package com.gzr.gzrdemo.util;

import android.os.Environment;

import com.gzr.gzrdemo.BuildConfig;

import de.mindpipe.android.logging.log4j.LogConfigurator;
import org.apache.log4j.Logger;

import java.io.File;

public class LogUtil {

    private Class mLogClass;
    private String mTag;
    public static boolean isWritePermissions = true;
    // 保存log的根目录
    public final static String ROOT = Environment.getExternalStorageDirectory()
            + File.separator + "gzrlog";
    public static final String PRE_FILE_NAME = "GzrApp";
    // crash文件的后缀
    private static final String LOG_CRASH_NAME = "_crash.log";
    private static final String LOG_RUN_NAME = "_run.log";
    public static String CRASH_PATH = ROOT + File.separator + PRE_FILE_NAME + LOG_CRASH_NAME;
    public static String RUN_PATH = ROOT + File.separator + PRE_FILE_NAME + LOG_RUN_NAME;
    private static Tag sLogClass;

    static {
        sLogClass = new Tag();
    }

    @SuppressWarnings("rawtypes")
    public LogUtil(Class logClass, String tag) {
        mTag = tag;
        mLogClass = logClass;
    }

    @SuppressWarnings("rawtypes")
    public LogUtil(Class logClass) {
        mLogClass = logClass;
    }

    /**
     * Write crash log to sd card
     */
    public void writeCrashLog(String err_msg) {
        Logger logger = getConfigLog(CRASH_PATH);
        logger.debug(mTag + " " + err_msg);
    }

    /**
     * Write crash log to sd card
     */
    public static void writeRunLog(String tag, String err_msg) {
        Logger logger = getConfigLog(RUN_PATH, sLogClass.getClass());
        logger.debug(tag + " " + err_msg);
    }

    /**
     * 获取一个写日志的logger 该方法不能只获取一次。每次使用都得重新获取该logger
     *
     * @param clazz Log tag name
     * @param path  保存日志的路径 %m 输出代码中指定的消息 %p 输出优先级，即DEBUG，INFO，WARN，ERROR，FATAL %r
     *              输出自应用启动到输出该log信息耗费的毫秒数 %c 输出所属的类目，通常就是所在类的全名 %t 输出产生该日志事件的线程名
     *              %n 输出一个回车换行符，Windows平台为“/r/n”，Unix平台为“/n” %d
     *              输出日志时间点的日期或时间，默认格式为ISO8601，也可以在其后指定格式，比如：%d{yyy MMM dd
     *              HH:mm:ss , SSS}，输出类似：2002年10月18日 22 ： 10 ： 28 ， 921 %l
     *              输出日志事件的发生位置
     *              ，包括类目名、发生的线程，以及在代码中的行数。举例：Testlog4.main(TestLog4.java: 10 )
     * @return
     */
    private static Logger getConfigLog(String path, Class clazz) {
        // 获取系统时间
        final LogConfigurator logConfigurator = new LogConfigurator();
        logConfigurator.setFileName(path);
        logConfigurator.setFilePattern("%d{yyy/MM/dd HH:mm:ss} %m%n");
        logConfigurator.configure();
        return Logger.getLogger(clazz);
    }

    /**
     * 获取一个写日志的logger 该方法不能只获取一次。每次使用都得重新获取该logger
     *
     * @param path 保存日志的路径 %m 输出代码中指定的消息 %p 输出优先级，即DEBUG，INFO，WARN，ERROR，FATAL %r
     *             输出自应用启动到输出该log信息耗费的毫秒数 %c 输出所属的类目，通常就是所在类的全名 %t 输出产生该日志事件的线程名
     *             %n 输出一个回车换行符，Windows平台为“/r/n”，Unix平台为“/n” %d
     *             输出日志时间点的日期或时间，默认格式为ISO8601，也可以在其后指定格式，比如：%d{yyy MMM dd
     *             HH:mm:ss , SSS}，输出类似：2002年10月18日 22 ： 10 ： 28 ， 921 %l
     *             输出日志事件的发生位置
     *             ，包括类目名、发生的线程，以及在代码中的行数。举例：Testlog4.main(TestLog4.java: 10 )
     * @return
     */
    private Logger getConfigLog(String path) {
        // 获取系统时间
        final LogConfigurator logConfigurator = new LogConfigurator();
        logConfigurator.setFileName(path);
        logConfigurator.setFilePattern("%d{yyy/MM/dd HH:mm:ss} %m%n");
        logConfigurator.configure();
        return Logger.getLogger(mLogClass);
    }

    public static void log(String tag, String msg) {
        if (BuildConfig.DEBUG_MODE || BuildConfig.PRINT_LOG) {
            if (isWritePermissions) {
                writeRunLog(tag, msg);
            }
        }
    }

    private static class Tag {
    }
}
