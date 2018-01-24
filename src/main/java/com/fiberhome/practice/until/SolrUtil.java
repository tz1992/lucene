package com.fiberhome.practice.until;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;

public class SolrUtil {
	private static SolrClient solrClient;
	private static String url;

	static {
		url = "http://localhost:8983/solr/persons";
		solrClient = new HttpSolrClient.Builder(url).
				withConnectionTimeout(10000).
				withSocketTimeout(60000).build();
		
	}

	/*
	 * 保存solr数据
	 */
	public static <T> Boolean saveSolrResource(T solrEntity) {
		
		DocumentObjectBinder binder = new DocumentObjectBinder();
		SolrInputDocument document = binder.toSolrInputDocument(solrEntity);
		try {
			solrClient.add(document);
			solrClient.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean removeSolrData(String id) {
		try {
			solrClient.deleteById(id);
			solrClient.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}
	
	public static QueryResponse query(String keywords) throws SolrServerException, IOException{
		SolrQuery query=new SolrQuery();
		query.setQuery("content:"+keywords+"OR name:"+keywords);
		query.setStart(0);
		query.setRows(100);
		query.setHighlight(true);//开启高亮
		query.addHighlightField("name");//设置高亮字段
		query.addHighlightField("content");
		query.setHighlightSimplePre("<font color='red'>");//设置高亮的前缀样式
		query.setHighlightSimplePost("<font>");//设置高亮词的后缀样式
		 /**
         * hl.snippets
         * hl.snippets参数是返回高亮摘要的段数，因为我们的文本一般都比较长，含有搜索关键字的地方有多处，如果hl.snippets的值大于1的话，
         * 会返回多个摘要信息，即文本中含有关键字的几段话，默认值为1，返回含关键字最多的一段描述。solr会对多个段进行排序。
         * hl.fragsize
         * hl.fragsize参数是摘要信息的长度。默认值是100，这个长度是出现关键字的位置向前移6个字符，再往后100个字符，取这一段文本。
         */
		QueryResponse queryResponse=solrClient.query(query);
		
		
		return queryResponse;
	}
}
