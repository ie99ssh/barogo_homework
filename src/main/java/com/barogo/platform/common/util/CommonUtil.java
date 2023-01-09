package com.barogo.platform.common.util;

import com.barogo.platform.common.exception.GlobalException;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class CommonUtil {

    /**
     * --------------------------------------------------------------------
     * ■IP 주소 조회 ■sangheon
     * --------------------------------------------------------------------
     **/
    public String getIpAddr(HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");
        if (ObjectUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ObjectUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ObjectUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ObjectUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ObjectUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    /**
     * --------------------------------------------------------------------
     * ■UUID ■sangheon
     * --------------------------------------------------------------------
     **/
    public String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * --------------------------------------------------------------------
     * ■modelAndView 생성 함수 ■sangheon
     * --------------------------------------------------------------------
     **/


    /**
     * --------------------------------------------------------------------
     * ■날짜 형 선언 ■sangheon
     * --------------------------------------------------------------------
     **/
    public enum DateFormat {
        YYYYMMDD,
        YYYYMM,
        YYYY,
        YYYYMMDDHH24MISS,
        YYYYMMDDHH24MISS_NOSPACE,
        HH24MISS,
        HH24,
        MI,
        SS,
        YYYYMMDDHH24MISSMS,
        YYYYMMDD2,
        YYYYMM_KOR
    }

    /**
     * --------------------------------------------------------------------
     * ■날짜 형 문자열로 변경 함수 ■sangheon
     * --------------------------------------------------------------------
     **/
    public String convertDate(DateFormat format, Date date) {
        String strRetValue = "";

        SimpleDateFormat simpleDateFormatV1 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat simpleDateFormatV2 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        SimpleDateFormat simpleDateFormatV3 = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat simpleDateFormatV4 = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat simpleDateFormatV5 = new SimpleDateFormat("yyyyMM");
        SimpleDateFormat simpleDateFormatV6 = new SimpleDateFormat("yyyy");
        SimpleDateFormat simpleDateFormatV7 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        SimpleDateFormat simpleDateFormatV8 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormatV9 = new SimpleDateFormat("yyyy년 MM월");

        switch (format) {
            case YYYYMMDDHH24MISS:
                strRetValue = simpleDateFormatV2.format(date);
                break;

            case YYYYMMDDHH24MISS_NOSPACE:
                strRetValue = simpleDateFormatV3.format(date);
                break;

            case HH24MISS:
                strRetValue = simpleDateFormatV4.format(date);
                break;

            case YYYYMM:
                strRetValue = simpleDateFormatV5.format(date);
                break;

            case YYYY:
                strRetValue = simpleDateFormatV6.format(date);
                break;

            case YYYYMMDDHH24MISSMS:
                strRetValue = simpleDateFormatV7.format(date);
                break;

            case YYYYMMDD2:
                strRetValue = simpleDateFormatV8.format(date);
                break;

            case YYYYMM_KOR:
                strRetValue = simpleDateFormatV9.format(date);
                break;

            case YYYYMMDD:
            default:
                strRetValue = simpleDateFormatV1.format(date);
                break;
        }

        return strRetValue;
    }

    public String convertDate(DateFormat format) {
        return convertDate(format, new Date());
    }

    // 수익년월 (현재-1 ~ 현재 -3, 총 3개월)
    public List<String> getSaleYearMonthList() {

        List<String> saleYearMonthList = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");

        int SALE_MONTH_START = 1;
        int SALE_MONTH_END = 3;
        for (int inx = SALE_MONTH_START; inx <= SALE_MONTH_END; inx++) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -inx);
            String strSaleYearMonth = dateFormat.format(cal.getTime()).substring(0, 6);
            saleYearMonthList.add(strSaleYearMonth);

        }

        return saleYearMonthList;
    }

    private Date add(final Date date, final int calendarField, final int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    public Date addMonths(final Date date, final int amount) {
        return add(date, Calendar.MONTH, amount);
    }

    /**
     * --------------------------------------------------------------------
     * ■디렉토리 확인 함수 ■sangheon
     * --------------------------------------------------------------------
     **/
    public File checkDirByPath(String strPath) {
        StringBuilder strFullPath = new StringBuilder();
        String[] arrPath = strPath.split("\\/");
        File filePath = new File(strPath);

        for (int i = 1; i < arrPath.length; i++) {
            strFullPath.append("/").append(arrPath[i]);

            filePath = new File(strFullPath.toString());

            if (!filePath.isDirectory())
                filePath.mkdir();
        }

        return filePath;
    }


    public String GetToday() throws GlobalException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();

        return simpleDateFormat.format(calendar.getTime());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public OrderSpecifier<?> getOrderSpecifier(Class classObject, String key, String direction, String fieldName) {

        Order order;

        if (ObjectUtils.isEmpty(fieldName)) fieldName = key;

        if (ObjectUtils.isEmpty(direction)) {
            order = Order.DESC;
        } else {
            if (direction.equalsIgnoreCase("ASC")) order = Order.ASC;
            else order = Order.DESC;
        }

        PathBuilder<?> pathBuilder
                = new PathBuilder(classObject, fieldName);
        return new OrderSpecifier(order, pathBuilder);
    }

    public LocalDateTime getFromDate(String fromYmd) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(fromYmd + " 00:00:00", formatter);
    }

    public LocalDateTime getToDate(String toYmd) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        return LocalDateTime.parse(toYmd + " 23:59:59.999", formatter);
    }

    public LocalDateTime getFromDate(LocalDate fromYmd) {
        return fromYmd.atTime(0, 0);
    }

    public LocalDateTime getToDate(LocalDate toYmd) {
        return toYmd.atTime(23, 59, 59, 999999999);
    }

}
