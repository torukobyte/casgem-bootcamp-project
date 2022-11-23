package com.torukobyte.bootcampproject.business.concretes.users;

import com.torukobyte.bootcampproject.business.abstracts.users.ApplicantService;
import com.torukobyte.bootcampproject.business.abstracts.users.EmployeeService;
import com.torukobyte.bootcampproject.business.constants.Messages;
import com.torukobyte.bootcampproject.business.dto.requests.users.applicants.CreateApplicantRequest;
import com.torukobyte.bootcampproject.business.dto.requests.users.applicants.UpdateApplicantRequest;
import com.torukobyte.bootcampproject.business.dto.responses.users.applicants.CreateApplicantResponse;
import com.torukobyte.bootcampproject.business.dto.responses.users.applicants.GetAllApplicantResponse;
import com.torukobyte.bootcampproject.business.dto.responses.users.applicants.GetApplicantResponse;
import com.torukobyte.bootcampproject.business.dto.responses.users.applicants.UpdateApplicantResponse;
import com.torukobyte.bootcampproject.core.util.mapping.ModelMapperService;
import com.torukobyte.bootcampproject.core.util.results.*;
import com.torukobyte.bootcampproject.entities.users.Applicant;
import com.torukobyte.bootcampproject.repository.abstracts.users.ApplicantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ApplicantManager implements ApplicantService {
    private ApplicantRepository repository;
    private EmployeeService employeeService;
    private ModelMapperService mapper;

    @Override
    public DataResult<List<GetAllApplicantResponse>> getAll() {
        List<Applicant> appliicants = repository.findAll();
        List<GetAllApplicantResponse> data = appliicants
                .stream()
                .map(
                        applicant -> mapper.forResponse().map(applicant, GetAllApplicantResponse.class))
                .toList();

        return new SuccessDataResult<>(data, Messages.Applicant.Listed);
    }

    @Override
    public DataResult<GetApplicantResponse> getById(int id) {
        Applicant appliicant = repository.findById(id).orElse(null);
        GetApplicantResponse data = mapper.forResponse().map(appliicant, GetApplicantResponse.class);

        return new SuccessDataResult<>(data, Messages.Applicant.ListedById);
    }

    @Override
    public DataResult<CreateApplicantResponse> add(CreateApplicantRequest request) {
        Applicant appliicant = mapper.forRequest().map(request, Applicant.class);
        repository.save(appliicant);
        CreateApplicantResponse data = mapper.forResponse().map(appliicant, CreateApplicantResponse.class);

        return new SuccessDataResult<>(data, Messages.Applicant.Created);
    }

    @Override
    public DataResult<UpdateApplicantResponse> update(UpdateApplicantRequest request, int id) {
        Applicant applicant = mapper.forRequest().map(request, Applicant.class);
        applicant.setId(id);
        repository.save(applicant);
        UpdateApplicantResponse data = mapper.forResponse().map(applicant, UpdateApplicantResponse.class);

        return new SuccessDataResult<>(data, Messages.Applicant.Updated);
    }

    @Override
    public Result delete(int id) {
        repository.deleteById(id);
        return new SuccessResult(Messages.Applicant.Deleted);
    }

    @Override
    public DataResult<GetApplicantResponse> becomeApplicant(String about, int id) {
        if(repository.findById(id).isPresent()) {
            return new ErrorDataResult<>(null, Messages.Applicant.AlreadyApplicant);
        }
        Applicant applicant = mapper.forResponse().map(employeeService.getById(id).getData(), Applicant.class);
        applicant.setAbout(about);
        repository.becomeApplicant(about,id);
        GetApplicantResponse data = mapper.forResponse().map(applicant, GetApplicantResponse.class);

        return new SuccessDataResult<>(data, Messages.Applicant.BecameEmployee);
    }
}
