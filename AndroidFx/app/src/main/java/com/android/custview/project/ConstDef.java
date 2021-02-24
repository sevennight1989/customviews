/**
 * 2020.08.27 add api PRE_DESTINATION ,STATE_CHANGE.
 * 2020.09.01 add api REQ_ID_DELETE_ROUTE.
 */
package com.android.custview.project;

public interface ConstDef {

    /**
     * go home
     */
    String REQ_ID_HOME = "req_1000";
    /**
     * go work
     */
    String REQ_ID_OFFICE = "req_1001";
    /**
     * quit navi
     */
    String REQ_ID_EXIT = "req_1012";
    /**
     * zoom in
     */
    String REQ_ID_ZOOM_IN = "req_1013";
    /**
     * zoom out
     */
    String REQ_ID_ZOOM_OUT = "req_1014";
    /**
     * switch map to 2D mode
     */
    String REQ_ID_SWITCH_MAP_MODE_2D = "req_1015";
    /**
     * switch map to 3D mode
     */
    String REQ_ID_SWITCH_MAP_MODE_3D = "req_1016";
    /**
     * switch map to 2D north up mode
     */
    String REQ_ID_SWITCH_MAP_MODE_NORTH_UP = "req_1017";
    /**
     * Is navigating?
     */
    String REQ_ID_QUERY_NAV_STATUS = "req_1018";
    /**
     * Home / Work is set?
     */
    String REQ_ID_QUERY_ADDRESS_SET = "req_1019";
    /**
     * Get zoom status  max, min, current
     */
    String REQ_ID_GET_ZOOM_STATUS = "req_1020";
    /**
     * query poi address
     */
    String REQ_ID_GET_ADDRESS_POI_LIST = "req_1021";
    /**
     * query nearby poi address
     */
    String REQ_ID_GET_CATEGORY_POI_LIST = "req_1022";
    /**
     * Get view mode 2d,3d,2dh
     */
    String REQ_ID_GET_VIEW_MODE = "req_1023";
    /**
     * reset the zoom of the map
     */
    String REQ_ID_ZOOM_RESET = "req_1024";
    /**
     * nav to address by id
     */
    String REQ_ID_NAV_TO_DESTINATION = "req_1025";
    /**
     * Cancel navigation route
     */
    String REQ_ID_DELETE_ROUTE = "req_1026";

    String REQ_ID_QUERY_PAGE_INFO = "req_1027";

    String REQ_ID_MULTIPLE_STATUS = "req_1028";

    String REQ_ID_SET_NAV_BACKGROUND = "req_1029";

    String REQ_ID_GET_COLOR_MODE = "req_1030";
    /**
     * switch map to day mode
     */
    String REQ_ID_SWITCH_MAP_MODE_DAY = "req_1031";
    /**
     * switch map to night mode
     */
    String REQ_ID_SWITCH_MAP_MODE_NIGHT = "req_1032";
    /**
     * switch map to auto mode
     */
    String REQ_ID_SWITCH_MAP_MODE_AUTO = "req_1033";

    String REQ_ID_ROUTE_OVERVIEW = "req_1034";

    String REQ_ID_CANCEL = "req_1035";

    String REQ_ID_ROUTE_DISTANCE = "req_1036";

    String REQ_ID_ROUTE_TIME = "req_1037";

    String REQ_ID_GET_TRAFFIC_INFO = "req_1038";

    String REQ_ID_OPEN_TRAFFIC = "req_1039";

    String REQ_ID_CLOSE_TRAFFIC = "req_1040";

    String REQ_ID_OPEN_HIGHWAY = "req_1041";

    String REQ_ID_CLOSE_HIGHWAY = "req_1042";

    String REQ_ID_OPEN_PAIDROAD = "req_1043";

    String REQ_ID_CLOSE_PAIDROAD = "req_1044";

    String REQ_ID_OPEN_FERRY = "req_1045";

    String REQ_ID_CLOSE_FERRY = "req_1046";

    String REQ_ID_OPEN_PERUSE_TOLL = "req_1047";

    String REQ_ID_CLOSE_PERUSE_TOLL = "req_1048";

    String REQ_ID_OPEN_UNPAVE_ROAD = "req_1049";

    String REQ_ID_CLOSE_UNPAVE_ROAD = "req_1050";

    String REQ_ID_SWITCH_MAP = "req_1051";

    String REQ_ID_GET_LICENSE = "req_1052";

    String REQ_ID_SELECT_DESTINATION = "req_1053";

    String REQ_ID_ACC_STATUS = "req_1054";

    /**
     * The below two apis in Navicontroller API_v2 have been replaced by  REQ_ID_GET_ZOOM_STATUS in Navicontroller API_v3.
     * As confirm with Bao on 2020.08.19, for convenience of develop or test, just use them temporarily.
     */
    @Deprecated
    String REQ_ID_GET_IS_MAX_ZOOM = "req_2000";
    @Deprecated
    String REQ_ID_GET_IS_MIN_ZOOM = "req_2001";




    /** Head unit use this cmd to pause tts on nav by navController.
     *  { "type":"external_command","id":"req_3001","command":"media_control","params":{"tts":"pause"} }
     */
    String REQ_ID_PAUSE_TTS = "req_3001";
    /** Head unit use this cmd to resume tts on nav by navController.
     *  { "type":"external_command","id":"req_3002","command":"media_control","params":{"tts":"resume"} }
     */
    String REQ_ID_RESUME_TTS = "req_3002";

