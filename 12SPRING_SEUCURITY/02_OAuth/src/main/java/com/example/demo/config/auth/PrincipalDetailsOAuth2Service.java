package com.example.demo.config.auth;

import com.example.demo.config.auth.provider.KakaoUserInfo;
import com.example.demo.config.auth.provider.OAuth2UserInfo;
import com.example.demo.domain.dto.UserDto;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class PrincipalDetailsOAuth2Service extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("PrincipalDetailsOAuth2Service's loadUser invoke...");
//        System.out.println("userRequest : " + userRequest);
//        System.out.println("PrincipalDetailsOauth2ServiceImpl's loadUser ..." + userRequest);
//        System.out.println("userRequest.getClientRegistration() :"+ userRequest.getClientRegistration());
//        System.out.println("userRequest.getAccessToken() : "+ userRequest.getAccessToken());
//        System.out.println("userRequest.getAdditionalParameters() : "+ userRequest.getAdditionalParameters());
//        System.out.println("userRequest.getAccessToken().getTokenValue() : "+ userRequest.getAccessToken().getTokenValue());
//        System.out.println("userRequest.getAccessToken().getTokenType().getValue() : "+ userRequest.getAccessToken().getTokenType().getValue());
//        System.out.println("userRequest.getAccessToken().getScopes() : "+ userRequest.getAccessToken().getScopes());

        //OAuth2UserInfo
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("oAuthUser : " + oAuth2User);
        System.out.println("getAttributes : " + oAuth2User.getAttributes());


        OAuth2UserInfo oAuth2UserInfo = null;

        Map<String, Object> attributes = oAuth2User.getAttributes();
        Long id = (Long)attributes.get("id");
        LocalDateTime connected_at = OffsetDateTime.parse(attributes.get("connected_at").toString()).toLocalDateTime();
        Map<String, Object> properties = (Map<String, Object>)attributes.get("properties");
        Map<String, Object> kakao_account = (Map<String, Object>)attributes.get("kakao_account");

        System.out.println("id : " + id);
        System.out.println("connected_at : " + connected_at);
        System.out.println("properties : " + properties);
        System.out.println("email : " + kakao_account);
        oAuth2UserInfo = new KakaoUserInfo(id, connected_at, properties, kakao_account);

        KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(id, connected_at, properties, kakao_account);
        System.out.println("KakaoUserInfo : " + kakaoUserInfo);




        // 최초 로그인 시 로컬 계정 DB 저장 처리
        String username = oAuth2UserInfo.getProvider()+"_"+oAuth2UserInfo.getProviderId();
        String password = passwordEncoder.encode("1234");
        Optional<User> userOptional = userRepository.findById(username);
            // UserDto 생성 >> PrincipalDetails에 포함
            // UserEntity 생성 >> 최초 로그인 시 DB 저장 용도로
        UserDto userDto = null;

        if(userOptional.isEmpty()){
            // 최초 로그인
            userDto = UserDto
                    .builder()
                    .username(username)
                    .password(password)
                    .role("ROLE_USER")
                    .build();

            User user = userDto.toEntity();
            userRepository.save(user);
        }else{
            // 기존 유저 존재
            User user = userOptional.get();
            userDto = UserDto.toDto(user);
        }





        return oAuth2User;
    }
}
