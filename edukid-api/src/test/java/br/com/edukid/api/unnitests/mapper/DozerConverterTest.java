package br.com.edukid.api.unnitests.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.edukid.api.entities.UserFather;
import br.com.edukid.api.mapper.DozerMapper;
import br.com.edukid.api.unnitests.mapper.mocks.MockUserFather;
import br.com.edukid.api.vo.v1.LoginVO;
import br.com.edukid.api.vo.v1.UserFatherVO;

public class DozerConverterTest {
    
    MockUserFather inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockUserFather();
    }

    @Test
    public void parseEntityToVOTest() {
        UserFatherVO output = DozerMapper.parseObject(inputObject.mockEntity(), UserFatherVO.class);
        assertEquals(0, output.getId());
        assertEquals("First Name Test0", output.getFirstName());
        assertEquals("Last Name Test0", output.getLastName());
        assertEquals("CPF Test0", output.getCpf());
        assertEquals("Phone Test0", output.getPhone());
        assertEquals("Email Test0", output.getEmail());
        assertEquals("Password Test0", output.getPassword());
    }

    @Test
    public void parseEntityListToVOListTest() {
        List<UserFatherVO> outputList = DozerMapper.parseListObjects(inputObject.mockEntityList(), UserFatherVO.class);
        UserFatherVO outputZero = outputList.get(0);
        
        assertEquals(0, outputZero.getId());
        assertEquals("First Name Test0", outputZero.getFirstName());
        assertEquals("Last Name Test0", outputZero.getLastName());
        assertEquals("CPF Test0", outputZero.getCpf());
        assertEquals("Phone Test0", outputZero.getPhone());
        assertEquals("Email Test0", outputZero.getEmail());
        assertEquals("Password Test0", outputZero.getPassword());
        
        UserFatherVO outputSeven = outputList.get(7);
      
        assertEquals(7, outputSeven.getId());
        assertEquals("First Name Test7", outputSeven.getFirstName());
        assertEquals("Last Name Test7", outputSeven.getLastName());
        assertEquals("CPF Test7", outputSeven.getCpf());
        assertEquals("Phone Test7", outputSeven.getPhone());
        assertEquals("Email Test7", outputSeven.getEmail());
        assertEquals("Password Test7", outputSeven.getPassword());
        
        UserFatherVO outputTwelve = outputList.get(12);
        
        assertEquals(12, outputTwelve.getId());
        assertEquals("First Name Test12", outputTwelve.getFirstName());
        assertEquals("Last Name Test12", outputTwelve.getLastName());
        assertEquals("CPF Test12", outputTwelve.getCpf());
        assertEquals("Phone Test12", outputTwelve.getPhone());
        assertEquals("Email Test12", outputTwelve.getEmail());
        assertEquals("Password Test12", outputTwelve.getPassword());
    }

    @Test
    public void parseVOToEntityTest() {
        UserFather output = DozerMapper.parseObject(inputObject.mockVO(), UserFather.class);
        assertEquals(0, output.getId());
        assertEquals("First Name Test0", output.getFirstName());
        assertEquals("Last Name Test0", output.getLastName());
        assertEquals("CPF Test0", output.getCpf());
        assertEquals("Phone Test0", output.getPhone());
        assertEquals("Email Test0", output.getEmail());
        assertEquals("Password Test0", output.getPassword());
    }

    @Test
    public void parserVOListToEntityListTest() {
        List<UserFather> outputList = DozerMapper.parseListObjects(inputObject.mockVOList(), UserFather.class);
        UserFather outputZero = outputList.get(0);
        
        assertEquals(0, outputZero.getId());
        assertEquals("First Name Test0", outputZero.getFirstName());
        assertEquals("Last Name Test0", outputZero.getLastName());
        assertEquals("CPF Test0", outputZero.getCpf());
        assertEquals("Phone Test0", outputZero.getPhone());
        assertEquals("Email Test0", outputZero.getEmail());
        assertEquals("Password Test0", outputZero.getPassword());
        
        UserFather outputSeven = outputList.get(7);
        
        assertEquals(7, outputSeven.getId());
        assertEquals("First Name Test7", outputSeven.getFirstName());
        assertEquals("Last Name Test7", outputSeven.getLastName());
        assertEquals("CPF Test7", outputSeven.getCpf());
        assertEquals("Phone Test7", outputSeven.getPhone());
        assertEquals("Email Test7", outputSeven.getEmail());
        assertEquals("Password Test7", outputSeven.getPassword());
        
        UserFather outputTwelve = outputList.get(12);
        
        assertEquals(12, outputTwelve.getId());
        assertEquals("First Name Test12", outputTwelve.getFirstName());
        assertEquals("Last Name Test12", outputTwelve.getLastName());
        assertEquals("CPF Test12", outputTwelve.getCpf());
        assertEquals("Phone Test12", outputTwelve.getPhone());
        assertEquals("Email Test12", outputTwelve.getEmail());
        assertEquals("Password Test12", outputTwelve.getPassword());
    }
    
    @Test
    public void parseEntityToLoginVOTest() {
        LoginVO output = DozerMapper.parseObject(inputObject.mockEntity(), LoginVO.class);
        assertEquals("Email Test0", output.getEmail());
        assertEquals("Password Test0", output.getPassword());
    }

    @Test
    public void parseEntityListToLoginVOListTest() {
        List<LoginVO> outputList = DozerMapper.parseListObjects(inputObject.mockEntityList(), LoginVO.class);
        LoginVO outputZero = outputList.get(0);
        
        assertEquals("Email Test0", outputZero.getEmail());
        assertEquals("Password Test0", outputZero.getPassword());
        
        LoginVO outputSeven = outputList.get(7);
        
        assertEquals("Email Test7", outputSeven.getEmail());
        assertEquals("Password Test7", outputSeven.getPassword());
        
        LoginVO outputTwelve = outputList.get(12);

        assertEquals("Email Test12", outputTwelve.getEmail());
        assertEquals("Password Test12", outputTwelve.getPassword());
    }
}
