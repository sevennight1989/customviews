package com.android.zp.base;

public class LR {
    /*********************************dimen***********************************************************/
    public final static class dimen {

        private static int getDimen(String str) {
            return Util_Resource.getDimenIdentifer(BaseApplicaion.getInstance().getContext(), str);
        }
    }

    /**********************************layout********************************************************************/
    public final static class layout {
        public static int activity_main = getLayout("activity_main");

        private static int getLayout(String str) {
            return Util_Resource.getLayoutIdentifier(BaseApplicaion.getInstance().getContext(), str);
        }
    }

    /**********************************id********************************************************************/
    public final static class id {

        /*****************  activity_sdk  ****************/
        public static int fatty_avatar = getId("fatty_avatar");
        public static int fatty_hello = getId("fatty_hello");


        private final static int getId(String str) {
            return Util_Resource.getIdIdentifier(BaseApplicaion.getInstance().getContext(), str);
        }


    }

    /**********************************style********************************************************************/
    public final static class style {

        private static int getStyle(String str) {
            return Util_Resource.getStyleIdentifer(BaseApplicaion.getInstance().getContext(), str);
        }
    }

    /**********************************style********************************************************************/
    public final static class color {
        private static int getColor(String str) {
            return Util_Resource.getColorIdentifer(BaseApplicaion.getInstance().getContext(), str);
        }
    }


    /**********************************anim********************************************************************/
    public final static class anim {
        private static int getAnim(String str) {
            return Util_Resource.getAnimIdentifer(BaseApplicaion.getInstance().getContext(), str);
        }
    }

    /**********************************mipmap********************************************************************/
    public final static class mipmap {

        private static int getMipmap(String str) {
            return Util_Resource.getMipmapIdentifer(BaseApplicaion.getInstance().getContext(), str);
        }
    }

    /**********************************drawable********************************************************************/
    public final static class drawable {


        public static int ic_baseline_emoji_emotions_24 = getDrawable("ic_baseline_emoji_emotions_24");

        private static int getDrawable(String str) {
            return Util_Resource.getDrawableIdentifer(BaseApplicaion.getInstance().getContext(), str);
        }
    }

    public final static class string {

        public static int hello_id = getStringId("fatty_hello");
        public static String hello = getString(hello_id);

        private static int getStringId(String str) {
            return Util_Resource.getStringIdentifer(BaseApplicaion.getInstance().getContext(), str);
        }

        private static String getString(int id) {
            return BaseApplicaion.getInstance().getContext().getResources().getString(id);
        }
    }

}