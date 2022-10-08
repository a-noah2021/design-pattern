package com.noah2021.ratelimiter.rule.util;

import com.noah2021.ratelimiter.error.EmRateLimiterError;
import com.noah2021.ratelimiter.error.RateLimiterException;
import org.apache.commons.lang3.StringUtils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @program: design-pattern
 * @description:
 * @author: noah2021
 * @date: 2022-10-08 21:45
 **/
public class UrlUtils {

    /**
     * Split url path into segments. support the path with url template variable.
     * @param urlPath
     * @return
     * @throws RateLimiterException
     */
    public static List<String> tokenizeUrlPath(String urlPath) throws RateLimiterException {
        if (StringUtils.isBlank(urlPath)) {
            return Collections.emptyList();
        }

        if (!urlPath.startsWith("/")) {
            throw new RateLimiterException(EmRateLimiterError.INVALID_URL, "UrlParser tokenize error, invalid urls: " + urlPath);
        }

        String[] dirs = urlPath.split("/");
        List<String> dirlist = new ArrayList<String>();
        for (int i = 0; i < dirs.length; ++i) {
            if ((dirs[i].contains(".") || dirs[i].contains("?") || dirs[i].contains("*"))
                    && (!dirs[i].startsWith("{") || !dirs[i].endsWith("}"))) {
                throw new RateLimiterException(EmRateLimiterError.INVALID_URL, "UrlParser tokenize error, invalid urls: " + urlPath);
            }

            if (!StringUtils.isEmpty(dirs[i])) {
                dirlist.add(dirs[i]);
            }
        }
        return dirlist;
    }

    /**
     Get url path, remove parameters.
     *
     * {@literal "http://www.test.com/v1/user" -> "/v1/user" }
     * {@literal "/v1/user" --> "/v1/user" }
     * {@literal "/v1/user?lender=true"-->"/v1/user" }
     * @param url
     * @return
     * @throws RateLimiterException
     */
    public static String getUrlPath(String url) throws RateLimiterException {
        if (StringUtils.isBlank(url)) {
            return null;
        }

        URI urlObj = null;
        try {
            urlObj = new URI(url);
        } catch (URISyntaxException e) {
            throw  new RateLimiterException(EmRateLimiterError.INVALID_URL, "Get url path error: " + url);
        }

        String path = urlObj.getPath();
        if (path.isEmpty()) {
            return "/";
        }
        return path;
    }

    public static boolean validUrl(String url) {
        if (StringUtils.isBlank(url)) {
            return false;
        }
        try {
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

}
