package com.thomas.cart.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.Base64;

@NoArgsConstructor
public class ParseJWT {

    public static String getUserId(String tokenJWT) throws JsonProcessingException {
        String[] split_string = tokenJWT.split("\\.");
        String base64EncodedBody = split_string[1];
        Base64 base64Url = new Base64(true);

        String body = new String(base64Url.decode(base64EncodedBody));
        final ObjectNode node = new ObjectMapper().readValue(body, ObjectNode.class);

        if(node.has("sub")){
            return String.valueOf(node.get("sub"));
        }

        return "-1";
    }

}
