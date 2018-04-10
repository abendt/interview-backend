package de.bringmeister.products

import org.assertj.core.api.KotlinAssertions
import org.junit.Before
import org.junit.Test

class ProductMasterDataRepositoryTest {

    val serviceUnderTest = ProductMasterDataRepository()

    @Before
    fun setUp() {
        serviceUnderTest.loadAllProducts()
    }

    @Test
    fun canFindAllMasterData() {
        val result = serviceUnderTest.findAllMasterData()

        KotlinAssertions.assertThat(result).hasSize(2)
        KotlinAssertions.assertThat(result).extracting("name").contains("Banana", "Tomato")
        KotlinAssertions.assertThat(result).extracting("stockKeepingUnit").contains("BA-01", "TO-02")
    }

    @Test
    fun masterDataMayBeAbsent() {
        KotlinAssertions.assertThat(serviceUnderTest.findMasterDataById("doesNotExist")).isNull()
    }

    @Test
    fun canFindMasterDataById() {
        val result = serviceUnderTest.findMasterDataById("b867525e-53f8-4864-8990-5f13a5dd9d14")
                ?: throw AssertionError("should exist")

        KotlinAssertions.assertThat(result.id).isEqualTo("b867525e-53f8-4864-8990-5f13a5dd9d14")
        KotlinAssertions.assertThat(result.name).isEqualTo("Tomato")
        KotlinAssertions.assertThat(result.stockKeepingUnit).isEqualTo("TO-02")
        KotlinAssertions.assertThat(result.description).isNotBlank()
    }
}