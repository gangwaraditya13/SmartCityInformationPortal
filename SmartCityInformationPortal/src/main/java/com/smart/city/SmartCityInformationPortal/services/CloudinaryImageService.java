package com.smart.city.SmartCityInformationPortal.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CloudinaryImageService {

    private final Cloudinary cloudinary;
    @Autowired
    public CloudinaryImageService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    /**
     * Upload image to Cloudinary
     */
    public Map<String, Object> uploadImage(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File must not be empty");
        }

        try {
            log.info("Uploading image: name={}, size={}",
                    file.getOriginalFilename(), file.getSize());

            return cloudinary.uploader()
                    .upload(file.getBytes(), ObjectUtils.emptyMap());

        } catch (IOException e) {
            log.error("Image upload failed", e);
            throw new RuntimeException("Failed to upload image");
        }
    }

    /**
     * Delete single image
     */
    public Map<String, Object> deleteImage(String publicId) {

        if (publicId == null || publicId.isBlank()) {
            throw new IllegalArgumentException("Public ID must not be empty");
        }

        try {
            log.info("Deleting image with publicId={}", publicId);

            return cloudinary.uploader()
                    .destroy(publicId, ObjectUtils.emptyMap());

        } catch (Exception e) {
            log.error("Image delete failed", e);
            throw new RuntimeException("Failed to delete image");
        }
    }

    /**
     * Delete multiple images
     */
    public Map<String, Object> deleteImages(List<String> publicIds) {

        if (publicIds == null || publicIds.isEmpty()) {
            throw new IllegalArgumentException("Public IDs list must not be empty");
        }

        try {
            log.info("Deleting {} images", publicIds.size());

            return cloudinary.api()
                    .deleteResources(publicIds, ObjectUtils.emptyMap());

        } catch (Exception e) {
            log.error("Bulk delete failed", e);
            throw new RuntimeException("Failed to delete images");
        }
    }

    /**
     * Update images
     */
    public Map<String, Object> replaceImage(
            MultipartFile newFile,
            String oldPublicId) {

        deleteImage(oldPublicId);
        return uploadImage(newFile);
    }

}
