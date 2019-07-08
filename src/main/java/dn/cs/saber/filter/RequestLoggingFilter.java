package dn.cs.saber.filter;

import org.apache.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Log request filter.
 * <p>Using Ordered.HIGHEST_PRECEDENCE to log all requests.</p>
 *
 * @author dn
 * @since 2.0.0
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = Logger.getLogger(RequestLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String equalSign = "=";
        String comma = ",";
        Map<String, String[]> parameterMap = request.getParameterMap();
        StringBuilder params = new StringBuilder(parameterMap.size() * 20);
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String key = entry.getKey();
            String[] value = entry.getValue();
            params.append(key).append(equalSign).append(String.join(comma, value)).append(comma);
        }
        LOGGER.info(String.format("request: [%s] [%d, %s] [%s] [%s] [%s]",
                request.getRemoteAddr(), Thread.currentThread().getId(), Thread.currentThread().getName(), request.getRequestURI(), request.getMethod(), params));
        filterChain.doFilter(request, response);
    }

}
