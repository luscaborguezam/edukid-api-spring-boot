package br.com.edukid.api.unnitests.mapper.mocks;

import java.util.ArrayList;
import java.util.List;

import br.com.edukid.api.entities.UserFather;
import br.com.edukid.api.vo.v1.LoginVO;
import br.com.edukid.api.vo.v1.UserFatherCadastroVO;

public class MockUserFather {


    public UserFather mockEntity() {
        return mockEntity(0);
    }
    
    public UserFatherCadastroVO mockVO() {
        return mockVO(0);
    }
    
    public LoginVO mockLoginVO() {
        return mockLoginVO(0);
    }
    
    public List<UserFather> mockEntityList() {
        List<UserFather> persons = new ArrayList<UserFather>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockEntity(i));
        }
        return persons;
    }

    public List<UserFatherCadastroVO> mockVOList() {
        List<UserFatherCadastroVO> persons = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockVO(i));
        }
        return persons;
    }
    
    public List<LoginVO> mockLoginVOList() {
        List<LoginVO> logins = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
        	logins.add(mockLoginVO(i));
        }
        return logins;
    }
    
    public UserFather mockEntity(Integer number) {
    	UserFather userFather = new UserFather();
    	userFather.setId(number);
    	userFather.setFirstName("First Name Test" + number);
    	userFather.setLastName("Last Name Test" + number);
    	userFather.setCpf("CPF Test" + number);
    	userFather.setPhone("Phone Test" + number);
    	userFather.setEmail("Email Test" + number);
    	userFather.setPassword("Password Test" + number);
        return userFather;
    }

    public UserFatherCadastroVO mockVO(Integer number) {
    	UserFatherCadastroVO userFather = new UserFatherCadastroVO();
    	userFather.setId(String.valueOf(number));
    	userFather.setFirstName("First Name Test" + number);
    	userFather.setLastName("Last Name Test" + number);
    	userFather.setCpf("CPF Test" + number);
    	userFather.setPhone("Phone Test" + number);
    	userFather.setEmail("Email Test" + number);
    	userFather.setPassword("Password Test" + number);
        return userFather;
    }
    
    public LoginVO mockLoginVO(Integer number) {
    	LoginVO login = new LoginVO();
    	login.setEmailOrNickName("Email Test" + number);
    	login.setPassword("Password Test" + number);
        return login;
    }

}
