package hello.exeption.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exeption.exception.UserException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;

@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Object handler,
                                         Exception ex) {
        try {
            // UserExcpeption 예외인지 확인
            if (ex instanceof UserException) {
                log.info("UserException resolver to 400");
                // request 에서 헤더의 accept 정보 호출
                String acceptHeader = request.getHeader("accept");
                // 400 에러 설정
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                // accept 정보가 json 인지 확인
                if ("application/json".equals(acceptHeader)) {
                    // json 변환하기위한 Map 객체 생성 및 ObjectMapper 이용한 문자열 변환
                    HashMap<String, Object> errorResult = new HashMap<>();
                    errorResult.put("ex", ex.getClass());
                    errorResult.put("message", ex.getMessage());
                    String result = objectMapper.writeValueAsString(errorResult);

                    // response 에 json 데이터 보내기
                    response.setContentType("application/json");
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().write(result);
                    // 빈 ModelAndView 반환 -> 뷰 렌더링 X
                    return new ModelAndView();
                }
            }
        } catch (IOException e) {
            log.error("resolver ex", e);
        }
        // 다른 HandlerExceptionResolver 호출, 없으면 예외 그대로 전달
        return null;
    }
}
