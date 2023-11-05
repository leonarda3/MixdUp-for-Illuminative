package com.abhinav12k.MixdUp.domain.usecase.getDrinkCardsFromFavorite

import com.abhinav12k.MixdUp.domain.repository.DrinkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetDrinkCardsFromFavorites @Inject constructor(
    private val repository: DrinkRepository
) {
    operator fun invoke() = repository.getAllDrinkCardsFromFavorites().flowOn(Dispatchers.IO)
}