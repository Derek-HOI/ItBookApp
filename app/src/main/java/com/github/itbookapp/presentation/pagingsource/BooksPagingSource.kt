package com.github.itbookapp.presentation.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.itbookapp.data.model.Books
import com.github.itbookapp.data.model.extract
import com.github.itbookapp.data.model.pagingCount
import com.github.itbookapp.domain.usecase.SearchUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class BooksPagingSource(
    private val query: String,
    private val dispatcher: CoroutineDispatcher,
    private val searchUseCase: SearchUseCase
) : PagingSource<Int, Books>() {

    private var totalCount = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Books> {
        return try {
            val currentPage = params.key ?: 1

            val result = withContext(dispatcher) {
                searchUseCase(SearchUseCase.Params(query, currentPage))
            }
            result.extract()?.total?.let {
                if (totalCount != it) totalCount = it
            }

            val prevPage = if (currentPage == 1) {
                null
            } else {
                currentPage.minus(1)
            }
            val nextPage = if (totalCount > 0 && currentPage * pagingCount > totalCount) {
                null
            } else {
                currentPage.plus(1)
            }

            LoadResult.Page(
                data = result.extract()?.books ?: listOf(),
                prevKey = prevPage,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Books>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}