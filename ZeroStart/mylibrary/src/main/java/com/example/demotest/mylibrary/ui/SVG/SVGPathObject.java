package com.example.demotest.mylibrary.ui.SVG;

import android.graphics.Path;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SVGPathObject {

    private static final String TAG = "SVGPathObject";
    private boolean isSingleSVG;
    private SVGBean fromSVGBean;
    private SVGBean toSVGBean;
    private SVGBean currentSVGBean;

    private Path currentPath;
    private float mFraction = 1.0F;
    private float mScale = 1.0F;

    SVGPathObject(String pathStr, float scale) {
        currentSVGBean = str2SVGBean(pathStr);
        mScale = scale;
        isSingleSVG = true;
    }

    SVGPathObject(String fromPathStr, String toPathStr, float scale) {
        fromSVGBean = str2SVGBean(fromPathStr);
        toSVGBean = str2SVGBean(toPathStr);
        currentSVGBean = str2SVGBean(fromPathStr);
        mScale = scale;
        isSingleSVG = false;
    }

    public void setFraction(float fraction) {
        if (fromSVGBean == null || toSVGBean == null) {
            Log.e(TAG, "fromSVGBean or toSVGBean is empty");
            return;
        }
        if (fromSVGBean.actBeanList.size() == 0 || toSVGBean.actBeanList.size() == 0) {
            Log.e(TAG, "fromSVGBean or toSVGBean is invalid");
            return;
        }

        mFraction = fraction;
        if (isSingleSVG) {
        } else {
            updateCurSVGBean(fromSVGBean, toSVGBean, fraction);
        }
    }

    public Path getPath() {
        transPath(currentSVGBean);
        return currentPath;
    }

    public void onRelease() {
        if (fromSVGBean != null && fromSVGBean.actBeanList != null) fromSVGBean.actBeanList.clear();
        if (toSVGBean != null && toSVGBean.actBeanList != null) toSVGBean.actBeanList.clear();
        if (currentSVGBean != null && currentSVGBean.actBeanList != null) currentSVGBean.actBeanList.clear();

        fromSVGBean = null;
        toSVGBean = null;
        currentSVGBean = null;
    }



    /******************************************************/


    private void transPath(SVGBean currentSVGBean) {
        if (currentPath == null) currentPath = new Path();
        currentPath.reset();
        for (SVGBean.SVGActBean actBean : currentSVGBean.actBeanList) {
            switch (actBean.act) {
                case SVGBean.SVGActBean.ACTION_M:
                    currentPath.moveTo(actBean.coordList.get(0) * mScale, actBean.coordList.get(1) * mScale);
//                    Log.e(TAG, "ACTION_M   " + actBean.coordList.get(0) * mScale + "   :   " +  actBean.coordList.get(1) * mScale);
                    break;
                case SVGBean.SVGActBean.ACTION_M_1:
                    currentPath.rMoveTo(actBean.coordList.get(0) * mScale, actBean.coordList.get(1) * mScale);
//                    Log.e(TAG, "ACTION_M_1   " + actBean.coordList.get(0) * mScale + "   :   " +  actBean.coordList.get(1) * mScale);
                    break;
                case SVGBean.SVGActBean.ACTION_Q:
                    currentPath.quadTo(actBean.coordList.get(0) * mScale, actBean.coordList.get(1) * mScale, actBean.coordList.get(2) * mScale, actBean.coordList.get(3) * mScale);
//                    Log.e(TAG, "ACTION_Q");
                    break;
                case SVGBean.SVGActBean.ACTION_Q_1:
                    currentPath.rQuadTo(actBean.coordList.get(0) * mScale, actBean.coordList.get(1) * mScale, actBean.coordList.get(2) * mScale, actBean.coordList.get(3) * mScale);
//                    Log.e(TAG, "ACTION_Q_1");
                    break;
                case SVGBean.SVGActBean.ACTION_C:
                    currentPath.cubicTo(actBean.coordList.get(0) * mScale, actBean.coordList.get(1) * mScale, actBean.coordList.get(2) * mScale, actBean.coordList.get(3) * mScale, actBean.coordList.get(4) * mScale, actBean.coordList.get(5) * mScale);
//                    Log.e(TAG, "ACTION_C");
                    break;
                case SVGBean.SVGActBean.ACTION_C_1:
                    currentPath.rCubicTo(actBean.coordList.get(0) * mScale, actBean.coordList.get(1) * mScale, actBean.coordList.get(2) * mScale, actBean.coordList.get(3) * mScale, actBean.coordList.get(4) * mScale, actBean.coordList.get(5) * mScale);
//                    Log.e(TAG, "ACTION_C_1");
                    break;
                case SVGBean.SVGActBean.ACTION_L:
                    currentPath.lineTo(actBean.coordList.get(0) * mScale, actBean.coordList.get(1) * mScale);
//                    Log.e(TAG, "ACTION_L   " + actBean.coordList.get(0) * mScale + "   :   " +  actBean.coordList.get(1) * mScale);
                    break;
                case SVGBean.SVGActBean.ACTION_L_1:
                    currentPath.rLineTo(actBean.coordList.get(0) * mScale, actBean.coordList.get(1) * mScale);
//                    Log.e(TAG, "ACTION_L_1   " + actBean.coordList.get(0) * mScale + "   :   " +  actBean.coordList.get(1) * mScale);
                    break;
                case SVGBean.SVGActBean.ACTION_Z:
                    currentPath.close();
//                    Log.e(TAG, "ACTION_Z");
                    break;
            }
        }
    }


    private void updateCurSVGBean(SVGBean from, SVGBean to, float fraction) {
        if (currentSVGBean == null) currentSVGBean = new SVGBean();

        currentSVGBean.actBeanList.clear();
        SVGBean.SVGActBean fromActBean;
        SVGBean.SVGActBean toActBean;
        SVGBean.SVGActBean tempActBean;
        for (int i=0; i<from.actBeanList.size(); i++) {
            tempActBean = new SVGBean.SVGActBean();
            fromActBean = from.actBeanList.get(i);
            toActBean = to.actBeanList.get(i);

            if (!fromActBean.act.equals(toActBean.act)) {
                Log.e(TAG, "updateCurSVGBean(), the actions of SVG path are different");
                return;
            }
            tempActBean.act = fromActBean.act;
            tempActBean.coordList = calculateCoord(fromActBean.coordList, toActBean.coordList, fraction);
            currentSVGBean.actBeanList.add(tempActBean);
        }
    }

    private List<Float> calculateCoord(List<Float> coordList, List<Float> coordList2, float fraction) {
        if (coordList == null || coordList2 == null) {
            return null;
        }
        List<Float> floats =  new ArrayList();
        for (int i=0; i<coordList.size(); i++) {
            float f1 = coordList.get(i);
            float f2 = coordList2.get(i);
            floats.add((f2 - f1)*fraction + f1);
        }
        return floats;
    }

    // pathStr:M 12.0,21.35 l -1.45,-1.32 C 5.4,15.36,2.0,12.28,2.0,8.5 Z
    private SVGBean str2SVGBean(String svgPath) {
        if (TextUtils.isEmpty(svgPath)) {
            Log.e(TAG, "svgPath is empty");
            return null;
        }
        String cleanPath = svgPath.trim();
        if (TextUtils.isEmpty(cleanPath)) return null;

        SVGBean svgBean = new SVGBean();
        String regStr = "([A-Za-z])\\s?([^A-Za-z]*)";
        Pattern pattern =  Pattern.compile(regStr);
        Matcher result = pattern.matcher(cleanPath);
        while (result.find()) {
            svgBean.actBeanList.add(str2SVGActBean(result.group(1).trim(), result.group(2).trim()));
        }
        return svgBean;
    }

    private SVGBean.SVGActBean str2SVGActBean(String actName, String coordName) {
        SVGBean.SVGActBean bean = new SVGBean.SVGActBean();
        bean.act = actName;
        if (!TextUtils.isEmpty(coordName)) {
            bean.coordList = coordStr2float(coordName);
        }
        return bean;
    }

//
//    private List<SVGBean.SVGAc                                                                                                                                                                                                                                                                                                                                                                                                            tBean> parsePathStr(String pathStr) {
//        if (pathStr == null) return null;
//        String cleanPath = pathStr.trim().toUpperCase();
//        if (TextUtils.isEmpty(cleanPath)) return null;
//
//        List actBeanList = new ArrayList<SVGBean.SVGActBean>();
//        String regStr = "[A-Za-z]\\s?[^A-Za-z]*";
//        Pattern pattern =  Pattern.compile(regStr);
//        Matcher result = pattern.matcher(cleanPath);
//        while (result.find()) {
//            String actStr = result.group().trim();
//            SVGBean.SVGActBean bean = new SVGBean.SVGActBean();
//            bean.act = actStr.split(" ")[0];
//            if (actStr.split(" ").length > 1) {
//                bean.coordList = coordStr2float(actStr.split(" ")[1]);
//            }
//            actBeanList.add(bean);
//        }
//        return actBeanList;
//    }

    private static List<Float> coordStr2float(String coordStr) {
        if (TextUtils.isEmpty(coordStr)) return null;
        List<Float> coordList = new ArrayList();
        String[] coords =  coordStr.split(",");
        for (String floatStr : coords) {
            coordList.add(Float.parseFloat(floatStr));
        }
        return coordList;
    }

    private static class SVGBean {
        List<SVGActBean> actBeanList = new ArrayList<>();
        private static class SVGActBean {
            public static final String ACTION_M = "M";
            public static final String ACTION_M_1 = "m";
            public static final String ACTION_L = "L";
            public static final String ACTION_L_1 = "l";
            public static final String ACTION_C = "C";
            public static final String ACTION_C_1 = "c";
            public static final String ACTION_Q = "Q";
            public static final String ACTION_Q_1 = "q";
            public static final String ACTION_Z = "Z";

            String act;
            List<Float> coordList;

        }
    }

    public static void test() {
        Log.e("SS", "test regZ".endsWith("Z") + "");
        String regStr = "([A-Za-z])\\s?([^A-Za-z]*)";
        String str = "M12.0,21.35 l -1.45,-1.32 C 5.4,15.36,2.0,12.28,2.0,8.5 Z";


        Pattern regReg =  Pattern.compile(regStr);
        Matcher result = regReg.matcher(str);
        while (result.find()) {
            Log.e("SS", result.group(1).trim() +"    " + result.group(2).trim() );
        }

    }
}
