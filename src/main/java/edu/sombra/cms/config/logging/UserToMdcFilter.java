package edu.sombra.cms.config.logging;

import edu.sombra.cms.util.SecurityUtil;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
@Order(2)
public class UserToMdcFilter implements Filter
{
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        MDC.put("userId", "<userId: " + SecurityUtil.getLoggedUserIdString() + ">");
        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove("userId");
        }
    }
}