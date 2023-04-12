package isel.pt.moneymate.controller

import isel.pt.moneymate.controller.models.CategoryInputModel
import isel.pt.moneymate.services.CategoryService
import isel.pt.moneymate.utils.Uris
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class CategoryController(private val categoryService : CategoryService) {
    @PostMapping(Uris.Category.CREATE)
    fun createCategory(@RequestBody categoryData: CategoryInputModel) : ResponseEntity<*> {
        val category = categoryService.createCategory(categoryData.toCategoryInputDTO())
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(category)
    }
    @GetMapping(Uris.Category.GET_CATEGORIES)
    fun getCategories(): ResponseEntity<*> {
        val categories = categoryService.getCategories()
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(categories)
    }

    @GetMapping(Uris.Category.GET_BY_ID)
    fun getCategoryById(@PathVariable categoryId: Int) : ResponseEntity<*>{
        val category = categoryService.getCategoryById(categoryId = categoryId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(category)
    }
    @PatchMapping(Uris.Category.UPDATE)
    fun updateCategory(@RequestBody categoryData: CategoryInputModel,@PathVariable categoryId: Int): ResponseEntity<*> {
        val category = categoryService.updateCategory(categoryData.toCategoryInputDTO(),categoryId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(category)
    }
    @DeleteMapping(Uris.Category.DELETE_BY_ID)
    fun deleteCategory(@PathVariable categoryId: Int) : ResponseEntity<*>{
        categoryService.deleteCategory(categoryId = categoryId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Category ${categoryId} was deleted successfully!")
    }
}
