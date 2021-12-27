package org.acme.getting.started;

import com.uber.h3core.H3Core;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;

@ApplicationScoped
public class AppLifecycleBean {

    private static final Logger LOG = Logger.getLogger(AppLifecycleBean.class);

    void onStart(@Observes StartupEvent ev) {
        LOG.info("The application is starting... os-arch: "+System.getProperty("os.name")+"-"+System.getProperty("os.arch"));
        try {

            File parent = new File(System.getProperty("java.io.tmpdir"));
            String contents[] = parent.list();
            LOG.info("List of files and directories in the specified directory: "+parent.getAbsolutePath());
            for(int i=0; i<contents.length; i++) {
                System.out.println(contents[i]);
            }
            H3Core h3 = H3Core.newInstance();
            LOG.info("h3 loaded: "+h3.geoToH3Address(48,120,1));
        } catch (Exception | java.lang.UnsatisfiedLinkError e) {
            e.printStackTrace();
        }
        LOG.info("The application started successfully");
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOG.info("The application is stopping...");
    }

    private static void addLibsToJavaLibraryPath(final String tmpDirName) {
        try {
            System.setProperty("java.library.path", tmpDirName);
            /* Optionally add these two lines */
            System.setProperty("jna.library.path", tmpDirName);
            System.setProperty("jni.library.path", tmpDirName);
            final Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
            fieldSysPath.setAccessible(true);
            fieldSysPath.set(null, null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}