package bbs.board.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * date           : 2025-01-14
 * created by     : 임경재
 * description    :
 */
@Service
@Slf4j
public class ImageFileService {

    private final ImageFileRepository imageFileRepository;
    private final String uploadDir;

    public ImageFileService(final ImageFileRepository imageFileRepository, @Value("${upload.directory}") final String uploadDir) {
        this.imageFileRepository = imageFileRepository;
        this.uploadDir = uploadDir;
    }

    public ImageFile saveImage(MultipartFile multipartFile, boolean isTemporary) throws IOException {
        // 디렉토리 생성
       if (multipartFile.isEmpty()) {
           return null;
       }

       String fullPath = uploadDir + multipartFile.getOriginalFilename();
       multipartFile.transferTo(new File(fullPath));

        // 엔티티 저장
        ImageFile imageFile = ImageFile.builder()
                .fileName(multipartFile.getOriginalFilename())
                .filePath(fullPath)
                .isTemporary(isTemporary)
                .build();

        return imageFileRepository.save(imageFile);
    }

    public void deleteTemporaryImages() {
        List<ImageFile> tempImages = imageFileRepository.findByIsTemporary(true);
        for (ImageFile image : tempImages) {
            File file = new File(image.getFilePath());
            if (file.exists()) {
                file.delete();
            }
            imageFileRepository.delete(image);
        }
    }

    public String getFullPath(String filename){
        return uploadDir + filename;
    }
}
