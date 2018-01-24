package com.fiberhome.practice.test;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.util.NamedList;
import org.junit.Test;

import com.fiberhome.practice.entity.Article;
import com.fiberhome.practice.until.SolrUtil;

public class TestSolr {
	@Test
	public void test() {
		Article article = new Article();
		article.setId(UUID.randomUUID().toString());
		article.setName("solr测试sss嘿");
		article.setContent("湖北武汉洪山邮科院11111嘿");
		article.setCreateTime(new Date());
		SolrUtil.saveSolrResource(article);
		try {
			QueryResponse response = SolrUtil.query("嘿");
		//response 里面含有包括高亮的一些信息
			NamedList<Object> namedList = response.getResponse();
			//这里使用highlighting的原因是，这个需要和QueryResponse里面的setResponse的判断相匹配
			NamedList<?> highlight = (NamedList<?>) namedList.get("highlighting");
			for(int i=0;i<highlight.size();i++){
				System.out.println(highlight.getName(i)+highlight.getVal(i));
			}
			List<Article> articles = response.getBeans(Article.class);
			for (Article article2 : articles) {
				System.out.println(article2.getName() + article2.getContent() + article2.getId());

			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	@Test
	public void test2() throws SolrServerException, IOException {
	QueryResponse response=	SolrUtil.query("湖北");
	List<Article> articles = response.getBeans(Article.class);
	System.out.println(articles.size());
		SolrUtil.removeSolrData("e67c0874-3379-41d4-b304-21d09299f788");
		
		QueryResponse response1=	SolrUtil.query("湖北");
		List<Article> articles1 = response1.getBeans(Article.class);
		System.out.println(articles1.size());
	}
}
