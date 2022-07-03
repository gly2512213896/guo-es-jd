package com.guo.controller;

import com.guo.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
public class ContentController {
    @Resource
    private ContentService contentService;

    @GetMapping("/parse/{keyword}")
    private Boolean parse(@PathVariable("keyword") String keyword) throws IOException {
        // System.out.println(contentService.parseContent(keyword));
        return contentService.parseContent(keyword);
    }
}
