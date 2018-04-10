package de.bringmeister.rest

import de.bringmeister.domain.Price
import de.bringmeister.domain.PriceUnit
import de.bringmeister.domain.ProductMasterData
import de.bringmeister.domain.ProductWithDetails
import de.bringmeister.products.PriceRepository
import de.bringmeister.products.ProductMasterDataRepository
import org.springframework.stereotype.Service

@Service
class ProductService(val productMasterDataRepository: ProductMasterDataRepository,
                     val priceRepository: PriceRepository) {

    fun allProducts() = productMasterDataRepository.findAllMasterData()

    fun productWithDetails(productId: String): ProductWithDetails? {
        val masterData = productMasterDataRepository.findMasterDataById(productId)
                ?: return null

        val pricesBySku = priceRepository.findPricesBySku(masterData.stockKeepingUnit)

        return ProductWithDetails(masterData, pricesBySku)
    }

    fun productPrice(productId: String, priceUnit: PriceUnit): Price? {
        val masterData = productMasterDataRepository.findMasterDataById(productId)
                ?: return null

        return priceRepository.findPriceBySkuAndUnit(masterData.stockKeepingUnit, priceUnit)
    }
}

