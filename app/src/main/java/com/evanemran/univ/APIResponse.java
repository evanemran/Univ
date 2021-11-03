package com.evanemran.univ;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class APIResponse {
    String alpha_two_code = "";
    List<String> web_pages = null;
    String name = "";
    String country = "";
    List<String> domains = null;
    @SerializedName("state-province")
    String state_province = "";

    public String getAlpha_two_code() {
        return alpha_two_code;
    }

    public List<String> getWeb_pages() {
        return web_pages;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public List<String> getDomains() {
        return domains;
    }

    public String getState_province() {
        return state_province;
    }
}
