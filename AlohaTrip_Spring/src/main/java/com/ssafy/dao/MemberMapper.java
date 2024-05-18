package com.ssafy.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.dto.MemberDto;

import java.util.Optional;

@Mapper
public interface MemberMapper {
	int getGugunNum(String gugunName)throws Exception;
	void memberRegister(MemberDto member);
	MemberDto memberLogin(String id, String password) throws Exception;
	MemberDto findById(Long memberId);
	String getGugunName(int gugunNum, int sidoNum) throws Exception;
	void memberUpdate(MemberDto member)throws Exception;
	void memberWithdrawal(int memberId)throws Exception;
	Optional<MemberDto> findBySocialId(String socialId);
	Optional<MemberDto> findByEmail(String email);
}
