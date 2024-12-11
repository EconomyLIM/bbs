package bbs.board;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * date           : 2024-12-03
 * created by     : 임경재
 * description    :
 */
@SpringBootTest
public class MockTest {

    @Test
    public void testSimple() throws Exception{
        // given
        final List<String> mockList = Mockito.mock(List.class);

        Mockito.when(mockList.size()).thenReturn(5);
        System.out.println("동작 값을 확인 합니다 :: " + mockList.size());   // 동작 값을 확인 합니다 :: 5

        // 3. 메서드 호출 검증 : Verify
        Mockito.verify(mockList).size();
    }
}
