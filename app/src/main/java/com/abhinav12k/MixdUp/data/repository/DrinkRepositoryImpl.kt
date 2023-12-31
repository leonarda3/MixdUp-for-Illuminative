package com.abhinav12k.MixdUp.data.repository

import com.abhinav12k.MixdUp.data.local.dao.DrinkCardDao
import com.abhinav12k.MixdUp.data.remote.CocktailApi
import com.abhinav12k.MixdUp.data.remote.dto.DrinksDto
import com.abhinav12k.MixdUp.data.remote.dto.IngredientsDto
import com.abhinav12k.MixdUp.domain.model.DrinkCard
import com.abhinav12k.MixdUp.domain.repository.DrinkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DrinkRepositoryImpl @Inject constructor(
    private val api: CocktailApi,
    private val drinkCardDao: DrinkCardDao
) : DrinkRepository {

    override suspend fun getAllCategories(): DrinksDto {
        return api.getAllCategories()
    }

    override suspend fun getDrinksBasedOnCategory(category: String): DrinksDto {
        return api.getDrinksBasedOnCategory(category)
    }

    override suspend fun getDrinkById(id: String): DrinksDto {
        return api.getDrinkById(id)
    }

    override suspend fun getDrinksByName(drinkName: String): DrinksDto {
        return api.getDrinksByName(drinkName)
    }

    override suspend fun getRandomDrink(): DrinksDto {
        return api.getRandomDrink()
    }

    override suspend fun addDrinkCardToFavorite(drinkCard: DrinkCard) {
        return drinkCardDao.addDrinkCardToFavorites(drinkCard)
    }

    override suspend fun removeDrinkCardFromFavorite(drinkId: String) {
        drinkCardDao.removeDrinkCardFromFavorites(drinkId)
    }

    override fun getAllDrinkCardsFromFavorites(): Flow<List<DrinkCard>> {
        return drinkCardDao.getAllDrinkCards()
    }

    override suspend fun isDrinkCardAddedToFavorites(id: String): Boolean {
        return drinkCardDao.isDrinkCardAddedToFavorite(id) != null
    }

    override suspend fun getIngredientDetailsByName(ingredientName: String): IngredientsDto {
        return api.getIngredientDetailsByName(ingredientName)
    }
}