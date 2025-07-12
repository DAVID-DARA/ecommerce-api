package com.project.ecommerce_api.product.service;

import com.cloudinary.Cloudinary;
import com.project.ecommerce_api.shared.exceptions.CustomException;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService {

    private static final Logger logger = LoggerFactory.getLogger(CloudinaryService.class);

    @Resource
    private Cloudinary cloudinary;

    public String uploadFile(MultipartFile file) {
        try {
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", "Products");
            Map<?, ?> uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
            String publicId = (String) uploadedFile.get("public_id");
            logger.info("Public: {}", publicId);
            return cloudinary.url().secure(true).generate(publicId);
        } catch (Exception e) {
            logger.error("Error uploading image to cloudinary: {}", e.getMessage());
            throw new CustomException("Error uploading image to cloudinary");
        }
    }

}
