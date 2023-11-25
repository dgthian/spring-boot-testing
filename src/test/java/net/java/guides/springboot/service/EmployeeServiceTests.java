package net.java.guides.springboot.service;

import net.java.guides.springboot.exception.ResourceNotFoundException;
import net.java.guides.springboot.model.Employee;
import net.java.guides.springboot.repository.EmployeeRepository;
import net.java.guides.springboot.service.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;


    @BeforeEach
    public void setup() {
        //employeeRepository = Mockito.mock(EmployeeRepository.class);
        //employeeService = new EmployeeServiceImpl(employeeRepository);
         employee = Employee.builder()
                .id(1L)
                .firstName("Djibril")
                .lastName("THIANDOUM")
                .email("djibril.thiandoum@gmail.com")
                .build();
    }

    // Junit test for saveEmployee method
    @DisplayName("Junit test for saveEmployee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        // given - precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

        System.out.println(employeeRepository);
        System.out.println(employeeService);
        // when - action or the behavior that wa are going to test
        Employee savedEmployee = employeeService.saveEmployee(employee);
        System.out.println(savedEmployee);

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // Junit test for saveEmployee method
    @DisplayName("Junit test for saveEmployee method which throw exception")
    @Test
    public void givenExistingEmail_whenSaveEmployee_thenThrowException() {
        // given - precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));
        //given(employeeRepository.save(employee)).willReturn(employee);

        System.out.println(employeeRepository);
        System.out.println(employeeService);
        // when - action or the behavior that wa are going to test
        assertThrows(ResourceNotFoundException.class, () ->{
            employeeService.saveEmployee(employee);
        });
        // then
        verify(employeeRepository,never()).save(any(Employee.class));
    }


    // Junit test for getAllEmployees method
    @DisplayName("Junit test for getAllEmployees method")
    @Test
    public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList() {
        // given - precondition or setup
       Employee employee2 = Employee.builder()
                .id(1L)
                .firstName("Awa")
                .lastName("Ndiaye")
                .email("awa.ndiaye@gmail.com")
                .build();
        given(employeeRepository.findAll()).willReturn(List.of(employee, employee2));

        // when - action or the behavior that wa are going to test
        List<Employee> employeeList = employeeService.getAllEmployees();

        // then - verify the output
        Assertions.assertThat(employeeList).isNotNull();
        Assertions.assertThat(employeeList).size().isEqualTo(2);
    }


    // Junit test for getAllEmployees method
    @DisplayName("Junit test for getAllEmployees method (negative scenario)")
    @Test
    public void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmployeesList() {
        // given - precondition or setup
        Employee employee2 = Employee.builder()
                .id(1L)
                .firstName("Awa")
                .lastName("Ndiaye")
                .email("awa.ndiaye@gmail.com")
                .build();
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        // when - action or the behavior that wa are going to test
        List<Employee> employeeList = employeeService.getAllEmployees();

        // then - verify the output
        assertThat(employeeList).isEmpty();
        assertThat(employeeList).size().isEqualTo(0);
    }

    // Junit test for getEmployeeById method
    @DisplayName("Junit test for getEmployeeById method")
    @Test
    public void givenEmployeeId_whenGetEmployeedById_thenReturnEmployeeObject() {
        // given - precondition or setup
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        // when - action or the behavior that wa are going to test
        Employee savedEmployee = employeeService.getEmployeeById(employee.getId()).get();

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }


    // Junit test for updateEmployee method
    @DisplayName("Junit test for updateEmployee method")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        // given - precondition or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("awa.ndiaye@gmail.com");
        employee.setFirstName("Awa");

        // when - action or the behavior that wa are going to test
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        // then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("awa.ndiaye@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Awa");
    }


    // Junit test for deleteEmployee method
    @DisplayName("Junit test for deleteEmployee method")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenNothing() {
        long employeeId = 1L;

        // given - precondition or setup
        willDoNothing().given(employeeRepository).deleteById(employeeId);

        // when - action or the behavior that wa are going to test
        employeeService.deleteEmployee(employeeId);

        // then - verify the output - How many times deleteById method is called
        verify(employeeRepository,times(1)).deleteById(employeeId);
    }
}
