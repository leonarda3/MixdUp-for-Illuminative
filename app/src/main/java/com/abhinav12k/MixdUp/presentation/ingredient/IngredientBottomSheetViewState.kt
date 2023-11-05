package com.abhinav12k.MixdUp.presentation.ingredient

import com.abhinav12k.MixdUp.domain.model.IngredientDetail

data class IngredientBottomSheetViewState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val ingredientDetail: IngredientDetail? = null
)
