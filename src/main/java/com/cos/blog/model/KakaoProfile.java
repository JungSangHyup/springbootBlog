package com.cos.blog.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class KakaoProfile {

    public Integer id;
    public String connected_at;
    public Properties properties;

    @Data
    public class Properties {

        public String nickname;
        public String profile_image;
        public String thumbnail_image;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }
    }

    public KakaoAccount kakaoAccount;

    @Data
    public class KakaoAccount {
        public Boolean profileNicknameNeedsAgreement;
        public Boolean profileImageNeedsAgreement;
        public Profile profile;
        public Boolean has_email;
        public Boolean emailNeedsAgreement;
        public Boolean isEmailValid;
        public Boolean isEmailVerified;
        public String email;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

        @Data
        public class Profile {
            public String nickname;
            public String thumbnailImageUrl;
            public String profileImageUrl;
            public Boolean isDefaultImage;
            private Map<String, Object> additionalProperties = new HashMap<String, Object>();

            public Map<String, Object> getAdditionalProperties() {
                return this.additionalProperties;
            }

            public void setAdditionalProperty(String name, Object value) {
                this.additionalProperties.put(name, value);
            }
        }
    }
}


