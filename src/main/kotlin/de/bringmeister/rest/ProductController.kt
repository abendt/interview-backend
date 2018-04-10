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
class ProductController(val productMasterDataRepository: ProductMasterDataRepository, val priceRepository: PriceRepository) {

    @GetMapping("/products", produces = ["application/json"])
    fun listAllProductsWithMasterData() = mapOf("products" to productMasterDataRepository.findAllMasterData())

    @GetMapping("/products/{productId}", produces = ["application/json"])
    fun showSingleProductWithMasterDataAndAllPrices(@PathVariable("productId") productId: String): ResponseEntity<Map<String, Any>> {
        val masterData = productMasterDataRepository.findMasterDataById(productId)
                ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        val pricesBySku = priceRepository.findPricesBySku(masterData.stockKeepingUnit)
        val product = mapOf("masterdata" to masterData, "prices" to pricesBySku)

        return ResponseEntity(mapOf("product" to product), HttpStatus.OK)
    }

    @GetMapping("/products/{productId}/prices/{priceUnit}", produces = ["application/json"])
    fun showSingleProductPriceByProductAndUnit(@PathVariable("productId") productId: String,
                                               @PathVariable("priceUnit") priceUnit: PriceUnit): ResponseEntity<Map<String, Any>> {
        val masterData = productMasterDataRepository.findMasterDataById(productId)
                ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        val price = priceRepository.findPriceBySkuAndUnit(masterData.stockKeepingUnit, priceUnit)

        val response = mapOf("price" to price)

        return ResponseEntity(response, HttpStatus.OK)
    }
}
