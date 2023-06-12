import {authHeader} from "./AuthHeader.jsx";
import instance from "./AxiosInterceptor.jsx";

class CategoryService {
    async createCategory(name) {
        try {
            const form = { name }
            const response = await instance.post("/api/categories", form,{headers: authHeader()})
            console.log(response.data)
            return response.data
        } catch (error) {
            console.error('Error createing category', error);
        }
    }

    getCategoriesOfUser() {
        return this.basicGet("/api/categories")
    }

    getSystemCategories() {
        return this.basicGet("/api/systemcategories")
    }

    async updateCategory(categoryId, name) {
        try {
            const form = { name }
            const response = await instance.patch(`/api/categories/${categoryId}`, form, {headers: authHeader()})
            console.log(response.data)
            return response.data
        } catch (error) {
            console.error('Error updating category with Id: '+ categoryId, error);
        }
    }

    async deleteCategory(categoryId) {
        try {
            const response = await instance.delete(`/api/categories/${categoryId}`, {headers: authHeader()})
            console.log(response.data)
            return response.data
        } catch (error) {
            console.error('Error deleting category with Id: '+ categoryId, error);
        }
    }
    async basicGet(url){
        try {
            const response = await instance.get(url, {headers: authHeader()})
            console.log(response.data)
            return response.data
        } catch (error) {
            console.error('Error getting categories of user:', error);
        }
        /*
        return instance.get(url, {headers: authHeader()})
            .then(response => {
                return response.data
            })

         */
    }
}

export default new CategoryService();