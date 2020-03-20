package file.univ_playground.controller;

import file.univ_playground.domain.User;
import file.univ_playground.dto.SessionRequestDto;
import file.univ_playground.dto.SessionResponseDto;
import file.univ_playground.service.UserService;
import file.univ_playground.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class SessionController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;

    //Request로 email과 password를 받아서 Response로 토큰을 준다
    @PostMapping("/session")
    public ResponseEntity<SessionResponseDto> create(
            @RequestBody SessionRequestDto resource
    ) throws URISyntaxException {
        String email = resource.getEmail();
        String password = resource.getPassword();

        //인증된 user
        User user = userService.authenticate(email,password);

        //getAccessToken은 비밀번호의 처음 10자리로 초기설정됨
        String accessToken = jwtUtil.createToken(user.getId(),user.getName());

        //응답
        //accessToken 을 반환해준다
        String url = "/session";
        return ResponseEntity.created(new URI(url))
                .body(SessionResponseDto.builder()
                        .accessToken(accessToken).build());
    }
}
