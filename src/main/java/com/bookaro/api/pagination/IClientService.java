package com.bookaro.api.pagination;

import java.util.List;
import com.bookaro.api.models.Client;

public interface IClientService {
	List<Client> findPaginated (int pageNo, int pageSize);
}
