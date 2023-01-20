package tech.tresearchgroup.babygalago.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostmanProjectGenerator {
    private static final Logger logger = LoggerFactory.getLogger(PostmanProjectGenerator.class);

    public static void main(String[] args) {
        /*
        List<String> classNames = ReflectionMethods.getClassNames(new String[]{
            "tech.tresearchgroup.babygalago.view.endpoints",
            "tech.tresearchgroup.babygalago.view.endpoints.api",
            "tech.tresearchgroup.babygalago.view.endpoints.ui"
        });
        for (String theClassName : classNames) {
            try {
                String[] classParts = theClassName.split("\\.");
                String noPackage = classParts[classParts.length - 1];
                Class theClass = ReflectionMethods.findClass(noPackage.toLowerCase(), new String[]{
                    "tech.tresearchgroup.babygalago.view.endpoints",
                    "tech.tresearchgroup.babygalago.view.endpoints.api",
                    "tech.tresearchgroup.babygalago.view.endpoints.ui"
                });
                if (theClass != null) {
                    logger.info(theClassName);
                } else {
                    logger.info("Null class: " + theClassName);
                }
            } catch (Exception e) {
                logger.error(theClassName);
                e.printStackTrace();
            }
        }*/
    }
}
