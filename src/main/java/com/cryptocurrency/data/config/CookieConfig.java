package com.cryptocurrency.data.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.CookieSerializer;

/**
 * The CookieConfig class is a Spring configuration class for configuring session cookies.
 * Author: Amadou BASS
 */
@Configuration
public class CookieConfig {

    /**
     * Configures and returns a CookieSerializer bean for session cookies.
     *
     * The DefaultCookieSerializer is used with the following settings:
     * - Secure cookies are enabled, ensuring cookies are sent over HTTPS only.
     * - The cookie path is set to "/", meaning the cookie is valid for the entire application.
     * - HttpOnly cookies are used, which helps mitigate the risk of client-side scripts accessing the protected cookie data.
     *
     * @return a configured CookieSerializer instance
     */
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        cookieSerializer.setUseSecureCookie(true);
        cookieSerializer.setCookiePath("/");
        cookieSerializer.setUseHttpOnlyCookie(true);
        return cookieSerializer;
    }
}

