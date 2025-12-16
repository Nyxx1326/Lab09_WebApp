package com.example.customerapi.service;

import com.example.customerapi.dto.CustomerRequestDTO;
import com.example.customerapi.dto.CustomerResponseDTO;
import com.example.customerapi.entity.Customer;
import com.example.customerapi.entity.CustomerStatus;
import com.example.customerapi.exception.DuplicateResourceException;
import com.example.customerapi.exception.ResourceNotFoundException;
import com.example.customerapi.mapper.CustomerMapper;
import com.example.customerapi.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerResponseDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerMapper::toDTO)
                .toList();
    }

    @Override
    public CustomerResponseDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found with id: " + id)
                );

        return CustomerMapper.toDTO(customer);
    }

    @Override
    public CustomerResponseDTO createCustomer(CustomerRequestDTO dto) {

        // Duplicate checks
        if (customerRepository.existsByCustomerCode(dto.getCustomerCode())) {
            throw new DuplicateResourceException("Customer code already exists: " + dto.getCustomerCode());
        }
        if (customerRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + dto.getEmail());
        }

        Customer customer = CustomerMapper.toEntity(dto);

        Customer saved = customerRepository.save(customer);
        return CustomerMapper.toDTO(saved);
    }

    @Override
    public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO dto) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found with id: " + id)
                );

        // Check email duplicate (if changed)
        if (!customer.getEmail().equals(dto.getEmail())
                && customerRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + dto.getEmail());
        }

        // Update editable fields
        customer.setFullName(dto.getFullName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setAddress(dto.getAddress());

        Customer updated = customerRepository.save(customer);

        return CustomerMapper.toDTO(updated);
    }

    @Override
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }

    @Override
    public List<CustomerResponseDTO> searchCustomers(String keyword) {
        return customerRepository.searchCustomers(keyword)
                .stream()
                .map(CustomerMapper::toDTO)
                .toList();
    }

    @Override
    public List<CustomerResponseDTO> getCustomersByStatus(String status) {
        CustomerStatus enumStatus;

        try {
            enumStatus = CustomerStatus.valueOf(status.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }

        return customerRepository.findByStatus(enumStatus)
                .stream()
                .map(CustomerMapper::toDTO)
                .toList();
    }
}
