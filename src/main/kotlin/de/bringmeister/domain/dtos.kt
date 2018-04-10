package de.bringmeister.domain

import com.fasterxml.jackson.annotation.JsonIgnore

data class ProductMasterData(val id: String,
                             val name: String,
                             val description: String,
                             val stockKeepingUnit: String)

enum class PriceUnit {
    PACKAGE, PIECE
}

data class Price(@JsonIgnore val id: String,
                 val cents: Int,
                 val currency: String,
                 val unit: PriceUnit)

data class ProductWithDetails(val details: ProductMasterData, val prices: List<Price>)