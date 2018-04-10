package de.bringmeister.products

import de.bringmeister.domain.ProductMasterData
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct
import javax.xml.bind.JAXBContext
import javax.xml.bind.annotation.*

@Service
class ProductMasterDataRepository {

    /**
     * an actual service prohably wouldn't store
     * large data sets in memory
     * instead fetch on demand from database, another system ...
     */
    lateinit var masterData: Map<String, ProductMasterData>

    @PostConstruct
    internal fun loadAllProducts() {

        val context = JAXBContext.newInstance(XmlProducts::class.java)

        val xmlProducts = javaClass.getResourceAsStream("/products/products.xml").use {
            context.createUnmarshaller().unmarshal(it)
        } as XmlProducts

        masterData = xmlProducts.product.map {
            ProductMasterData(it.id, it.name, it.description, it.sku)
        }.associate {
            Pair(it.id, it)
        }
    }

    fun findAllMasterData(): Collection<ProductMasterData> = masterData.values

    fun findMasterDataById(id: String): ProductMasterData? = masterData[id]
}


// i use an extra datamodel here for communicating with the products
// this allows us to shield the main logic from unwanted implementation details
// e.g. use of xml specific annotations, inability to use kotlin dataclass as jaxb classes need a no-args ctor


@XmlRootElement(name = "Products")
@XmlAccessorType(XmlAccessType.FIELD)
class XmlProducts {
    @XmlElement(name = "Product")
    var product: MutableList<XmlProduct> = mutableListOf()
}

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class XmlProduct {
    @XmlAttribute
    var id: String = ""

    @XmlElement(name = "Name")
    var name: String = ""

    @XmlElement(name = "Description")
    var description: String = ""

    @XmlElement
    var sku: String = ""
}