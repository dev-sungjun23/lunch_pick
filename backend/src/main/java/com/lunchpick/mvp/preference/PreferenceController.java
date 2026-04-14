package com.lunchpick.mvp.preference;

import com.lunchpick.mvp.dto.PreferenceResponse;
import com.lunchpick.mvp.dto.PreferenceSaveRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/preferences")
public class PreferenceController {

    private final PreferenceService preferenceService;

    public PreferenceController(PreferenceService preferenceService) {
        this.preferenceService = preferenceService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PreferenceResponse savePreference(@Valid @RequestBody PreferenceSaveRequest request) {
        return preferenceService.savePreference(request);
    }
}
