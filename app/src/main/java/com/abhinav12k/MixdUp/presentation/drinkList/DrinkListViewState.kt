package com.abhinav12k.MixdUp.presentation.drinkList

import android.os.Parcelable
import com.abhinav12k.MixdUp.domain.model.Category
import com.abhinav12k.MixdUp.domain.model.DrinkCard
import com.abhinav12k.MixdUp.presentation.drinkList.model.DrinkSection
import kotlinx.parcelize.Parcelize

@Parcelize
data class DrinkListViewState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchDrinkCards: List<DrinkCard>? = null,
    val categories: List<Category>? = null,
    val drinkSections: List<DrinkSection>? = null
) : Parcelable