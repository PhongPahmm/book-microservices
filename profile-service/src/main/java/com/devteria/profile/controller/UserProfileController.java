package com.devteria.profile.controller;

import com.devteria.profile.dto.request.UserProfileCreationRequest;
import com.devteria.profile.dto.response.UserProfileResponse;
import com.devteria.profile.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileController {
    UserProfileService userProfileService;

    @PostMapping("/users")
    public UserProfileResponse createUserProfile(@RequestBody UserProfileCreationRequest request){
        return userProfileService.createUserProfile(request);
    }

    @GetMapping("/users/{userProfileId}")
    public UserProfileResponse getUserProfile(@PathVariable String userProfileId){
        return userProfileService.getUserProfile(userProfileId);
    }
}
