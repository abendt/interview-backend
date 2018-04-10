package de.bringmeister.rest

import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.hasItems
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @LocalServerPort
    var localPort = 0

    @Before
    fun setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
        RestAssured.port = localPort
    }

    @Test
    fun canListAllProductsWithMasterData() {
        RestAssured.given()
                .accept("application/json")
                .whenever()
                .get("/products")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .log().all()
                .body("products.name", hasItems("Banana", "Tomato"))
    }

    @Test
    fun showNonExistingProductReturns404() {
        RestAssured.given()
                .accept("application/json")
                .whenever()
                .get("/products/doesNotExist")
                .then()
                .log().all()
                .statusCode(404)
    }

    @Test
    fun canShowExistingProduct() {
        RestAssured.given()
                .accept("application/json")
                .whenever()
                .get("/products/43b105a0-b5da-401b-a91d-32237ae418e4")
                .then()
                .log().all()
                .statusCode(200)
                .body("product.details.id", equalTo("43b105a0-b5da-401b-a91d-32237ae418e4"))
                .body("product.prices.size()", equalTo(2))
    }

    @Test
    fun canShowPriceByProductAndUnit() {
        RestAssured.given()
                .accept("application/json")
                .whenever()
                .get("/products/43b105a0-b5da-401b-a91d-32237ae418e4/prices/PIECE")
                .then()
                .log().all()
                .statusCode(200)
                .body("price.cents", equalTo(245))
                .body("price.currency", equalTo("EUR"))
                .body("price.unit", equalTo("PIECE"))
    }

    @Test
    fun showPriceReturns404ForNonExistingProduct() {
        RestAssured.given()
                .accept("application/json")
                .whenever()
                .get("/products/doesNotExist/prices/PIECE")
                .then()
                .log().all()
                .statusCode(404)
    }
}

fun RequestSpecification.whenever() = this.`when`()