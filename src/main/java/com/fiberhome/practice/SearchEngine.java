package com.fiberhome.practice;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
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
		Directory directory = FSDirectory.open(Paths.get("E:/luceneTest/index"));
		// 读取索引
		directoryReader = DirectoryReader.open(directory);
		// 创建search
		IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
		// 创建搜索的query
		// 创建parse用来确定搜索的内容，第二个参数表示搜索的域
		QueryParser parser = new QueryParser("content", new StandardAnalyzer());// content表示搜索的域或者说字段

		Query query = parser.parse(tag);
		TopDocs topDocs = indexSearcher.search(query, 20);
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;

		for (ScoreDoc scoreDoc : scoreDocs) {
	    Document document=indexSearcher.doc(scoreDoc.doc);
        
		System.out.println( document.get("name")+document.get("path")+document.get("content"));
		}
		
	}
	public static void main(String[] args) throws IOException, ParseException {
		indexSearch("你");
	}
}