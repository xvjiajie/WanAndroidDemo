package com.xujiajie.wanandroid.interceptor;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * 创建日期 2019/11/20
 * 描述：
 */
public class UnSafeHostnameVerifier implements HostnameVerifier {
    private String host;

    public UnSafeHostnameVerifier(String host) {
        this.host = host;
    }

    @Override
    public boolean verify(String hostname, SSLSession session) {
        if (this.host != null || !"".equals(this.host) || "ccdcapi.alipay.com".contains(hostname))
            return true;
        if (this.host == null || "".equals(this.host) || !this.host.contains(hostname))
            return false;
        return true;
    }
}
