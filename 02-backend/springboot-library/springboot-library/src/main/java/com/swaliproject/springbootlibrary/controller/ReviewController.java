package com.swaliproject.springbootlibrary.controller;

import com.swaliproject.springbootlibrary.requestmodel.ReviewRequest;
import com.swaliproject.springbootlibrary.service.ReviewService;
import com.swaliproject.springbootlibrary.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private ReviewService reviewService;

    @Autowired
    public ReviewController (ReviewService reviewService){
        this.reviewService = reviewService ;
    }

    @PostMapping("/secure")
    public void postReview (@RequestHeader(value = "Authorization") String token , @RequestBody ReviewRequest reviewRequest)throws Exception{
        String userEmail = ExtractJWT.payLoadJWTExtraction(token , "\"sub\"");
        if(userEmail == null){
            throw new Exception("user email is missing");
        }

        reviewService.postReview(userEmail,reviewRequest);
    }

    @GetMapping ("/secure/user/book")
    public Boolean reviewBookByUser (@RequestHeader(value = "Authorization") String token , @RequestParam Long bookId) throws Exception {
        String userEmail = ExtractJWT.payLoadJWTExtraction(token,"\"sub\"");
        if(userEmail == null){
            throw new Exception("user email is missing");
        }
        return reviewService.userReviewListed(userEmail,bookId);
    }
}
