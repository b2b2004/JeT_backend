package com.travelrec.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.travelrec.project.config.auth.PrincipalDetail;
import com.travelrec.project.dto.MailDto;
import com.travelrec.project.dto.UserDto;
import com.travelrec.project.mapper.UserMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
    private JavaMailSender mailSender;
    
    private static final String FROM_ADDRESS = "b2b2007@naver.com";

	public String 회원가입(UserDto user) {
		int insert = 0;
		String rawPassword = user.getPassword();
		String encryptionPassword = bcryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encryptionPassword);
		insert = userMapper.insert(user);
		
		if(insert > 0) {
			return "회원가입 성공";
		}else {
			return "회원가입 실패";
		}
	}
	
	public UserDto 유저확인(PrincipalDetail principalDetail) {
		String id = principalDetail.getUser().getUserId();
		UserDto user = userMapper.findByUserId(id);
		return  user;
	}
	
    public MailDto createMail(String userId , String email, String tempPassword) {
        MailDto dto = new MailDto();
        dto.setAddress(email);
        dto.setTitle(userId + "님의 Jet 임시비밀번호 안내 이메일 입니다.");
        dto.setMessage("안녕하세요. Jet 임시비밀번호 안내 관련 이메일 입니다." + "[" + userId + "]" + "님의 임시 비밀번호는 "
                + tempPassword + " 입니다.");
        return dto;
    }
	
	public String 임시비밀번호발송(UserDto user) {
		
		int update = 0;
		if(user == null)
		{
			return "입력사항을 다시 확인해주세요.";
		}else {
			UserDto userCK = userMapper.findByUserId(user.getUserId());
			if(!userCK.getEmail().equals(user.getEmail()))
			{
				return "이메일을 잘못 입력하셨습니다.";
			}else if(!userCK.getUserId().equals(user.getUserId())) {
				return "아이디를 잘못 입력하셨습니다.";
			}else {
				String tempPassword = getTempPassword();
				String encryptionPassword = bcryptPasswordEncoder.encode(tempPassword);
				update = userMapper.updatePw(user.getUserId(), encryptionPassword);
				if(update > 0)
				{
					MailDto dto = createMail(user.getUserId(), user.getEmail(), tempPassword); //이메일 전송
					mailSend(dto);
				}else {
					return "데이터베이스 임시비밀번호 저장이 실패하였습니다.";
				}
			}
		}
		return "해당 이메일로 임시비밀번호가 발송되었습니다.";
	}


    public String getTempPassword() {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        String str = "";
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }

    public void mailSend(MailDto mailDto){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setFrom(FROM_ADDRESS);
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());
        mailSender.send(message);
    }
}