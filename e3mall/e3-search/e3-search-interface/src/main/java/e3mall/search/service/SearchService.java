package e3mall.search.service;

import e3mall.common.domain.SearchResult;

public interface SearchService {
	
	SearchResult search(String keyword,int page,int rows)throws Exception;

}
