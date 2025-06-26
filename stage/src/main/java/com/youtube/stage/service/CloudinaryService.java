package com.youtube.stage.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryService {

    private Cloudinary cloudinary;

    @Value("${cloudinary.cloud_name}")
    private String cloudName;

    @Value("${cloudinary.api_key}")
    private String apiKey;

    @Value("${cloudinary.api_secret}")
    private String apiSecret;

    @PostConstruct
    public void init() {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }

    public String uploadVideo(byte[] videoBytes, String originalFilename) {
        try {
            String uniqueId = UUID.randomUUID().toString();
            String publicId = "videos/" + uniqueId;

            Map uploadResult = cloudinary.uploader().uploadLarge(videoBytes, ObjectUtils.asMap(
                    "resource_type", "video",
                    "public_id", publicId
            ));
            return (String) uploadResult.get("secure_url");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String uploadImage(byte[] imageBytes, String originalFilename) {
        try {
            String uniqueId = UUID.randomUUID().toString();
            String publicId = "thumbnails/" + uniqueId;

            Map uploadResult = cloudinary.uploader().upload(imageBytes, ObjectUtils.asMap(
                    "resource_type", "image",
                    "public_id", publicId
            ));
            return (String) uploadResult.get("secure_url");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
