package com.main.psoos.controller;

import com.main.psoos.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/barcode")
@ResponseBody
public class DownloadController {

    @Autowired
    OrderService orderService;


    @GetMapping("/downloadss/{joId}")
    public HttpEntity<byte[]> downloadImageByName(@PathVariable("joId") String name){
        byte[] image = orderService.getImage(name);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.IMAGE_PNG);
        header.setContentLength(image.length);

        return new HttpEntity<byte[]>(image, header);
    }



}
