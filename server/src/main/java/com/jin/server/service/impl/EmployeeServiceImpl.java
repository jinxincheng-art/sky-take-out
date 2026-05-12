package com.jin.server.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jin.common.constant.MessageConstant;
import com.jin.common.constant.PasswordConstant;
import com.jin.common.constant.StatusConstant;
import com.jin.common.context.BaseContext;
import com.jin.common.result.PageResult;
import com.jin.pojo.dto.EmployeeDTO;
import com.jin.pojo.dto.EmployeeLoginDTO;
import com.jin.pojo.dto.EmployeePageQueryDTO;
import com.jin.pojo.entity.Employee;
import com.jin.common.exception.AccountLockedException;
import com.jin.common.exception.AccountNotFoundException;
import com.jin.common.exception.PasswordErrorException;
import com.jin.server.mapper.EmployeeMapper;
import com.jin.server.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // TODO 后期需要进行md5加密，然后再进行比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    @Override
    public void addEmp(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        employee.setStatus(StatusConstant.ENABLE);
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.addEmp(employee);
    }

    @Override
    public PageResult selectEmpByPage(EmployeePageQueryDTO employeePageQueryDTO) {
        Page<Employee> page;
        PageResult pageInfo = null;
        try {
            PageHelper.startPage(employeePageQueryDTO.getPage(),employeePageQueryDTO.getPageSize());
            page = employeeMapper.selectEmpByPage(employeePageQueryDTO);
            pageInfo = new PageResult(page.getTotal(),page.getResult());
        }catch (Exception e){
            log.error("查询失败:{}",e.getMessage());
        }finally {
            PageHelper.clearPage();
        }
        return pageInfo;
    }
}
