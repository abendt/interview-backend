package de.bringmeister.rest

import de.bringmeister.domain.PriceUnit
import de.bringmeister.products.PriceRepository
import de.bringmeister.products.ProductMasterDataRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(val productService: ProductService) {

    @GetMapping("/products", produces = ["application/json"])
    fun listAllProductsWithMasterData() = mapOf("products" to productService.allProducts())

    @GetMapping("/products/{productId}", produces = ["application/json"])
    fun showSingleProductWithMasterDataAndAllPrices(@PathVariable("productId") productId: String): ResponseEntity<Map<String, Any>> {
        val result = productService.productWithDetails(productId)
                ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        return ResponseEntity(mapOf("product" to result), HttpStatus.OK)
    }

    @GetMapping("/products/{productId}/prices/{priceUnit}", produces = ["application/json"])
    fun showSingleProductPriceByProductAndUnit(@PathVariable("productId") productId: String,
                                               @PathVariable("priceUnit") priceUnit: PriceUnit): ResponseEntity<Map<String, Any>> {
        val result = productService.productPrice(productId, priceUnit)
                ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        val response = mapOf("price" to result)

        return ResponseEntity(response, HttpStatus.OK)
    }
}
