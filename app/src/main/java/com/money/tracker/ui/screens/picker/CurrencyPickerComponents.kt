package com.money.tracker.ui.screens.picker

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.money.tracker.ui.screens.Currency
import java.util.Locale

private data class LetterDivider(
    val letter: String
)

@Composable
fun CurrencyPicker(
    modifier: Modifier = Modifier,
    initialSelectedCurrency: Currency?,
    preselectedCurrency: Currency = Currency.getDefault(),
    includeKeyboardShownInsetSpacer: Boolean,
    lastItemSpacer: Dp = 0.dp,
    onKeyboardShown: (keyboardVisible: Boolean) -> Unit = {},
    onSelectedCurrencyChanged: (Currency) -> Unit
) {
    var keyboardShownState by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    val keyboardShownInsetDp by animateDpAsState(
        targetValue = if (keyboardShownState && includeKeyboardShownInsetSpacer) 56.dp else 0.dp,
        animationSpec = tween(300)
    )

    Column(
        modifier = modifier
    ) {
        var preselected by remember {
            mutableStateOf(initialSelectedCurrency == null)
        }
        var selectedCurrency by remember {
            mutableStateOf(initialSelectedCurrency ?: preselectedCurrency)
        }
        var searchTextFieldValue by remember { mutableStateOf(TextFieldValue("")) }

        if (!keyboardShownState) {
            SelectedCurrencyCard(
                currency = selectedCurrency,
                preselected = preselected
            )
            Spacer(Modifier.height(20.dp))
        }

        SearchInput(
            searchTextFieldValue = searchTextFieldValue,
            onFocusChanged = { focused ->
                keyboardShownState = focused
                onKeyboardShown(focused)
            }
        ) {
            searchTextFieldValue = it
        }

        Spacer(Modifier.height(20.dp))

        CurrencyList(
            searchQueryLowercase = searchTextFieldValue.text.lowercase(Locale.getDefault()),
            selectedCurrency = selectedCurrency,
            lastItemSpacer = lastItemSpacer + keyboardShownInsetDp,
        ) {
            preselected = false
            selectedCurrency = it
            onSelectedCurrencyChanged(it)
            keyboardController?.hide()
            keyboardShownState = false
            onKeyboardShown(false)
        }
    }
}

@Composable
private fun SearchInput(
    searchTextFieldValue: TextFieldValue,
    onFocusChanged: (Boolean) -> Unit,
    onSetSearchTextFieldValue: (TextFieldValue) -> Unit
) {
    val inputFocus = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(50.dp))
            .border(2.dp, Color.Gray, RoundedCornerShape(50.dp))
            .clickable {
                inputFocus.requestFocus()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.width(16.dp))
        Icon(
            modifier = Modifier.padding(vertical = 8.dp),
            imageVector = Icons.Filled.Search,
            contentDescription = "Search"
        )
        Spacer(Modifier.width(8.dp))
        Box(
            contentAlignment = Alignment.CenterStart
        ) {
            if (searchTextFieldValue.text.isEmpty()) {
                Text(
                    text = "Search Currency",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                )
            }
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .focusRequester(inputFocus)
                    .testTag("search_input")
                    .onFocusChanged { focusState -> onFocusChanged(focusState.isFocused) },
                value = searchTextFieldValue,
                onValueChange = {
                    onSetSearchTextFieldValue(it.copy(text = it.text.trim()))
                },
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = Color.Black
                ),
                singleLine = true,
                cursorBrush = SolidColor(Color.Black),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                        onFocusChanged(false)
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
            )
        }
    }
}

@Composable
private fun SelectedCurrencyCard(
    currency: Currency,
    preselected: Boolean,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = if (preselected) listOf(
                        Color.Green,
                        Color.Cyan
                    ) else listOf(Color.Blue, Color.Magenta)
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.width(24.dp))
        Column {
            Text(
                text = currency.name,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = currency.code,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold
                )
            )
        }
        Spacer(Modifier.weight(1f))
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = "Selected",
            tint = Color.White
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = if (preselected) "Pre-selected" else "Selected",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        )
        Spacer(Modifier.width(32.dp))
    }
}

@Composable
private fun CurrencyList(
    searchQueryLowercase: String,
    selectedCurrency: Currency,
    lastItemSpacer: Dp,
    onCurrencySelected: (Currency) -> Unit
) {
    val currencies = Currency.getAvailable()
        .filter {
            searchQueryLowercase.isBlank() ||
                    it.code.lowercase(Locale.getDefault()).startsWith(searchQueryLowercase) ||
                    it.name.lowercase(Locale.getDefault()).startsWith(searchQueryLowercase)
        }
        .sortedBy { it.code }
        .sortedBy { it.isCrypto }

    val currenciesWithLetters = mutableListOf<Any>()
    var lastFirstLetter: String? = null
    for (currencyItem in currencies) {
        val firstLetter =
            if (currencyItem.isCrypto) "Crypto" else currencyItem.code.first().toString()
        if (firstLetter != lastFirstLetter) {
            currenciesWithLetters.add(
                LetterDivider(
                    letter = firstLetter
                )
            )
            lastFirstLetter = firstLetter
        }
        currenciesWithLetters.add(currencyItem)
    }

    val listState = remember(searchQueryLowercase, selectedCurrency) {
        LazyListState(
            firstVisibleItemIndex = 0,
            firstVisibleItemScrollOffset = 0
        )
    }

    LazyColumn(
        state = listState
    ) {
        itemsIndexed(currenciesWithLetters) { index, item ->
            when (item) {
                is Currency -> {
                    CurrencyItemCard(
                        currency = item,
                        selected = item.code == selectedCurrency.code
                    ) {
                        onCurrencySelected(item)
                    }
                }

                is LetterDivider -> {
                    LetterDividerItem(
                        spacerTop = if (index == 0) 12.dp else 32.dp,
                        letterDivider = item
                    )
                }
            }
        }
        if (lastItemSpacer.value > 0) {
            item {
                Spacer(Modifier.height(lastItemSpacer))
            }
        }
    }
}

@Composable
private fun CurrencyItemCard(
    currency: Currency,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Spacer(Modifier.height(12.dp))
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                color = if (selected) Color.Magenta else Color.LightGray,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
            .padding(vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.width(24.dp))
        Text(
            text = currency.code,
            style = MaterialTheme.typography.titleMedium.copy(
                color = if (selected) Color.White else Color.Black,
                fontWeight = FontWeight.ExtraBold
            )
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = currency.name.take(20),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = if (selected) Color.White else Color.Black,
                fontWeight = FontWeight.SemiBold
            )
        )
        Spacer(Modifier.width(32.dp))
    }
}

@Composable
private fun LetterDividerItem(
    spacerTop: Dp,
    letterDivider: LetterDivider
) {
    if (spacerTop > 0.dp) {
        Spacer(Modifier.height(spacerTop))
    }
    Text(
        modifier = Modifier.padding(start = 32.dp),
        text = letterDivider.letter,
        style = MaterialTheme.typography.bodySmall.copy(
            color = Color.DarkGray,
            fontWeight = FontWeight.SemiBold
        )
    )
    Spacer(Modifier.height(6.dp))
}
