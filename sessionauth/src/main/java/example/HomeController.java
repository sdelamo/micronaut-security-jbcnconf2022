package example;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.views.View;
import jakarta.annotation.security.PermitAll;
import java.util.HashMap;
import java.util.Map;

@PermitAll
@Controller
public class HomeController {

    @Produces(MediaType.TEXT_HTML)
    @View("home")
    @Get
    public Map<String, Object> index() {
        return new HashMap<>();
    }

    @Produces(MediaType.TEXT_HTML)
    @View("login")
    @Get("/login")
    public Map<String, Object> login() {
        return new HashMap<>();
    }
}
