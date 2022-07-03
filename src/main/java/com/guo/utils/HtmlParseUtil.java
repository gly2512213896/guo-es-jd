package com.guo.utils;

import com.guo.pojo.Content;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.omg.CORBA.TIMEOUT;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class HtmlParseUtil {
    // public static void main(String[] args) throws IOException {
    //
    //
    // }

    public List<Content> parseJD(String keywords) throws IOException {
        String url= "https://search.jd.com/Search?keyword="+keywords;

        Document document= Jsoup.parse(new URL(url), 30000);
        Element element=document.getElementById("J_goodsList");
        // System.out.println(element.html());
        Elements elements=element.getElementsByTag("li");

        List<Content> contentList=new ArrayList<>();

        for (Element el : elements) {
            String img=el.getElementsByTag("img").eq(0).attr("data-lazy-img");
            String price=el.getElementsByClass("p-price").eq(0).text();
            String title=el.getElementsByClass("p-name").eq(0).text();

            Content content=new Content();
            content.setImg(img);
            content.setPrice(price);
            content.setTitle(title);

            contentList.add(content);
        }

        return contentList;
    }
}
