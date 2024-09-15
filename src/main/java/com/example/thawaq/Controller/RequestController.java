package com.example.thawaq.Controller;

import com.example.thawaq.Api.ApiResponse;
import com.example.thawaq.Model.Request;
import com.example.thawaq.Model.User;
import com.example.thawaq.Service.RequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/request")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;

    @GetMapping("/get-all")
    public ResponseEntity getAllRatings() {
        return ResponseEntity.status(200).body(requestService.getAllRequests());
    }

 //Final
    @GetMapping("/get-my-request")
    public ResponseEntity getMyRequestForExpert(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(requestService.getMyRequestForExpert(user.getId()));
    }

    //Final(Ask)
    @PostMapping("/add-request/{expertId}")
    public ResponseEntity addRequest(@Valid @RequestBody Request request,@AuthenticationPrincipal User user, @PathVariable Integer expertId) {
        requestService.addRequest(request,user.getStoreAdmin().getId(),expertId);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully added request !"));
    }

    //Final
    @PutMapping("/update-request/{requestId}")
    public ResponseEntity updateRequest( @Valid @RequestBody Request request,@AuthenticationPrincipal User user, @PathVariable Integer requestId) {
        requestService.updateRequest(request,user.getId(),requestId);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully updated request!"));}


    @DeleteMapping("/delete-request/{requestUd}")
    public ResponseEntity deleteRequest(@AuthenticationPrincipal User user,@PathVariable Integer requestId) {
        requestService.deleteRequest(user.getId(),requestId);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully deleted request!"));}
}
