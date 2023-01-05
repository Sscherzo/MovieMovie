package com.example.moviemovie.review.helper;

import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class FileHelper {
    // 싱글톤 패턴 시작
    private static FileHelper instance = null;

    public static FileHelper getInstance() {
        if(instance == null) instance = new FileHelper();

        return instance;
    }
    private FileHelper() {}
    // 싱글톤 패턴 끝

    public boolean write(String filePath, byte[] data) {
        boolean result = false;

        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath);
            // 파일 쓰기
            out.write(data);
            // 저장 성공
            result = true;
            Log.d("[INFO]", "파일 저장 성공 >> " + filePath);
        } catch (FileNotFoundException e) {
            Log.d("[ERROR]", "지정된 경로를 찾을 수 없음 >> " + filePath);
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("[ERROR]", "파일 저장 실패 >> " + filePath);
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("[ERROR]", "알 수 없는 에러 >> " + filePath);
            e.printStackTrace();
        } finally {
            try {
                if(out != null) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public byte[] read(String filePath) {
        byte[] data = null;
        InputStream in = null;
        try {
            in = new FileInputStream(filePath);
            // 파일의 내용을 저장할 변수 생성
            // in.available() : 파일의 크기 구하기
            data = new byte[in.available()];
            in.read(data);
            Log.d("[INFO]", "파일 읽기 성공 >> " + filePath);
        } catch (FileNotFoundException e) {
            Log.d("[ERROR]", "지정된 경로를 찾을 수 없음 >> " + filePath);
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("[ERROR]", "파일 읽기 실패 >> " + filePath);
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("[ERROR]", "알 수 없는 에러 >> " + filePath);
            e.printStackTrace();
        } finally {
            try {
                if(in != null) in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public boolean writeString(String filePath, String content, String encType) {
        boolean result = false;
        byte[] data = null;

        try {
            data = content.getBytes(encType);
            result = write(filePath, data);     // 파일에 저장
        } catch (UnsupportedEncodingException e) {
            Log.d("[ERROR]", "인코딩 지정 에러");
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("[ERROR]", "알 수 없는 에러");
            e.printStackTrace();
        }
        return result;
    }

    public String readString(String filePath, String encType) {
        String content = null;
        byte[] data = read(filePath);       // 파일 읽기
        // byte[]값을 문자열로 변환
        try {
            content = new String(data, encType);
        } catch (UnsupportedEncodingException e) {
            Log.d("[ERROR]", "인코딩 지정 에러");
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("[ERROR]", "알 수 없는 에러");
            e.printStackTrace();
        }
        return content;
    }

}