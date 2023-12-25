import books.domain.Book;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.hasItem;

public class BooksRESTTest {

    @BeforeClass
    public static void setup() {
        RestAssured.port = Integer.valueOf(8080);
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "";
    }

    @Test
    public void testGetOneBook() {
        // add the book to be fetched
        Book book = new Book("878","Book 123", 18.95, "Joe Smith");
        given()
                .contentType("application/json")
                .body(book)
                .when().post("/books").then()
                .statusCode(200);
        // test getting the book
        given()
                .when()
                .get("books/878")
                .then()
                .contentType(ContentType.JSON)
                .and()
                .body("isbn",equalTo("878"))
                .body("title",equalTo("Book 123"))
                .body("price",equalTo(18.95f))
                .body("author",equalTo("Joe Smith"));
        //cleanup
        given()
                .when()
                .delete("books/878");
    }

    @Test
    public void testUpdateook() {
        // add the book to be fetched
        Book book = new Book("878","Book 123", 18.95, "Joe Smith");
        given()
                .contentType("application/json")
                .body(book)
                .when().post("/books").then()
                .statusCode(200);

        book.setAuthor("Joe XX");

        // test update the book
        given()
                .contentType("application/json")
                .body(book)
                .when().put("books/878")
                .then()
                .contentType(ContentType.JSON)
                .and()
                .body("isbn",equalTo("878"))
                .body("title",equalTo("Book 123"))
                .body("price",equalTo(18.95f))
                .body("author",equalTo("Joe XX"));
        //cleanup
        given()
                .when()
                .delete("books/878");
    }

    @Test
    public void testFindBook() {
        // add the book to be fetched
        Book book1 = new Book("878","Book 123", 18.95, "Joe Smith");
        Book book2 = new Book("999","Book xxx", 20.95, "Tin Tin");
        given()
                .contentType("application/json")
                .body(book1)
                .when().post("/books").then()
                .statusCode(200);
        given()
                .contentType("application/json")
                .body(book2)
                .when().post("/books").then()
                .statusCode(200);

        // test getting the book
        given()
                .when()
                .get("/books")
                .then()
                .contentType(ContentType.JSON)
                .body("books.isbn",hasItems ("878","999"))
                .body("books.title",hasItems("Book 123","Book xxx"))
                .body("books.price",hasItems(18.95f,20.95f))
                .body("books.author",hasItems("Joe Smith","Tin Tin"));

        //cleanup
        given()
                .when()
                .delete("books/878");
        given()
                .when()
                .delete("books/999");
    }


}
