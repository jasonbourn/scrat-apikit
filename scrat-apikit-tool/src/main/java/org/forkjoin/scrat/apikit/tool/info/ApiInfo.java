package org.forkjoin.scrat.apikit.tool.info;

import org.forkjoin.scrat.apikit.core.ActionType;
import org.forkjoin.scrat.apikit.tool.AnalyseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * api 信息
 *
 * @author zuoge85 on 15/6/11.
 */
public class ApiInfo extends ModuleInfo {
    private Map<String, Map<ActionType, ApiMethodInfo>> methodInfosMap = new HashMap<>();
    private ArrayList<ApiMethodInfo> methodInfos = new ArrayList<>();

    public void addApiMethod(ApiMethodInfo apiMethodInfo) {
        Map<ActionType, ApiMethodInfo> map = methodInfosMap.computeIfAbsent(apiMethodInfo.getUrl(), k -> new HashMap<>());
        if (map.put(apiMethodInfo.getType(), apiMethodInfo) != null) {
            throw new AnalyseException(apiMethodInfo + "apiMethodInfo严重错误,重复的定义:url" + apiMethodInfo.getUrl() + ",type:" + apiMethodInfo.getType());
        }
        apiMethodInfo.setIndex(methodInfos.size());
        methodInfos.add(apiMethodInfo);
    }

    public ApiMethodInfo get(String url, ActionType type) {
        Map<ActionType, ApiMethodInfo> map = methodInfosMap.get(url);
        if (map == null) {
            return null;
        }
        return map.get(type);
    }

    public ArrayList<ApiMethodInfo> getMethodInfos() {
        return methodInfos;
    }

    @Override
    public String toString() {
        return "ApiInfo{" +
                "methodInfos=" + methodInfosMap +
                '}';
    }

}
