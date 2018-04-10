package de.bringmeister.rest

import de.bringmeister.domain.Price
import de.bringmeister.domain.PriceUnit
import de.bringmeister.domain.ProductMasterData
import de.bringmeister.domain.ProductWithDetails
import de.bringmeister.products.ProductService
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(val productService: ProductService) {

    @ApiOperation(value = "List all products with their master data",
            response = ProductMasterData::class,
            responseContainer = "List")
    @GetMapping("/products", produces = ["application/json"])
    fun listAllProductsWithMasterData() = productService.allProducts()

    @ApiOperation(value = "Show single product with master data and all available prices",
            response = ProductWithDetails::class,
            responseContainer = "Map")
    @GetMapping("/products/{productId}", produces = ["application/json"])
    fun showSingleProductWithMasterDataAndAllPrices(@PathVariable("productId") productId: String): ResponseEntity<ProductWithDetails> {
        val result = productService.productWithDetails(productId)
                ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        return ResponseEntity(result, HttpStatus.OK)
    }

    @ApiOperation(value = "Show single product price for one product and specific unit",
            response = Price::class,
            responseContainer = "Map")
    @GetMapping("/products/{productId}/prices/{priceUnit}", produces = ["application/json"])
    fun showSingleProductPriceByProductAndUnit(@PathVariable("productId") productId: String,
                                               @PathVariable("priceUnit") priceUnit: PriceUnit): ResponseEntity<Price> {
        val result = productService.productPrice(productId, priceUnit)
                ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        return ResponseEntity(result, HttpStatus.OK)
    }
}
