package com.example.RestTest.services;

import com.example.RestTest.dao.OtherUserRepository;
import com.example.RestTest.model.OtherUser;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class OtherUserServiceTest {
    @Mock
    private OtherUserRepository repository_;

    private OtherUserService service_;

    public OtherUserServiceTest(){
        MockitoAnnotations.initMocks(this);
        this.service_ = new OtherUserService(repository_);
    }

    @Test
    void checkUserPresence_Should_Return_True() throws Exception{
        given(repository_.getUserByUserName("Alex_Morph")).willReturn(
                new OtherUser("1","Alex_Morph"));
        boolean userExist = service_.checkUserPresence(
                new OtherUser("1","Alex_Morph"));
        assertThat(userExist).isTrue();
    }

    @Test
    void checkUserPresence_Should_Return_False() throws Exception{
        given(repository_.getUserByUserName("Alex_Morph")).willReturn(
                null);
        boolean userExist = service_.checkUserPresence(
                new OtherUser("1","Alex_Morph"));
        assertThat(userExist).isFalse();
    }

    //@Test(expected = Exception.class)
    //void checkUserPresence_Should_Throw_Exception() throws Exception{
    //    given(repository.getUserByUserName(anyString())).willThrow(Exception.class);
    //    boolean userExist = service.checkUserPresence(
    //            new OtherUser("2","Alex_Morph"));
    //}
}