package com.example.SpringFileUpload.controllers;

import com.example.SpringFileUpload.helpers.FileUploadHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class FileUploadController {

    @Autowired
    private FileUploadHelper fileUploadHelper;

    @PostMapping("/upload-file")
    public ResponseEntity<String> uploadFile(@RequestParam(value="profilePicture", required = false) MultipartFile multipartFile){
        try{
            System.out.println(multipartFile.getOriginalFilename());
            System.out.println(multipartFile.getSize());
            // 15 KB = 15219 bytes
            // 1 KB = 1024 bytes
            // 1 MB = 1048576 bytes
            System.out.println(multipartFile.getContentType());
            System.out.println(multipartFile.getName());

            // Validation
            if(multipartFile.isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request must contain file");
            }
            else if(!multipartFile.getContentType().equals("image/jpeg")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Only JPEG content type are allowed");
            }
            else if(multipartFile.getSize() > 1048579){
                // Don't upload if image is greater than 1 MB
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Image size must not exceed 1 MB.");
            }
            else {
                // File Upload
//                multipartFile.transferTo();
                boolean result = fileUploadHelper.uploadFile(multipartFile);
                if(result){
//                    return ResponseEntity.ok("File is successfully uploaded");
                    System.out.println("Context Path : " + ServletUriComponentsBuilder.fromCurrentContextPath().toUriString());
                    return ResponseEntity.ok(ServletUriComponentsBuilder.fromCurrentContextPath().path("/uploads/").path(multipartFile.getOriginalFilename()).toUriString());
                }
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong. Try again !");
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong.");
        }
    }
}
