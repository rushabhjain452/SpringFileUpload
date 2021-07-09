package com.example.SpringFileUpload.helpers;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

@Component
public class FileUploadHelper {

//     Static Path
//    public final String UPLOAD_DIR = "C:\\Rushabh\\Projects\\IdeaProjects\\SpringFileUpload\\src\\main\\resources\\static\\uploads";
//    public final String UPLOAD_DIR = "src\\main\\resources\\static\\uploads";

//     Dynamic Path
    // ClassPathResource will give the path of "resources" directory
    public final String UPLOAD_DIR = new ClassPathResource("static/uploads/").getFile().getAbsolutePath();

    public FileUploadHelper() throws IOException {

    }

    public boolean uploadFile(MultipartFile multipartFile){
        System.out.println(UPLOAD_DIR);
        boolean isUploaded = false;
        try{
//            Using IO Package
//            // Read file
//            InputStream inputStream = multipartFile.getInputStream();
//            byte data[] = new byte[inputStream.available()];
//            inputStream.read(data);
//            inputStream.close();
//            // Write file
//            FileOutputStream fileOutputStream = new FileOutputStream(UPLOAD_DIR + File.separator + multipartFile.getOriginalFilename());
//            fileOutputStream.write(data);
//            fileOutputStream.flush();
//            fileOutputStream.close();

//            Using NIO Package
//            public static long copy(InputStream in, Path target, CopyOption... options)
//            Files.copy(multipartFile.getInputStream(), Paths.get(UPLOAD_DIR + File.separator + multipartFile.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);

            // Using "transferTo" method of MultipartFile
            multipartFile.transferTo(new File(UPLOAD_DIR + File.separator + multipartFile.getOriginalFilename()));

            // Set uploaded to true
            isUploaded = true;
        }
        catch(Exception e){
            e.printStackTrace();
            isUploaded = false;
        }
        return isUploaded;
    }

}
