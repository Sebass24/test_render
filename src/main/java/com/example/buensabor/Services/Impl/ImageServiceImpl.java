package com.example.buensabor.Services.Impl;
import com.example.buensabor.Exceptions.ServiceException;
import com.example.buensabor.Models.Entity.Image;
import com.example.buensabor.Repositories.ImageRepository;
import com.example.buensabor.Services.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageServiceImpl extends BaseServiceImpl<Image,Long> implements ImageService {

    private ImageRepository imageRepository;
    private HttpServletRequest request;

    public ImageServiceImpl(ImageRepository imageRepository, HttpServletRequest request) {
        super(imageRepository);
        this.imageRepository = imageRepository;
        this.request = request;
    }

    @Transactional
    public Image save(MultipartFile img) throws ServiceException {
        try {
            if (img.isEmpty()) {
                throw new ServiceException("Empty image file");
            }

            // Guardar el archivo en el sistema de archivos
            String fileName = UUID.randomUUID().toString() + "-" + img.getOriginalFilename();
            String filePath = new File("").getAbsolutePath() + "/src/main/resources/static/" + fileName;
            File dest = new File(filePath);
            img.transferTo(dest);


            String serverPath = fileName;
            Image entity = new Image(fileName,serverPath,img);
            entity = baseRepository.save(entity);
            return entity;
        }catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public MultipartFile getImageById(Long id){
        Optional<Image> img = imageRepository.findById(id);

        if (img.isPresent()){
            File file = new File(img.get().getPath());

            MultipartFile multipartFile;
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                multipartFile = new MockMultipartFile(file.getName(), fileInputStream);
                return multipartFile;
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
        return null;
    }

//    @Transactional
//    public Image update(MultipartFile img) throws ServiceException {
//        try {
//            if (entity.getId() == null) {
//                throw new ServiceException("La entidad a modificar debe contener un Id.");
//            }
//            return baseRepository.save(entity);
//        }catch (Exception e) {
//            throw new ServiceException(e.getMessage());
//        }
//    }
}
