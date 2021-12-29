package org.acme.getting.started;

import com.uber.h3core.H3Core;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.apache.commons.io.FileUtils;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@ApplicationScoped
public class AppLifecycleBean {

    private static final Logger LOG = Logger.getLogger(AppLifecycleBean.class);

    void onStart(@Observes StartupEvent ev) {
        LOG.info("The application is starting... os-arch: "+System.getProperty("os.name")+"-"+System.getProperty("os.arch"));
        try {
            LOG.info("next load3");
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
}