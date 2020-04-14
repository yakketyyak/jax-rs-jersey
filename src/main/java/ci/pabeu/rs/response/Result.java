package ci.pabeu.rs.response;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Result {

	private String token;

	public Result(String token) {
		this.setToken(token);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
