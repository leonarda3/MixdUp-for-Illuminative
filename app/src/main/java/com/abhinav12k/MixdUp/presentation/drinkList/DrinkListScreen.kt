package com.abhinav12k.MixdUp.presentation.drinkList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.abhinav12k.MixdUp.R
import com.abhinav12k.MixdUp.common.BackPressHandler
import com.abhinav12k.MixdUp.domain.model.Category
import com.abhinav12k.MixdUp.domain.model.DrinkCard
import com.abhinav12k.MixdUp.presentation.drinkList.components.*
import com.abhinav12k.MixdUp.presentation.drinkList.model.DrinkSection
import com.abhinav12k.MixdUp.presentation.favoriteScreen.FavoriteDrinksViewModel
import com.abhinav12k.MixdUp.presentation.ui.theme.Pink200
import com.abhinav12k.MixdUp.presentation.ui.theme.Pink900

@Composable
fun DrinkListScreen(
    viewModel: DrinkListViewModel,
    favoriteDrinksViewModel: FavoriteDrinksViewModel,
    onDrinkCardClicked: (drinkId: String) -> Unit,
    onViewAllClicked: () -> Unit
) {
    val (text, changeSearchBarText) = rememberSaveable {
        mutableStateOf("")
    }
    val viewState = rememberSaveable {
        viewModel.drinkListViewState
    }
    val favoriteDrinks = rememberSaveable {
        favoriteDrinksViewModel.favoriteDrinks
    }

    val onBackPressedInCaseUserNavigatedViaSearch = {
        viewModel.showDrinkSections()
        favoriteDrinksViewModel.showFavoritesSection()
        changeSearchBarText("")
    }

    if (!viewState.value.searchDrinkCards.isNullOrEmpty()) {
        BackPressHandler {
            onBackPressedInCaseUserNavigatedViaSearch.invoke()
        }
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Pink200)
        ) {
            DrinkListScreenContent(
                categories = null,
                searchDrinkCards = viewState.value.searchDrinkCards,
                drinkSections = viewState.value.drinkSections,
                favoriteDrinks = favoriteDrinks.value,
                searchText = text,
                onSearchTextChanged = {
                    if (it.isNotEmpty()) {
                        favoriteDrinksViewModel.hideFavoriteSection()
                        viewModel.getDrinksBasedOnName(it)
                    } else {
                        viewModel.showDrinkSections()
                        favoriteDrinksViewModel.showFavoritesSection()
                    }
                    changeSearchBarText(it)
                },
                onChipClicked = {
                    viewModel.updateChipSelection(it)
                    viewModel.getDrinkCardsByCategory(it.queryParam)
                },
                onDrinkCardClicked = {
                    onDrinkCardClicked(it)
                },
                onViewAllClicked = onViewAllClicked
            )
            if (viewState.value.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            if (!viewState.value.error.isNullOrBlank()) {
                Text(
                    text = viewState.value.error!!,
                    color = Pink900,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
fun DrinkListScreenContent(
    modifier: Modifier = Modifier,
    categories: List<Category>?,
    searchDrinkCards: List<DrinkCard>?,
    drinkSections: List<DrinkSection>?,
    favoriteDrinks: List<DrinkCard>?,
    searchText: String,
    onSearchTextChanged: (drinkName: String) -> Unit,
    onChipClicked: (category: Category) -> Unit,
    onDrinkCardClicked: (drinkId: String) -> Unit,
    onViewAllClicked: () -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        item {
            SearchBar(
                placeHolder = R.string.search_placeholder,
                value = searchText,
                modifier = modifier.padding(top = 8.dp, bottom = 8.dp),
                onValueChange = {
                    onSearchTextChanged(it)
                }
            )
        }

        if (!favoriteDrinks.isNullOrEmpty() && searchDrinkCards == null) {
            item {
                FavoriteDrinkSection(
                    favoriteDrinks = favoriteDrinks,
                    onClick = {
                        onDrinkCardClicked(it)
                    },
                    onViewAllClicked = onViewAllClicked
                )
            }
        }

        if (categories != null) {
            item {
                HorizontalChipList(
                    categories = categories,
                    modifier = modifier,
                    onChipClicked = {
                        onChipClicked(it)
                    }
                )
            }
        }

        if (searchDrinkCards != null) {
            item {
                DrinkCardsGrid(
                    drinkCards = searchDrinkCards,
                    modifier = modifier,
                    onClick = {
                        onDrinkCardClicked(it)
                    }
                )
            }
        }

        if (drinkSections != null) {
            items(drinkSections) { drinkSection ->
                DrinkSection(
                    modifier = modifier,
                    drinkSection = drinkSection,
                    onDrinkCardClicked = {
                        onDrinkCardClicked(it)
                    }
                )
            }
        }
    }
}