    /** Head unit use this cmd to send  the result of audio focus to nav by navController.   audio_focus 1: success ,others :failed
     *  { "type":"external_command","id":"req_3003","command":"media_control","params":{"audio_focus":1} }
     */
    String REQ_ID_REQ_AUDIO_FOCUS_RET = "req_3003";


    /**
     * The belows are used to sync data between nav and head unit,  also use navController.
     */

    /**
     * Head unit send destination data to nav module on head unit , then nav module receive destination data and start navigation.
     * Pateo define the data type.
     *  {"type":"external_command","id":"nav_req_1000","command":"notify","params":{"destination":"destination data"}}
     */
    String PRE_DESTINATION = "nav_req_1000";

    /**
     * When mobile connect to Head unit or disconnect, head unit will send status to Nav module on head unit.
     * Pateo define the data type.
     *  {"type":"external_command","id":"nav_req_1001","command":"notify","params":{"state":"0"}}   "0": connect , "1": disconnect.
     */
    String STATE_CHANGE = "nav_req_1001";

    /**
     * add user favourite
     */
    String ADD_NAV_USER_FAV = "nav_req_001";
    /**
     * update user favourite
     */
    String UPDATE_NAV_USER_FAV = "nav_req_002";
    /**
     * cancel user favourite
     */
    String CANCEL_NAV_USER_FAV = "nav_req_003";
    /**
     * query user favourite
     */
    String QUERY_NAV_USER_FAV = "nav_req_004";
    /**
     * add nav history record.
     */
    String ADD_NAV_HISTORY = "nav_req_005";
    /**
     * clear all nav history records.
     */
    String CLEAR_NAV_HISTORY = "nav_req_006";
    /**
     * delete a history record with type
     */
    String DELETE_NAV_HISTORY = "nav_req_007";
    /**
     * query all nav history records.
     */
    String QUERY_NAV_HISTORY = "nav_req_008";


    String CMD_NAV_EXIT = "exit";
    String CMD_ZOOM_IN = "map_control";
    String CMD_ZOOM_OUT = "map_control";
    String CMD_NAV_HOME = "navigation";
    String CMD_NAV_OFFICE = "navigation";
    String CMD_DELETE_ROUTE = "navigation";
    String CMD_SWITCH_MAP_MODE_2D = "map_control";
    String CMD_SWITCH_MAP_MODE_3D = "map_control";
    String CMD_SWITCH_MAP_MODE_NORTH_UP = "map_control";
    String CMD_SWITCH_MAP_MODE_DAY = "map_control";
    String CMD_SWITCH_MAP_MODE_NIGHT = "map_control";
    String CMD_SWITCH_MAP_MODE_AUTO = "map_control";
    String CMD_QUERY_NAV_STATUS = "navigation";
    String CMD_QUERY_HOME_ADDRESS_SET = "get_home";
    String CMD_QUERY_COMPANY_ADDRESS_SET = "get_work";
    String CMD_GET_ZOOM_STATUS = "map_control";
    String CMD_GET_VIEW_MODE = "map_control";
    String CMD_QUERY_ADDRESS_POI_LIST = "search";
    String CMD_QUERY_CATEGORY_POI_LIST = "search";
    String CMD_ZOOM_RESET = "map_control";
    String CMD_NAV_TO_DESTINATION = "menu_control";
    String CMD_ID_GET_IS_MAX_ZOOM = "query";
    String CMD_ID_GET_IS_MIN_ZOOM = "query";

    String CMD_ID_PRE_DESTINATION = "navigation";
    String CMD_ID_STATUS_CHANGE = "notify";
    String CMD_ID_QUERY_PAGE_INFO = "query";
    String CMD_SWITCH_STATE = "switch_state";
    String CMD_CANCEL = "menu_control";
    String CMD_SELECT_DESTINATION = "menu_control";
    String CMD_MEDIA_CONTROL = "media_control";
    String CMD_QUERY = "query";
    String CMD_MULTIPLE_STATUS = "get_status";
    String CMD_SETTINGS = "setting";
    String CMD_SET_NAV_BACKGROUND = "menu_control";
    String CMD_GET_LICENSE = "get_licenses";
    String CMD_ACC_STATUS = "notify_acc_status";


    /**  Receive response
     *   {"type":"external_command","id":"<an unique value>","command":"cloudResponse","params":{"func":"","code":"","result":""}}
     * This command is used to send cloud response to nav module on head unit by using navController.
     * The value of func is refers on IPateoResponseApi.java.  One of methods's name below :
     * onResponseAddNaiUserFavorite,    onResponseUpdateNavUserFavorite,   onResponseCancelNavUserFavorite    onResponseGetNaiUserFavorite,
     * onResponseAddNavHistory,         onResponseDeleteNavHistory,        onResponseQueryNavHistory,         onResponseClearAllNavHistory.
     * Such as "func":"onResponseAddNaiUserFavorite".
     * The value of code is match with the method's param  statusCode.
     * The value of result is match with the method's param result.
     *
     */
}
