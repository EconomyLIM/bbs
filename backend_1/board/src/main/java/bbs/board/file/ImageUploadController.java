package bbs.board.file;

/**
 * date           : 2025-01-14
 * created by     : 임경재
 * description    :
 */
import bbs.board.common.dto.BasicResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ImageUploadController {

    private final ImageFileService imageFileService;

    @PostMapping("/upload")
    public ResponseEntity<BasicResponse> uploadImage(@RequestParam("file") MultipartFile file) {
        try {

            ImageFile savedFile = imageFileService.saveImage(file, true);

            FileUploadResponseDTO fileUploadResponseDTO = new FileUploadResponseDTO(savedFile.getFileName());
            return ResponseEntity.ok(fileUploadResponseDTO);

        } catch (IOException e) {
            log.error(e.getMessage());
            return ResponseEntity.ok(new BasicResponse("BAD", "test"));
        }
    }

    @PostMapping("/delete-temp-images")
    public ResponseEntity<?> deleteTemporaryImages() {
        imageFileService.deleteTemporaryImages();
        return ResponseEntity.ok("임시 이미지 삭제 완료");
    }

    @ResponseBody
    @GetMapping("/files/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        String fullPath = imageFileService.getFullPath(filename);
        log.info("========================== fullPath = {}", fullPath);
        return new UrlResource("file:" + fullPath);
    }
}