package bbs.board.file;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * date           : 2025-01-14
 * created by     : 임경재
 * description    :
 */
public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {
    List<ImageFile> findByIsTemporary(boolean isTemporary);
}
