package com.fiberhome.practice;



import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.fiberhome.practice.entity.User;

public class IndexFiles {
	public static Version luceneVersion = Version.LATEST;

	/*
	 * 建立索引
	 */
	public static void createIndex() throws IOException {
		IndexWriter writer = null;
		// 1,创建directory,放置索引的位置
		// Directory directory=new RAMDirectory(); 将索引存在内存之中
		Directory directory = FSDirectory.open(Paths.get("./indexDir"));
		// 2,创建IndexWriter,分词器
		Analyzer analyzer = new StandardAnalyzer();
		writer = new IndexWriter(directory, new IndexWriterConfig(analyzer));
		// 3,创建document对象
		Document document = null;
		User user = new User();
		user.setContent("lucene很牛逼");
		user.setId("2");
		user.setName("lucene");
		Field idField = new StringField("id", user.getId(), Store.YES);
		Field nameField = new TextField("name", user.getName(), Store.YES);
		Field contentField = new TextField("content", user.getContent(), Store.YES);
		document = new Document();
		//向document里面添加field
		document.add(idField);
		document.add(nameField);
		document.add(contentField);
		writer.addDocument(document);
		writer.close();
		

	}

	public static void main(String[] args) throws IOException {
		createIndex();
	}
}
