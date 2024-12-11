package bbs.board.repository;

import bbs.board.domain.Board;
import bbs.board.dto.common.CommonPageSizes;
import bbs.board.dto.request.BoardSearchRequestDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static bbs.board.domain.QBoard.board;

/**
 * date           : 2024-12-03
 * created by     : 임경재
 * description    :
 */
public class BoardRepositoryImpl implements BoardCustomRepository{

    private final JPAQueryFactory queryFactory;

    public BoardRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Board> findBoardBySearch(BoardSearchRequestDTO dto) {
        return queryFactory.select(board)
                .from(board)
                .where(allSearch(dto))
                .offset(dto.getPage())
                .limit(CommonPageSizes.BOARD_PAGE_SIZE.getPageSize())
                .fetch();
    }

    private BooleanBuilder allSearch(BoardSearchRequestDTO dto){
        return titleEq(dto.getTitle())
                .and(searchEq(dto.getSearchWord()));
    }

    private BooleanBuilder titleEq(String title){
        if (title == null){
            return new BooleanBuilder();
        }

        return new BooleanBuilder(board.title.eq(title));
    }

    private BooleanBuilder searchEq(String searchWord){
        if (searchWord == null){
            return new BooleanBuilder();
        }
        return new BooleanBuilder(board.content.like(searchWord));
    }
}
