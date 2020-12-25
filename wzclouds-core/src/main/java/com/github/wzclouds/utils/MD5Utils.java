package com.github.wzclouds.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 一般的md5加密算法，与别人的md5是相同的
 * 将密码进行MD5加密处理
 * @author pdy
 * @date 2019-06-20 22:14
 */
@Slf4j
public class MD5Utils {

    private static final String ALGORITHM_MD5 = "MD5";

    /**
     * Encrypt the password with MD5
     *
     * @param pass the password to encryption
     * @return encryption string
     */
    public static String getPassMD5(String pass) {
        String keys = null;
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM_MD5);
            if (pass == null) {
                pass = "";
            }
            byte[] bPass = pass.getBytes();
            md.update(bPass);
            keys = bytesToHexString(md.digest());
        } catch (NoSuchAlgorithmException aex) {
            log.warn("加密算法出错：DataSourceFactory", aex);
        }
        return keys;
    }

    /**
     * 将beye[]转换为十六进制字符串
     *
     * @param bArray
     * @return
     */
    protected static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    public static String fileMd5(InputStream inputStream) {
        try {
            return DigestUtils.md5Hex(inputStream);
        } catch (IOException e) {
            log.error("fileMd5-error", e);
        }
        return null;
    }

    /**
     * 获取文件MD5
     *
     * @param is
     * @return
     */
    public static String getMd5(InputStream is) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[8192];
            int length;
            while ((length = is.read(buffer)) != -1) {
                md5.update(buffer, 0, length);
            }
            return new String(Hex.encodeHex(md5.digest()));
        } catch (Exception e) {
            log.error("get MD5 error{}", e);
            return null;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                log.error("close in error{}", e);
            }
        }
    }

}