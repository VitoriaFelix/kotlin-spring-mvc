package com.eprogramar.blog.service

import com.eprogramar.blog.model.Category
import com.eprogramar.blog.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {

    fun findAll(): List<Category> {
        return categoryRepository.findAll()
    }

}