package com.study.fundingkotlin.controller

import com.study.fundingkotlin.data.ProductResponse
import com.study.fundingkotlin.entity.Product
import com.study.fundingkotlin.service.ProductService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(val productService: ProductService) {

    @GetMapping("/api/v1/products")
    fun allProduct(): List<Product> {
        return productService.getAllProduct()
    }

    @GetMapping("/api/v2/products")
    fun allProducts() : List<ProductResponse> {
        return productService.getProducts()
    }

    @GetMapping("/api/v3/products")
    fun getAllProductWithPAgeByQueryMethod(@RequestParam size: Int, @RequestParam page: Int): Page<ProductResponse> {
        return productService.getProductProgress(size, page)
    }

    @GetMapping("/api/v4/products")
    fun getAllProduct(pageable: Pageable?): Slice<ProductResponse> {
        return productService.getAllProducts(pageable!!)
    }
}
