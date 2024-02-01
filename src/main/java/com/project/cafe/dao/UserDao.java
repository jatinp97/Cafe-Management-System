package com.project.cafe.dao;

import com.project.cafe.pojo.User;
import com.project.cafe.wrapper.UserWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UserDao extends JpaRepository<User,Integer> {

    User findByEmailId(@Param("email") String email); //implementation found in USER POJO

    List<UserWrapper> getAllUser();
    List<String> getAllAdmin();

    @Transactional
    @Modifying
    Integer updateStatus(@Param("status") String status, @Param("id") Integer id);

    User findByEmail(String email);//No Implementation as JPA directly generates the query as we have Written FINDBY+PojoName

}
