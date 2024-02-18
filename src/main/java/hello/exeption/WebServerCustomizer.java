package hello.exeption;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;

//@Component
public class WebServerCustomizer implements
        WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/templates/error-page/404");
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.NOT_FOUND, "/templates/error-page/500");
        ErrorPage errorPageEx = new ErrorPage(RuntimeException.class, "/templates/error-page/500");
    }
}
