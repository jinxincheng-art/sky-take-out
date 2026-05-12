package com.jin.server.service;

import com.jin.common.result.PageResult;
import com.jin.pojo.dto.EmployeeDTO;
import com.jin.pojo.dto.EmployeeLoginDTO;
import com.jin.pojo.dto.EmployeePageQueryDTO;
import com.jin.pojo.entity.Employee;

import java.util.List;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     * @param employeeDTO
     */
    void addEmp(EmployeeDTO employeeDTO);

    /**
     * 分页查询员工
     * @param employeePageQueryDTO
     * @return
     */
    PageResult selectEmpByPage(EmployeePageQueryDTO employeePageQueryDTO);
}
