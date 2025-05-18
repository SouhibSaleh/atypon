package org.example.Services;
import org.example.Repos.AdminRepo;
import org.example.Repos.StudentRepo;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class AuthValidator {

    public static List<String> getToken(String token, String position){
        if(token==null||token.length()==0)return null;
        if(!token.startsWith("mybasic")) return null;
        token = token.split(" ")[1];
        List<String>  creds =new ArrayList<>(Arrays.asList((new String(Base64.getDecoder().decode(token),
                StandardCharsets.UTF_8))
                .split(" ")));
        if (creds == null || creds.size() != 3) {

            return null;
        }

        if (!creds.get(2).equals(position)){
            return null;
        }
        String id="";
    if(position.equals("student"))
        id = new StudentRepo().getUserId(creds.get(0), creds.get(1));
        else   id = new AdminRepo().getAdminId(creds.get(0), creds.get(1));
        if (id.equals("-1")) {
            return null;
        }
        creds.add(id);
        return creds;
    };

}
