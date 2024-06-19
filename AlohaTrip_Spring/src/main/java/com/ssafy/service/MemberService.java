package com.ssafy.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ssafy.dao.MemberMapper;
import com.ssafy.dto.member.MemberDto;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {
	
	private final MemberMapper memberMapper;

	public int getGugunNum(String gugunName) throws Exception {
		return memberMapper.getGugunNum(gugunName);
	}

	@Transactional
	public void memberRegister(MemberDto member) throws Exception {
		memberMapper.memberRegister(member);
	}

	public MemberDto memberLogin(String id, String password) throws Exception {
		return memberMapper.memberLogin(id, password);
	}

	@Cacheable(value = "memberDto", key = "#memberId")
	public MemberDto findById(Long memberId) {
		return memberMapper.findById(memberId);
	}
	
	public String getGugunName(int gugunNum, int sidoNum) throws Exception {
		return memberMapper.getGugunName(gugunNum, sidoNum);
	}

	@Transactional
	@CachePut(value = "memberDto", key = "#member.memberId")
	public MemberDto memberUpdate(MemberDto member) throws Exception {
		memberMapper.memberUpdate(member);
		return findById(member.getMemberId());
	}

	public void memberWithdrawal(int memberId) throws Exception {
		memberMapper.memberWithdrawal(memberId);
		
	}

	public String setPassword(String password, String id) {
		String resultPassword = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			StringBuffer sb = new StringBuffer();
			byte[] pwdSalt;
			int repeatNum = id.length();
			
			md.update((id).getBytes());
			pwdSalt = md.digest();
			
			
	         for(byte b : pwdSalt) {
	        	 sb.append(String.format("%02x", b));
	         }
	         id = sb.toString();
			
			 md = MessageDigest.getInstance("SHA-256");
			
			 md.update((password + id).getBytes());
			 pwdSalt = md.digest();
			 
			 for(int i = 0; i < repeatNum; i++) {
				 md.update(pwdSalt);
				 pwdSalt = md.digest();
			 }
	         for(byte b : pwdSalt) {
	        	 sb.append(String.format("%02x", b));
	         }
	         resultPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultPassword;
	}
}
