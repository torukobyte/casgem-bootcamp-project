package com.torukobyte.bootcampproject.api.controllers.users;

import com.torukobyte.bootcampproject.business.abstracts.users.ApplicantService;
import com.torukobyte.bootcampproject.business.constants.Paths;
import com.torukobyte.bootcampproject.business.dto.requests.users.ChangeUserPasswordRequest;
import com.torukobyte.bootcampproject.business.dto.requests.users.applicants.CreateApplicantRequest;
import com.torukobyte.bootcampproject.business.dto.requests.users.applicants.UpdateApplicantRequest;
import com.torukobyte.bootcampproject.business.dto.responses.users.applicants.CreateApplicantResponse;
import com.torukobyte.bootcampproject.business.dto.responses.users.applicants.GetAllApplicantResponse;
import com.torukobyte.bootcampproject.business.dto.responses.users.applicants.GetApplicantResponse;
import com.torukobyte.bootcampproject.business.dto.responses.users.applicants.UpdateApplicantResponse;
import com.torukobyte.bootcampproject.core.util.results.DataResult;
import com.torukobyte.bootcampproject.core.util.results.Result;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(Paths.apiV1 + "applicants")
public class ApplicantsController {
    private ApplicantService service;

    @GetMapping
    DataResult<List<GetAllApplicantResponse>> getAll() {
        return service.getAll();
    }

    @PostMapping
    @Valid
    @ResponseStatus(HttpStatus.CREATED)
    DataResult<CreateApplicantResponse> add(@Valid @RequestBody CreateApplicantRequest request) {
        return service.add(request);
    }

    @GetMapping("/{id}")
    DataResult<GetApplicantResponse> getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    DataResult<UpdateApplicantResponse> update(
            @Valid @RequestBody UpdateApplicantRequest request,
            @PathVariable int id) {
        return service.update(request, id);
    }

    @DeleteMapping("/{id}")
    Result delete(@PathVariable int id) {
        return service.delete(id);
    }

    @PostMapping("/be-an-applicant/{id}")
    DataResult<GetApplicantResponse> beAnApplicant(@RequestParam String about, @PathVariable int id) {
        return service.beAnApplicant(about, id);
    }

    @DeleteMapping("/remove-an-applicant/{id}")
    Result removeAnApplicant(@PathVariable int id) {
        return service.removeAnApplicant(id);
    }

    @PutMapping("/change-password/{id}")
    Result changePassword(@RequestBody ChangeUserPasswordRequest request, @PathVariable int id) {
        return service.changePassword(request, id);
    }
}
