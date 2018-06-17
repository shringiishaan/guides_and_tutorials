package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Chief implements Filter {

    public Chief() {
        
    }    

    @Override
    public void init(FilterConfig filterConfig) {
        
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;

        String requestedPath = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        if(requestedPath.endsWith(".css") || requestedPath.endsWith(".js")) {
            httpRequest.getServletContext().getRequestDispatcher(requestedPath).forward(request, response);
            return;
        }
        System.out.println("requested path : "+requestedPath);
        HttpSession session = httpRequest.getSession(true);
        handleSpamRequests(session);
        httpRequest.getServletContext().getRequestDispatcher(requestedPath).forward(request, response);
    }
    
    public void handleSpamRequests(HttpSession session) {
        Object lastRequestTime = session.getAttribute("lastRequestTime");
        if(lastRequestTime!=null) {
            long timeElapsed = System.currentTimeMillis() - (long)lastRequestTime;
            System.out.println("time elapsed : " + timeElapsed);
            if (timeElapsed<1000) {
                //Handle DOS
            }
        }
        session.setAttribute("lastRequestTime",System.currentTimeMillis());
    }

    @Override
    public void destroy() {
        
    }
}