package bbs.board.file;

import jakarta.persistence.*;
import lombok.*;

/**
 * date           : 2025-01-14
 * created by     : 임경재
 * description    :
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String filePath;

    private boolean isTemporary; // 임시 여부
}
