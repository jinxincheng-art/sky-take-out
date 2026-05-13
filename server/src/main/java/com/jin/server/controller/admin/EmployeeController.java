package com.jin.server.controller.admin;

import com.jin.common.constant.JwtClaimsConstant;
import com.jin.common.result.PageResult;
import com.jin.pojo.dto.EmployeeDTO;
import com.jin.pojo.dto.EmployeeLoginDTO;
import com.jin.pojo.dto.EmployeePageQueryDTO;
import com.jin.pojo.entity.Employee;
import com.jin.common.properties.JwtProperties;
import com.jin.common.result.Result;
import com.jin.server.service.EmployeeService;
import com.jin.common.utils.JwtUtil;
import com.jin.pojo.vo.EmployeeLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 新增员工
     * @return
     */
    @PostMapping()
    public Result addEmp(@RequestBody EmployeeDTO employeeDTO){
        log.info("新增员工:{}",employeeDTO);
        employeeService.addEmp(employeeDTO);
        return Result.success();
    }

    /**
     * 分页查询员工
     * @param employeePageQueryDTO
     */
    @GetMapping("/page")
    public Result<PageResult> selectEmpByPage(EmployeePageQueryDTO employeePageQueryDTO){
        log.info("分页查询参数位:{}",employeePageQueryDTO);
        PageResult pageResult = employeeService.selectEmpByPage(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 启用或禁用员工
     * @param id
     * @param status
     * @return
     */
    @PostMapping("/status/{status}")
    public Result updateStatus(Long id, @PathVariable Integer status){
        log.info("更新员工:{}状态为{}",id,status);
        employeeService.updateStatus(id,status);
        return Result.success();
    }

    @GetMapping("{id}")
    public Result<Employee> getEmpById(@PathVariable Long id){
        log.info("查询员工id:{}",id);
        Employee emp = employeeService.getEmpById(id);
        return Result.success(emp);
    }

    @PutMapping()
    public Result updateEmp(@RequestBody EmployeeDTO employeeDTO){
        log.info("更新员工数据:{}",employeeDTO);
        employeeService.updateEmp(employeeDTO);
        return Result.success();
    }

}
