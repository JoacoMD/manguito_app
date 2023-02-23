package app.manguito.backend.dto;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";

	private String mail;

    private int expiresIn;

    public JwtAuthenticationResponse(String accessToken, String mail, int expiresIn) {
        this.accessToken = accessToken;
		this.mail = mail;
        this.expiresIn = expiresIn;
    }
	
}