package net.java.guides.springboot.repository;

import net.java.guides.springboot.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class EmployeeRepositoryTests {
    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee ;

    @BeforeEach
    public void setup() {
         employee = Employee.builder()
                .firstName("Djibril")
                .lastName("Thiandoum")
                .email("djibril.thiandoum@gmail.com")
                .build();
    }

    //Junit test for save employees operation
    @DisplayName("Junit test for save employees operation")
    @Test
    public void givenEmployeeObject_whenSave_thenSavedEmployee() {
        //given - precondition or setup
       /** Employee employee = Employee.builder()
                .firstName("Djibril")
                .lastName("Thiandoum")
                .email("djibril.thiandoum@gmail.com")
                .build();**/
        //when - action or the behavior that wa are going to test
        Employee savedEmployee = employeeRepository.save(employee);

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    // Junit test for get all employees operation
    @DisplayName("Junit test for get all employees operation")
    @Test
    public void givenEmployeesList_whenFindAll_thenEmployeesList() {
        // given - precondition or setup
       /** Employee employee = Employee.builder()
                .firstName("Djibril")
                .lastName("Thiandoum")
                .email("djibril.thiandoum@gmail.com")
                .build();**/

        Employee employee2 = Employee.builder()
                .firstName("Awa")
                .lastName("Ndiaye")
                .email("awa.ndiaye@gmail.com")
                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee2);

        // when - action or the behavior that wa are going to test
        List<Employee> employeeList = employeeRepository.findAll();

        // then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }



    // Junit test for get employee by id operation
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {
        // given - precondition or setup
        /**Employee employee = Employee.builder()
                .firstName("Djibril")
                .lastName("Thiandoum")
                .email("djibril.thiandoum@gmail.com")
                .build();**/
        employeeRepository.save(employee);

        // when - action or the behavior that we are going  test
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();

        // then - verify the output
        assertThat(employeeDB).isNotNull();
    }

   // Junit test for get employee by email operation
    @DisplayName("Junit test for get employee by email operation")
    @Test
    public void givenEmail_whenFindByEmail_thenReturnEmployeeObject() {
        // given - precondition or setup
        /**Employee employee = Employee.builder()
                .firstName("Djibril")
                .lastName("Thiandoum")
                .email("djibril.thiandoum@gmail.com")
                .build();**/
        employeeRepository.save(employee);

        // when - action or the behavior that wa are going to test
        Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();

        // then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    // Junit test for update employee operation
    @DisplayName("Junit test for update employee operation")
    @Test
    public void givenEmployeeObject_whenUpdateEmplooyee_thenReturnUpdateObject() {
        // given - precondition or setup
        /**Employee employee = Employee.builder()
                .firstName("Djibril")
                .lastName("Thiandoum")
                .email("djibril.thiandoum@gmail.com")
                .build();**/
        employeeRepository.save(employee);

        // when - action or the behavior that wa are going to test
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setEmail("d.thiandoum@gmail.com");
        savedEmployee.setFirstName("Djibril Gueye");
        employeeRepository.save(savedEmployee);
        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        // then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("d.thiandoum@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Djibril Gueye");
    }

    // Junit test for delete employee operation
    @DisplayName("Junit test for delete employee operation ")
    @Test
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee() {
        // given - precondition or setup
       /** Employee employee = Employee.builder()
                .firstName("Djibril")
                .lastName("Thiandoum")
                .email("djibril.thiandoum@gmail.com")
                .build();**/
        employeeRepository.save(employee);

        // when - action or the behavior that wa are going to test
        //employeeRepository.delete(employee);
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        // then - verify the output
        assertThat(employeeOptional).isEmpty();

    }


    // Junit test for custom query using JPQL with index
    @DisplayName("Junit test for custom query using JPQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject() {
        // given - precondition or setup
       /** Employee employee = Employee.builder()
                .firstName("Djibril")
                .lastName("Thiandoum")
                .email("djibril.thiandoum@gmail.com")
                .build();**/
        employeeRepository.save(employee);
        String firstName = "Djibril";
        String lastName = "Thiandoum";

        // when - action or the behavior that wa are going to test
        Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }


    // Junit test for custom query using JPQL with Named params
    @DisplayName("Junit test for custom query using JPQL with Named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject() {
        // given - precondition or setup
        /**Employee employee = Employee.builder()
                .firstName("Djibril")
                .lastName("Thiandoum")
                .email("djibril.thiandoum@gmail.com")
                .build();**/
        employeeRepository.save(employee);
        String firstName = "Djibril";
        String lastName = "Thiandoum";

        // when - action or the behavior that wa are going to test
        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(firstName, lastName);

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // Junit test for custom query using native SQL with index
    @DisplayName("Junit test for custom query using native SQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject() {
        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Djibril")
                .lastName("Thiandoum")
                .email("djibril.thiandoum@gmail.com")
                .build();
        employeeRepository.save(employee);
        //String firstName = "Djibril";
        //String lastName = "Thiandoum";

        // when - action or the behavior that wa are going to test
        Employee savedEmployee = employeeRepository.findByNativeSQL(employee.getFirstName(), employee.getLastName());

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // Junit test for custom query using native SQL with index
    @DisplayName("Junit test for custom query using native SQL with named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQLNamedParams_thenReturnEmployeeObject() {
        // given - precondition or setup
        /**Employee employee = Employee.builder()
                .firstName("Djibril")
                .lastName("Thiandoum")
                .email("djibril.thiandoum@gmail.com")
                .build();**/
        employeeRepository.save(employee);
        //String firstName = "Djibril";
        //String lastName = "Thiandoum";

        // when - action or the behavior that wa are going to test
        Employee savedEmployee = employeeRepository.findByNativeSQLNamedParams(employee.getFirstName(), employee.getLastName());

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }


}
