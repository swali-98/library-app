package com.swaliproject.springbootlibrary.controller;


import com.swaliproject.springbootlibrary.entity.Book;
import com.swaliproject.springbootlibrary.responsemodel.ShelfCurrentLoansResponse;
import com.swaliproject.springbootlibrary.service.BookService;
import com.swaliproject.springbootlibrary.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService){
        this.bookService=bookService;
    }

    @GetMapping("/secure/checkedout/byuser")
    public boolean checkoutByUser(@RequestHeader(value = "Authorization") String token,@RequestParam Long bookId){
        String userEmail = ExtractJWT.payLoadJWTExtraction(token,"\"sub\"");
        return bookService.checkoutByUser(userEmail,bookId);
    }

    @GetMapping("/secure/currentloans/count")
    public int currentLoansCount(@RequestHeader(value = "Authorization") String token){
        String userEmail = ExtractJWT.payLoadJWTExtraction(token,"\"sub\"");
        return bookService.currentLoansCount(userEmail);
    }

    @PutMapping("/secure/checkout")
    public Book checkoutBook(@RequestHeader(value = "Authorization") String token,@RequestParam Long bookId) throws Exception{
        String userEmail = ExtractJWT.payLoadJWTExtraction(token,"\"sub\"");

        return bookService.checkoutBook(userEmail,bookId);
    }

    @GetMapping("/secure/currentloans")
    public List<ShelfCurrentLoansResponse> currentLoans(@RequestHeader(value = "Authorization") String token) throws Exception{
        String userEmail = ExtractJWT.payLoadJWTExtraction(token ,"\"sub\"");

        return bookService.currentLoans(userEmail);
    }

    @PutMapping("/secure/return")
    public void returnBook(@RequestHeader(value = "Authorization") String token, @RequestParam Long bookId) throws Exception{
        String userEmail = ExtractJWT.payLoadJWTExtraction(token,"\"sub\"");

        bookService.returnBook(userEmail,bookId);
    }

    @PutMapping("/secure/renew/loan")
    public void renewLoan(@RequestHeader(value="Authorization") String token , @RequestParam Long bookId) throws Exception{
        String userEmail = ExtractJWT.payLoadJWTExtraction(token,"\"sub\"");
        bookService.renewLoan(userEmail,bookId);
    }

}
