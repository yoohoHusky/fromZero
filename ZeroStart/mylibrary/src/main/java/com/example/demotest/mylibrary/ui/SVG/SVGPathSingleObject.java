package com.example.demotest.mylibrary.ui.SVG;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SVGPathSingleObject {

    private static final String TAG = "SVGPathObject";
    private final PathMeasure pathMeasure;

    private Path srcPath;
    private Path currentPath;
    private float mFraction = 1.0F;
    private float mScale = 1.0F;
    private SVGBean srcBean;

    private float start;
    private float end;

    SVGPathSingleObject(String pathStr, float scale) {
        srcBean = str2SVGBean(pathStr);
        mScale = scale;
        pathMeasure = new PathMeasure();
        transPath(srcBean);
    }

    public void setFraction(float fraction) {
        if (srcBean == null) {
            Log.e(TAG, "srcBean empty");
            return;
        }
        mFraction = fraction;
    }

    public Path getSrcPath() {
        return srcPath;
    }

    public Path getPath() {
        return getCurrentPath(srcPath);
    }

    private Path getCurrentPath(Path srcBean) {
        if (srcBean == null) return null;

        pathMeasure.setPath(srcPath, true);
        Path dst = new Path();
        dst.rLineTo(0,0);

        end = pathMeasure.getLength() * mFraction;
        pathMeasure.getSegment(0, end, dst, true);
        return dst;
    }

    public void onRelease() {
        if (srcBean != null && srcBean.actBeanList != null) srcBean.actBeanList.clear();
        srcBean = null;
    }



    /******************************************************/


    private void transPath(SVGBean currentSVGBean) {
        if (srcPath == null) srcPath = new Path();
        srcPath.reset();
        for (SVGBean.SVGActBean actBean : currentSVGBean.actBeanList) {
            switch (actBean.act) {
                case SVGBean.SVGActBean.ACTION_M:
                    srcPath.moveTo(actBean.coordList.get(0) * mScale, actBean.coordList.get(1) * mScale);
//                    Log.e(TAG, "ACTION_M   " + actBean.coordList.get(0) * mScale + "   :   " +  actBean.coordList.get(1) * mScale);
                    break;
                case SVGBean.SVGActBean.ACTION_M_1:
                    srcPath.rMoveTo(actBean.coordList.get(0) * mScale, actBean.coordList.get(1) * mScale);
//                    Log.e(TAG, "ACTION_M_1   " + actBean.coordList.get(0) * mScale + "   :   " +  actBean.coordList.get(1) * mScale);
                    break;
                case SVGBean.SVGActBean.ACTION_Q:
                    srcPath.quadTo(actBean.coordList.get(0) * mScale, actBean.coordList.get(1) * mScale, actBean.coordList.get(2) * mScale, actBean.coordList.get(3) * mScale);
//                    Log.e(TAG, "ACTION_Q");
                    break;
                case SVGBean.SVGActBean.ACTION_Q_1:
                    srcPath.rQuadTo(actBean.coordList.get(0) * mScale, actBean.coordList.get(1) * mScale, actBean.coordList.get(2) * mScale, actBean.coordList.get(3) * mScale);
//                    Log.e(TAG, "ACTION_Q_1");
                    break;
                case SVGBean.SVGActBean.ACTION_C:
                    srcPath.cubicTo(actBean.coordList.get(0) * mScale, actBean.coordList.get(1) * mScale, actBean.coordList.get(2) * mScale, actBean.coordList.get(3) * mScale, actBean.coordList.get(4) * mScale, actBean.coordList.get(5) * mScale);
//                    Log.e(TAG, "ACTION_C");
                    break;
                case SVGBean.SVGActBean.ACTION_C_1:
                    srcPath.rCubicTo(actBean.coordList.get(0) * mScale, actBean.coordList.get(1) * mScale, actBean.coordList.get(2) * mScale, actBean.coordList.get(3) * mScale, actBean.coordList.get(4) * mScale, actBean.coordList.get(5) * mScale);
//                    Log.e(TAG, "ACTION_C_1");
                    break;
                case SVGBean.SVGActBean.ACTION_L:
                    srcPath.lineTo(actBean.coordList.get(0) * mScale, actBean.coordList.get(1) * mScale);
//                    Log.e(TAG, "ACTION_L   " + actBean.coordList.get(0) * mScale + "   :   " +  actBean.coordList.get(1) * mScale);
                    break;
                case SVGBean.SVGActBean.ACTION_L_1:
                    srcPath.rLineTo(actBean.coordList.get(0) * mScale, actBean.coordList.get(1) * mScale);
//                    Log.e(TAG, "ACTION_L_1   " + actBean.coordList.get(0) * mScale + "   :   " +  actBean.coordList.get(1) * mScale);
                    break;
                case SVGBean.SVGActBean.ACTION_Z:
                    srcPath.close();
//                    Log.e(TAG, "ACTION_Z");
                    break;
            }
        }
    }



    private List<Float> calculateCoord(List<Float> coordList, List<Float> coordList2, float fraction) {
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
