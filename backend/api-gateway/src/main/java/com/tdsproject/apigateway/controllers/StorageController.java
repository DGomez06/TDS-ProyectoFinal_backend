package com.tdsproject.apigateway.controllers;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.cloud.StorageClient;
import com.tdsproject.apigateway.entities.Images;
import com.tdsproject.apigateway.entities.Property;
import com.tdsproject.apigateway.repositories.ImagesRepository;
import com.tdsproject.apigateway.repositories.PropertyRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/storage")
public class StorageController {
    @Autowired
    ImagesRepository imgRepository;
    @Autowired
    PropertyRepository propertyRepository;

    @PostMapping("/{id}")
    public ResponseEntity<String> saveImage(@PathVariable("id") Integer id, @RequestParam("file")MultipartFile file) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();
        String filename = UUID.randomUUID().toString() + file.getOriginalFilename();
        bucket.create(filename, file.getBytes(), file.getContentType());
        String URL = String.format("https://firebasestorage.googleapis.com/v0/b/rentalm-e4a0c.appspot.com/o/%s?alt=media", filename);

        if (propertyRepository.findById(id).isEmpty()){
            return ResponseEntity.ok("No existe");
        }

        Images img = new Images(
                URL,
                propertyRepository.findById(id).get()
        );

        imgRepository.save(img);

        return ResponseEntity.ok("Guardado");
    }
}
