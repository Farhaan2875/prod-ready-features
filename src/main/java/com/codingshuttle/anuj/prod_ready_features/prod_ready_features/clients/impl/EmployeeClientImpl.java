package com.codingshuttle.anuj.prod_ready_features.prod_ready_features.clients.impl;

import com.codingshuttle.anuj.prod_ready_features.prod_ready_features.advice.ApiResponse;
import com.codingshuttle.anuj.prod_ready_features.prod_ready_features.clients.EmployeeClient;
import com.codingshuttle.anuj.prod_ready_features.prod_ready_features.dto.EmployeeDTO;
import com.codingshuttle.anuj.prod_ready_features.prod_ready_features.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.lang.reflect.Type;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeClientImpl implements EmployeeClient {

    private final RestClient restClient;

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        try{
            ApiResponse<List<EmployeeDTO>> employeeDTOList = restClient.get()
                    .uri("employees") //appended to base url (already defined)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
            return employeeDTOList.getData(); //get data method defined in ApiResponse class
        } catch(Exception e){
            throw new RuntimeException(e); //see tests for method call\
            // A LOT OF METHOD LIKE 500, 4XX errors can arise in rest clients so we sue try catch
        }
    }

    @Override
    public EmployeeDTO getEmployeeById(Long employeeId) {
        try{
            ApiResponse<EmployeeDTO> employeeResponse = restClient.get()
                    .uri("employees/{employeeId}" , employeeId)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() { //receiving data
                    });
            return employeeResponse.getData();
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        try{
            ResponseEntity<ApiResponse<EmployeeDTO>> employeeDTOApiResponse = restClient.post()
                    .uri("employees")
                    .body(employeeDTO) //sending data
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req,res) -> { //USED TO HANDLE IN REST CLIENTS
                        System.out.println(new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("could not create employee");
                    })
                    .toEntity(new ParameterizedTypeReference<>(){ //you get the whole entity with status code and body and meta data with this method
                    });
            return employeeDTOApiResponse.getBody().getData();
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
