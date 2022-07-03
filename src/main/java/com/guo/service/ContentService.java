package com.guo.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.elasticsearch.core.bulk.IndexOperation;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import com.alibaba.fastjson.JSON;
import com.guo.pojo.Content;
import com.guo.utils.HtmlParseUtil;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.swing.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
public class ContentService {
    @Autowired
    private ElasticsearchClient client;

    public Boolean parseContent(String keywords) throws IOException {
        List<Content> contents = new HtmlParseUtil().parseJD(keywords);
        BulkRequest.Builder bk = new BulkRequest.Builder();
        // 1. operations方法参数为一个传入BulkOperation.Builder，传出BulkOperation的fn接口
        // 也就是说需要一个BulkOperation，然后返回一个BulkRequest.Builder
        // 2. op.index方法参数为一个传入IndexOperation.Builder<TDocument>，传出IndexOperation<TDocument>的fn接口
        // 即需要一个IndexOperation<TDocument>，然后返回ObjectBuilder<BulkOperation>
        // 3. i.index参数为String，返回一个builderT（即任意builder的泛型）
        for (Content content : contents) {
            bk.operations(op -> op.index(i -> i.index("jd_goods")
                    .id(UUID.randomUUID().toString())
                    .document(content)));
        }
        BulkResponse bulkResponse=client.bulk(bk.build());
        if (bulkResponse.errors()) {
            System.out.println("Bulk had errors");
            for (BulkResponseItem item: bulkResponse.items()) {
                if (item.error() != null) {
                    System.out.println(item.error().reason());
                }
            }
        }
        return !bulkResponse.errors();
    }

    // 2. 获取存储的数据
    public List<Map<String,Object>> searchPage(String keyword,int pageNo,int pageSize){
        if(pageNo<=1) pageNo=1;
        

    }

}
