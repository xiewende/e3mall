package e3mall.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import e3mall.common.domain.SearchResult;
import e3mall.search.dao.SearchDao;
import e3mall.search.service.SearchService;
//商品搜索
@Service
public class SearchServiceImpl implements SearchService {
	
	@Autowired
	private SearchDao searchDao;

	public SearchResult search(String keyword, int page, int rows) throws Exception {
		//创建一个Solrquery对象
		SolrQuery query = new SolrQuery();
		//设置查询条件
		query.setQuery(keyword);
		//设置分页条件
		if(page<=0) {page = 1;}
		query.setStart((page-1) * rows);
		query.setRows(rows);
		//设置默认搜索域
		query.set("df", "item_title");
		//开启高亮显示
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		//调用dao执行查询
		SearchResult searchResult = searchDao.search(query);
		long mun = searchResult.getRecourdCount();
		int totalpage = (int) (mun / rows);
		if(mun % rows > 0) {totalpage++;}
		//添加到返回结果
		searchResult.setTotalPages(totalpage);
		return searchResult;
	}

}
