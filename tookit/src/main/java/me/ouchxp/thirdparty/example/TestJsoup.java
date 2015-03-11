package me.ouchxp.thirdparty.example;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestJsoup {
	
	public static void main(String[] args) throws Exception {
		
		// Define data structure
		class Article {
			String href;
			String title;
			
			Article(Element e) {
				this.href = e.attr("href");
				this.title = e.text();
			}
			
			public String toString() {
				return href + " @ " + title;
			}
		}
		
		// Parsing data
		InputStream is =TestJsoup.class.getClassLoader().getResourceAsStream("group.txt");
		Document doc = Jsoup.parse(is, "UTF-8", "http://baseurl.com/");
		Elements articleElems = doc.select("body > div.gwrap > div.gmain > ul.titles > li > div > h4 > a");
		List<Article> articles = articleElems.stream().map(Article::new).collect(Collectors.toList());
		
		// Test output
		articles.forEach(System.out::println);
		
	}
}
