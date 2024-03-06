package de.kct.kct.controller;

import de.kct.kct.domain.Customer12;
import de.kct.kct.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("customers")
public class CustomerController {
    private CustomerService customerService;

    @PostMapping("/upload-customers-data")
    public ResponseEntity<?> uploadCustomersData(@RequestParam("file") MultipartFile file) {
        this.customerService.saveCustomersToDatabase(file);
        return ResponseEntity
                .ok(Map.of("Message", " Customers data uploaded and saved to database successfully"));
    }

    @GetMapping
    public ResponseEntity<List<Customer12>> getCustomers() {
        return new ResponseEntity<>(customerService.getCustomers(), HttpStatus.FOUND);
    }

    @GetMapping("/helloworld")
    public String helloworld() {
        return "hello1234";
    }

}
