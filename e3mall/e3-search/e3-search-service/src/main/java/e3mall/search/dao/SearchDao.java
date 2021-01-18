package e3mall.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import e3mall.common.domain.SearchItem;
import e3mall.common.domain.SearchResult;

@Repository
public class SearchDao {
	
	@Autowired
	private SolrServer solrserver;
	
	//根据查询条件查询索引库
	public SearchResult search(SolrQuery query) throws SolrServerException {
		//根据query查询索引库
		QueryResponse queryResponse = solrserver.query(query);
		//取查询结果
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		//取查询结果总记录数
		long mun =  solrDocumentList.getNumFound();
		SearchResult result = new SearchResult();
		result.setRecourdCount(mun);
		//取商品列表，需要高亮显示
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		List<SearchItem> itemList = new ArrayList<>();
		for(SolrDocument solrDocument : solrDocumentList) {
			SearchItem item = new SearchItem();
			item.setCategory_name((String) solrDocument.get("item_category_name"));
			item.setId((String) solrDocument.get("id"));
			item.setImage((String) solrDocument.get("item_image"));
			item.setPrice((long) solrDocument.get("item_price"));
			item.setSell_point((String) solrDocument.get("item_sell_point"));
			
			//取高亮显示
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title = "";
			if (list !=null && list.size() > 0 ) {
				title = list.get(0);
			} else {
				title = (String) solrDocument.get("item_title");
			}
			item.setTitle(title);
			itemList.add(item);
		}
		result.setItemList(itemList);
		return result;
		
	}

}
