package de.bringmeister.products

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.bringmeister.domain.Price
import de.bringmeister.domain.PriceUnit
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class PriceRepository {

    /**
     * an actual service prohably wouldn't store
     * large data sets in memory
     * instead fetch on demand from database, another system ...
     */
    lateinit var prices: List<Price>

    @PostConstruct
    fun loadAndNormalizePrices() {
        val jsonPrices = javaClass.getResourceAsStream("/products/prices.json").use {
            jacksonObjectMapper().readValue<List<JsonPrice>>(it, object : TypeReference<List<JsonPrice>>() {})
        }

        prices = jsonPrices.map {
            Price(it.id, it.price.normalizePrice(), it.price.currency, it.normalizeUnit())
        }
    }

    fun findPricesBySku(sku: String): List<Price> = prices.filter { it.id == sku }

    fun findPriceBySkuAndUnit(sku: String, unit: PriceUnit): Price {
        val findPricesBySku = findPricesBySku(sku)

        return findPricesBySku.first { it.unit == unit }
    }
}

// i use an extra datamodel here for communicating with the price datamodel
// this allows us to shield the main logic from implementation details
// e.g. price normalization, flattening of data structure, possible use of jackson annotations

data class JsonNestedPrice(val value: Double, val currency: String) {
    // prefer to store price as Int instead of float/double
    // (e.g. https://stackoverflow.com/questions/3730019/why-not-use-double-or-float-to-represent-currency/)
    fun normalizePrice(): Int {
        assert(currency == "EUR") {
            "needs to be extended for additionals currencies here"
        }

        return (value * 100).toInt()
    }
}

data class JsonPrice(val id: String, val price: JsonNestedPrice, val unit: String) {
    fun normalizeUnit() = PriceUnit.valueOf(unit.toUpperCase())
}