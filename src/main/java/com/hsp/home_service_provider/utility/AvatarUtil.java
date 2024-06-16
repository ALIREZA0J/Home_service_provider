package com.hsp.home_service_provider.utility;


import com.hsp.home_service_provider.exception.AvatarException;
import com.hsp.home_service_provider.exception.FileNotFoundException;
import com.hsp.home_service_provider.exception.ImageInputStreamException;
import com.hsp.home_service_provider.model.Avatar;
import com.hsp.home_service_provider.model.Specialist;
import com.hsp.home_service_provider.model.enums.AvatarStatus;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;

public class AvatarUtil {

    public static void checkFileExist(File file) {
        if (!file.exists()) {
            throw new FileNotFoundException("File not found.");
        }
    }
    public static void checkTheSizeOfTheFile(File file) throws IOException {
        long bytes = Files.size(file.toPath());
        if ((bytes / 1024) > 300)
            throw new AvatarException("Size of photo more than 300 kilobytes.");
    }


    public static byte[] readPhoto(File file) throws IOException {

        String imageFormat = imageFormatReader(file).toLowerCase();
        BufferedImage image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image = ImageIO.read(file);
        if (imageFormat.equals("jpg") || imageFormat.equals("jpeg")) {
            ImageIO.write(image, "jpg", baos);
            return baos.toByteArray();
        } else return null;
    }

    public static String imageFormatReader(File file) throws IOException {
        ImageInputStream iis = ImageIO.createImageInputStream(file);
        if (iis == null) {
            throw new ImageInputStreamException("Unable to create ImageInputStream.");
        }
        Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
        if (!readers.hasNext()) {
            throw new AvatarException("No readers found for the given image.");
        }

        ImageReader reader = readers.next();

        if (!(reader.getFormatName().equalsIgnoreCase("jpg")
                || reader.getFormatName().equalsIgnoreCase("jpeg")) )
            throw new AvatarException("There is a problem with the photo format."+
                    " It should be JPG or JPEG.");

        return reader.getFormatName();

    }

    public static void saveImageOnLocalSystem(Avatar avatar) throws IOException {
        byte[] imageBytes = avatar.getAvatarData();
        assert imageBytes != null;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
        BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
        String photoFileName = avatar.getName() + "." + avatar.getType();
        File fileSavedImage = new File("D:\\java\\java.project\\home-service-provider\\photo\\"
                + photoFileName);
        ImageIO.write(bufferedImage, avatar.getType(), fileSavedImage);
        System.out.println("Image saved successfully to " + fileSavedImage.getAbsolutePath());
    }

    public static Avatar getAvatar(Specialist specialist, String fileFormat, byte[] imageData) {
        return Avatar.builder()
                .setAvatarData(imageData)
                .setType(fileFormat)
                .setSpecialist(specialist)
                .setName(specialist.getGmail())
                .setAvatarStatus(AvatarStatus.NOT_SAVED_IN_FILE)
                .build();
    }
}
