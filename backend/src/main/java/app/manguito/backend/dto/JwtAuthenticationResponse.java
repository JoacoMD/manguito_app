package app.manguito.backend.dto;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String mail;
    private int expiresIn;
    private String emprendimientoUrl;

    public JwtAuthenticationResponse(String accessToken, String mail, int expiresIn, String emprendimientoUrl) {
        this.accessToken = accessToken;
		this.mail = mail;
        this.emprendimientoUrl = emprendimientoUrl;
        this.expiresIn = expiresIn;
    }
	
}