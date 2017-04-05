package io.robe.auth.token.configuration;

public class TokenBasedAuthConfiguration {

    private String tokenKey;

    private String domain;

    private String path = "/";

    private int maxage = 0;

    private boolean secure = false;

    private int poolSize = 4;

    private String algorithm;

    private String serverPassword;

    private String logoutPath = "authentication/logout";

    public String getLogoutPath() {
        return logoutPath;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public String getDomain() {
        return domain;
    }

    public String getPath() {
        return path;
    }

    public int getMaxage() {
        return maxage;
    }

    public boolean isSecure() {
        return secure;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getServerPassword() {
        return serverPassword;
    }
}
