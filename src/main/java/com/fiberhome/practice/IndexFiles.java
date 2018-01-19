package com.fiberhome.practice;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;



import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class IndexFiles {
public static Version luceneVersion=Version.LATEST;

/*
 * 建立索引
 */
public static void createIndex() throws IOException{
	IndexWriter writer=null;
	//1,创建directory,放置索引的位置
	Directory directory=FSDirectory.open(Paths.get("E:/luceneTest/index"));
	//2,创建IndexWriter
	IndexWriterConfig indexWriterConfig=new IndexWriterConfig(new StandardAnalyzer());
	writer=new IndexWriter(directory, indexWriterConfig);
	//3,创建document对象
	Document document=null;
	//4,未document对象添加field对象
	File files=new File("E:/luceneTest/files");//需要索引的文件
	for(File file:files.listFiles()){
		document=new Document();
		document.add(new StringField("path", file.getName(), Field.Store.YES));
        System.out.println(file.getName());
        document.add(new StringField("name", file.getName(),Field.Store.YES));
        InputStream stream = Files.newInputStream(Paths.get(file.toString()));
        document.add(new TextField("content", new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))));
        writer.addDocument(document);
	}
	writer.close();
			
}

public static void main(String[] args) throws IOException {
	createIndex();
}
}
