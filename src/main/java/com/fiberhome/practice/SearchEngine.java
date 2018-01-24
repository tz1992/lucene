package com.fiberhome.practice;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class SearchEngine {
	public static Version luceneVersion = Version.LATEST;

	public static void indexSearch(String tag) throws IOException, ParseException {

		DirectoryReader directoryReader = null;
		// 获取索引
		Directory directory = FSDirectory.open(Paths.get("./indexDir"));
		// 读取索引
		directoryReader = DirectoryReader.open(directory);
		// 创建search
		IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
		// 创建搜索的query
		// 创建parse用来确定搜索的内容,也就是field的String name这个参数，第二个参数表示搜索的域
		QueryParser parser = new QueryParser("id", new StandardAnalyzer());
         
		Query query = parser.parse(tag);
		
		TopDocs topDocs = indexSearcher.search(query, 20);
		//和这个关键词有关的field数量
		int count =topDocs.totalHits;
		//和这个关键词搜索的相关度
		float scores=topDocs.getMaxScore();
		
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		
		for (ScoreDoc scoreDoc : scoreDocs) {
			//根据scoreDocs的doc 获取到这个document
		Document document=	indexSearcher.doc(scoreDoc.doc);
		
		System.out.println(document.get("id")+document.get("name")+document.get("content"));
		}
		
		
	}

		
	public static void main(String[] args) throws IOException, ParseException {
		indexSearch("2");
	}
}