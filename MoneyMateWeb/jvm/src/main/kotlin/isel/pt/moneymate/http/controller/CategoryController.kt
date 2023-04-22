package isel.pt.moneymate.http.controller

import isel.pt.moneymate.domain.User
import isel.pt.moneymate.http.models.categories.CreateCategoryDTO
import isel.pt.moneymate.http.models.categories.UpdateCategoryDTO
import isel.pt.moneymate.services.CategoryService
import isel.pt.moneymate.http.utils.Uris
import jakarta.validation.Valid
import jakarta.validation.constraints.Digits
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class CategoryController(private val categoryService : CategoryService) {
    @PostMapping(Uris.Category.CREATE)
    fun createCategory(@Valid @RequestBody categoryData: CreateCategoryDTO, user: User) : ResponseEntity<*> {
        val category = categoryService.createCategory(categoryData, user.id)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(category)
    }

    @GetMapping(Uris.Category.GET_CATEGORIES)
    fun getCategories(
        @RequestParam(defaultValue = "0") offset: Int,
        @RequestParam(defaultValue = "10") limit: Int
    ): ResponseEntity<*> {
        val categories = categoryService.getCategories(offset, limit)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(categories)
    }

    @GetMapping(Uris.Category.GET_BY_ID)
    fun getCategoryById(@PathVariable categoryId: Int) : ResponseEntity<*>{
        val category = categoryService.getCategoryById(categoryId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(category)
    }
    @PatchMapping(Uris.Category.UPDATE)
    fun updateCategory(@Valid @RequestBody categoryData: UpdateCategoryDTO, @PathVariable categoryId: Int): ResponseEntity<*> {
        val category = categoryService.updateCategory(categoryData, categoryId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(category)
    }
    @DeleteMapping(Uris.Category.DELETE_BY_ID)
    fun deleteCategory(@PathVariable categoryId: Int) : ResponseEntity<*>{
        categoryService.deleteCategory(categoryId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Category $categoryId was deleted successfully!")
    }
}
