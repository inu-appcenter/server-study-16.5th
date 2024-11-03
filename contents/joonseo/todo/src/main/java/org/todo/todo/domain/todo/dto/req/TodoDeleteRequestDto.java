package org.todo.todo.domain.todo.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "투두 삭제 req dto")
@Getter
@NoArgsConstructor
public class TodoDeleteRequestDto {
    /**
     * 여기서 택지가 두개가 있다고 생각하는데, 유저 아이디를 받거나 이메일을 받거나인데,
     * 일단 구체적인 인증인가(jwt)를 구현하지 않고 한다고 치면, 로그인 시에 유저의 식별값을 반환해서
     * 웹 스토리지에 넣어주고 요청날릴때마다, 포함해서 넣어주는 방식으로 비스무리하게 기능 구현이 가능할 것 같은데
     * 여기서 로그인 시에 이메일을 던져주는게 맞는지, 아니면 아이디(long)을 던져주는게 맞는지가 의문입니다.
     * 개발 편의상 아이디를 넣어주고 요청에서 아이디 받아오는게 편하긴 한데, 보안적으로... 음.. 이게 맞나 라는 생각이 드네요
     * 지금은 보안은 조금 배제한다고 해도 어.. 애매한 부분인거같습니다.
     */
    @Schema(description = "삭제를 요청한 유저 아이디", example = "1")
    @NotBlank
    private Long userId;

    @Schema(description = "삭제할 투두 타이틀", example = "이터널 선샤인")
    @NotBlank
    private String title;
}
