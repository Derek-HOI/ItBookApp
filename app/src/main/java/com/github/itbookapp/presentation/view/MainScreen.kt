package com.github.itbookapp.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.github.itbookapp.data.model.Books
import com.github.itbookapp.data.model.RequestResult
import com.github.itbookapp.data.model.onSuccess
import com.github.itbookapp.presentation.view.common.CommonTextField
import com.github.itbookapp.presentation.viewmodel.BookViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    bookViewModel: BookViewModel = hiltViewModel()
) {
    val newList = remember { mutableStateListOf<Books>() }
    val searchBooks = bookViewModel.booksPager.collectAsLazyPagingItems()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        var isList by remember { mutableStateOf(true) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Box(modifier = Modifier.weight(1.0F)) {
                CommonTextField(
                    inputText = bookViewModel.query,
                    hint = "검색어를 입력하세요.",
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        // search
                        bookViewModel.invalidatePagingSource()
                    }),
                    doAfterTextChanged = {
                        bookViewModel.query = it
                        if (it.isBlank()) {
                            bookViewModel.invalidatePagingSource()
                        }
                    }
                )
            }
            // list <-> grid toggle
            Switch(checked = isList, onCheckedChange = { isList = it })
        }

        //TODO test
//        var isRefreshing by remember { mutableStateOf(false) }
//        val pullRefreshState = rememberPullRefreshState(
//            refreshing = isRefreshing,
//            onRefresh = {
//                isRefreshing = true
//                bookViewModel.getNew()
//                searchBooks.refresh()
//            }
//        )
        Box(
            modifier = Modifier
                .weight(1.0F)
//                .pullRefresh(pullRefreshState)
        ) {
            when (searchBooks.loadState.refresh) {
                is LoadState.Loading, is LoadState.Error -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (searchBooks.loadState.refresh is LoadState.Loading) {
                            CircularProgressIndicator()
                        } else {
                            Text(
                                text = "Search Error",
                                style = TextStyle(fontSize = 13.sp, color = Color.Black)
                            )
                        }
                    }
                }
                else -> {
                    if (isList) {
                        ListScreen(
                            showNew = bookViewModel.query.isBlank(),
                            newList = newList,
                            searchBooks = searchBooks
                        )
                    } else {
                        GridScreen(
                            showNew = bookViewModel.query.isBlank(),
                            newList = newList,
                            searchBooks = searchBooks
                        )
                    }
                }
            }
//            PullRefreshIndicator(
//                refreshing = isRefreshing,
//                modifier = Modifier.align(Alignment.TopCenter),
//                state = pullRefreshState
//            )
        }
    }

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(key1 = Unit, block = {
        launch {
            bookViewModel.eventFlow.flowWithLifecycle(lifecycle).flowOn(Dispatchers.Default)
                .collectLatest {
                    when (it) {
                        is BookViewModel.BookEvent.GetBooks -> {

                        }
                        is BookViewModel.BookEvent.GetNew -> {
//                            isRefreshing = it.result == RequestResult.Loading
                            it.result.onSuccess { response ->
                                newList.addAll(response.books)
                            }
                        }
                    }
                }
        }
        launch {
            bookViewModel.getNew()
        }
    })

}

@Composable
private fun ListScreen(
    showNew: Boolean,
    newList: List<Books>,
    searchBooks: LazyPagingItems<Books>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        content = {
            if (showNew) {
                items(newList) { item ->
                    ListBookItem(
                        image = item.image,
                        title = item.title ?: "",
                        subtitle = item.subtitle ?: "",
                        price = item.price ?: ""
                    ) {
                        //TODO details
                    }
                }
            } else {
                items(count = searchBooks.itemCount) { idx ->
                    searchBooks[idx]?.let { item ->
                        ListBookItem(
                            image = item.image,
                            title = item.title ?: "",
                            subtitle = item.subtitle ?: "",
                            price = item.price ?: ""
                        ) {
                            //TODO details
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun GridScreen(
    showNew: Boolean,
    newList: List<Books>,
    searchBooks: LazyPagingItems<Books>
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(3),
        content = {
            if (showNew) {
                items(newList) { item ->
                    GridBookItem(
                        image = item.image,
                        title = item.title ?: "",
                        subtitle = item.subtitle ?: "",
                        price = item.price ?: ""
                    ) {
                        //TODO details
                    }
                }
            } else {
                items(count = searchBooks.itemCount) { idx ->
                    searchBooks[idx]?.let { item ->
                        GridBookItem(
                            image = item.image,
                            title = item.title ?: "",
                            subtitle = item.subtitle ?: "",
                            price = item.price ?: ""
                        ) {
                            //TODO details
                        }
                    }
                }
            }
        }
    )
}
