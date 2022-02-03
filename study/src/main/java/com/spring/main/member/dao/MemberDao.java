package com.spring.main.member.dao;

import org.apache.ibatis.annotations.Mapper;

import com.spring.main.member.dto.MemberDto;

/**
 * @class : studycom.spring.main.member.MemberDao.java
 * @author : soyun8
 * @date : 2021. 11. 7.
 * @desc : member Dao (DB와의 연결을 위함)
 */


/* @Mapper : MyBatis 3.0부터 지원하는 기능
 * @Mapper에서 interface로 작성 후, 따로 implements(구현)을 하지 않는다.
 * */

/* namespace란 클래스의 패키지와 유사함, 원하는 SQL문을 찾아 실행할때 동작함
 * MyBatis와 Repository를 연결하는 방법들
 * 1. @ComponentScan : mapper의 namespace를 사용하여 연결하는 방식, interface, class, mapper 3가지를 모두 구현해야함
 * 2. @Mapper : interface와 mapper만으로도 구현가능
 * 3. @Repository : sqlsession 이용
 * */

@Mapper
public interface MemberDao {

	int overlay(String id);

	int emoverlay(String email);

	int join(MemberDto dto);

	MemberDto findId(String username);

}
