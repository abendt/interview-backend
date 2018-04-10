package de.bringmeister.rest

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import de.bringmeister.domain.ProductMasterData
import de.bringmeister.products.PriceRepository
import de.bringmeister.products.ProductMasterDataRepository
import org.assertj.core.api.KotlinAssertions
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ProductServiceTest {

    @InjectMocks
    lateinit var serviceUnderTest: ProductService

    @Mock
    lateinit var priceRepository: PriceRepository

    @Mock
    lateinit var productRepository: ProductMasterDataRepository

    @Test
    fun productWithDetailsIsAbsentByDefault() {
        KotlinAssertions.assertThat(serviceUnderTest.productWithDetails("doesNotExist")).isNull()
    }

    @Test
    fun productWithDetailsFetchesPricesBySku() {
        doReturn(aValidProduct("sku"))
                .whenever(productRepository).findMasterDataById(any())

        serviceUnderTest.productWithDetails("aProduct")

        verify(priceRepository).findPricesBySku("sku")
    }

    private fun aValidProduct(withSku: String) = ProductMasterData("id", "name", "myDescription", withSku)
}