package bbs.board.file;

import bbs.board.common.dto.BasicResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * date           : 2025-01-14
 * created by     : 임경재
 * description    :
 */
@Getter
public class FileUploadResponseDTO extends BasicResponse {
    private String url;

    public FileUploadResponseDTO(final String url) {
        this.url = "http://localhost:8080/api/files/" + url;
    }

    public FileUploadResponseDTO() {
    }
}
