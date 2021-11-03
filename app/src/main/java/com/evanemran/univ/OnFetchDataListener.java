package com.evanemran.univ;

import java.util.List;

public interface OnFetchDataListener {
    void onResponse(List<APIResponse> responses, String message);
    void onError(String message);
}
