package com.swaliproject.springbootlibrary.controller;

import com.swaliproject.springbootlibrary.entity.Message;
import com.swaliproject.springbootlibrary.requestmodel.AdminQuestionRequest;
import com.swaliproject.springbootlibrary.service.MessagesService;
import com.swaliproject.springbootlibrary.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/messages")
public class MessagesController {

    private MessagesService messagesService;

    @Autowired
    public MessagesController(MessagesService messagesService){
        this.messagesService=messagesService;
    }

    @PostMapping("/secure/add/message")
    public void postMessage (@RequestHeader(value = "Authorization") String token , @RequestBody Message  messageRequest){
        String userEmail = ExtractJWT.payLoadJWTExtraction(token,"\"sub\"");
        messagesService.postMessage(messageRequest,userEmail);
    }

    @PutMapping("/secure/admin/message")
    public void putMessage(@RequestHeader(value = "Authorization") String token , @RequestBody AdminQuestionRequest adminQuestionRequest) throws Exception{
        String userEmail = ExtractJWT.payLoadJWTExtraction(token,"\"sub\"");
        String admin = ExtractJWT.payLoadJWTExtraction(token,"\"userType\"");
        if(admin==null || !admin.equals("admin")){
            throw new Exception ("Administration page only");
        }

        messagesService.putMessage(adminQuestionRequest,userEmail);
    }
}
