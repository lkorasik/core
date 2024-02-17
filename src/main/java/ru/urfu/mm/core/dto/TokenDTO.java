package ru.urfu.mm.core.dto;

public class TokenDTO {
    private String token;

    public TokenDTO(String token) {
        this.token = token;
    }

    // todo: разберись. ПОчему-то если убрать этот конструктор, то не jackson не сможети распарсить json.
    public TokenDTO() {

    }

    public String getToken() {
        return token;
    }
}
