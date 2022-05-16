package com.bookaro.api.pagination;

import java.util.List;

import com.bookaro.api.models.Book;

public interface IBookService {	
	List<Book> findPaginated (int pageNo, int pageSize);
}
