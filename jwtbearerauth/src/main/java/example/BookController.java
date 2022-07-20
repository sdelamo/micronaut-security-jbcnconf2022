package example;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import java.util.Arrays;
import java.util.List;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/books")
public class BookController {

    @Get
    public List<Book> index() {
        return Arrays.asList(new Book("1491950358", "Building Microservices"),
        new Book("1680502395", "Release It!"),
        new Book("0321601912", "Continuous Delivery"));
    }
}