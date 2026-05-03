package com.jin.server.service;

import com.jin.pojo.dto.EmployeeLoginDTO;
import com.jin.pojo.entity.Employee;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

}
