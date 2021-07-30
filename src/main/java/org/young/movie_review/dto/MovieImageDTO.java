package org.young.movie_review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieImageDTO {
    private String uuid;
    private String imgName;
    private String path;

    public String getImageURL(){
        try{
            return URLEncoder.encode(path+"/"+uuid+"_"+imgName,"UTF-8");
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return "";
    }
    public String getThumbnailURL(){
        try{
            //인코딩된 형태로 저 문자열을 브라우저로 전달하는 것
            return URLEncoder.encode(path+"/"+"s_"+uuid+"_"+imgName,"UTF-8");
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return "";
    }
}
