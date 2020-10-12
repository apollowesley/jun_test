package com.foo.common.base.utils;

import com.foo.common.base.utils.laboratory.imageUtils.ImageLoader;

import java.io.File;
import java.io.IOException;

public class ImageHelper {

    private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory
            .getLogger(ImageHelper.class);

    public void croopImage() throws IOException {
        final String sourcePath = "";
        final String targetPath = "";
        File file = ImageLoader.fromFile(sourcePath)
                .crop(20, 100, 50, 200)
                .writeToFile(new File(targetPath));
        System.out.println(file.getName());
        ;
    }
}
