package com.hwua.mapper;

import com.hwua.domain.User;
import com.hwua.domain.UserExample;
import java.util.List;

import com.hwua.domain.User;
import com.hwua.domain.UserExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    @Select("select * from user where username = #{username}")
    User selectByUsername(String username);
    @Update("update user set password=#{password} where username =#{username}")
    int updateByUsername(String username,String password );

    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectUserInfoByUsername(String username);
    @Update("update user set real_name=#{realName},phone=#{phone},email=#{email},status=#{status},sex=#{sex} where username =#{username}")
    void updateUserInfo(String username, String realName, String phone, String email, Integer status, Integer sex);
}
