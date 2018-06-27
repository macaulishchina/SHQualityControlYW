package com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass;

import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Dataclass.Idname;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.util.List;

/**
 * 作者： scj
 * 创建时间： 2018/5/10
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass
 */


public class PointInfo {


    /**
     * Result : True
     * message :
     * PointData : [{"MonitoringPointUid":"CCF33A69-ED62-4BA8-9CFB-F0E3540E0E90","PointId":"106","MonitoringPointName":"蒲汇塘泵站（浮标）","IsVideoOrNot":"0","Address":"","Des":"","Region":"徐汇","SiteType":"浮标站","Area":"","Valley":"太湖流域","heliu":"蒲汇塘","ControlType":"市控","RunStatus":"离线","EQI":"","CalEQIType":"河流","LastDataTime":"2018-05-11 13:11:00","X":"","Y":""},{"MonitoringPointUid":"2624670B-AFE2-4713-973A-32A7882E309E","PointId":"55","MonitoringPointName":"大朱厍（浮标）","IsVideoOrNot":"0","Address":"","Des":"","Region":"青浦","SiteType":"浮标站","Area":"商榻片","Valley":"太湖流域","heliu":"淀山湖","ControlType":"市控","RunStatus":"离线","EQI":"","CalEQIType":"河流","LastDataTime":"2018-05-11 13:11:00","X":"-51187.9305","Y":"-10429.4262"},{"MonitoringPointUid":"F49BE9AD-F207-46A2-B62D-3494A2620D1E","PointId":"27","MonitoringPointName":"前卫村桥","IsVideoOrNot":"0","Address":"","Des":"","Region":"崇明","SiteType":"岸边站","Area":"崇明岛片区","Valley":"长江流域","heliu":"","ControlType":"国控","RunStatus":"离线","EQI":"","CalEQIType":"河流","LastDataTime":"2018-05-11 13:11:00","X":"3555.0713","Y":"53496.3107"},{"MonitoringPointUid":"091A48C0-C98C-4FC7-9256-B913C8BF24FD","PointId":"15","MonitoringPointName":"淀峰","IsVideoOrNot":"0","Address":"","Des":"","Region":"青浦","SiteType":"固定站","Area":"青松片","Valley":"太湖流域","heliu":"","ControlType":"国控","RunStatus":"在线","EQI":"","CalEQIType":"河流","LastDataTime":"2018-05-11 13:11:00","X":"-46556.8242","Y":"-16056.3265"},{"MonitoringPointUid":"B0916791-568F-47C4-A15D-909466CCB019","PointId":"74","MonitoringPointName":"沈砖公路桥","IsVideoOrNot":"0","Address":"","Des":"","Region":"青浦","SiteType":"岸边站","Area":"青松片","Valley":"太湖流域","heliu":"华田泾","ControlType":"市控","RunStatus":"离线","EQI":"","CalEQIType":"河流","LastDataTime":"2018-05-11 13:11:00","X":"-35716.9175","Y":"-17531.6403"},{"MonitoringPointUid":"313B01C9-1123-4A6B-92B8-5389B1E8AEBF","PointId":"43","MonitoringPointName":"滴水湖（浮标）","IsVideoOrNot":"0","Address":"","Des":"","Region":"浦东新区","SiteType":"浮标站","Area":"浦东片","Valley":"太湖流域","heliu":"滴水湖","ControlType":"市控","RunStatus":"离线","EQI":"","CalEQIType":"河流","LastDataTime":"2018-05-11 13:11:00","X":"44007.9818","Y":"-37653.9281"},{"MonitoringPointUid":"1CCBC0F8-B20E-4109-B132-4E781B595C6B","PointId":"53","MonitoringPointName":"网箱渔场（浮标）","IsVideoOrNot":"0","Address":"","Des":"","Region":"青浦","SiteType":"浮标站","Area":"","Valley":"太湖流域","heliu":"淀山湖","ControlType":"市控","RunStatus":"离线","EQI":"","CalEQIType":"河流","LastDataTime":"2018-05-11 13:11:00","X":"-49533.2196","Y":"-15919.4910"},{"MonitoringPointUid":"EFE61DF8-1F3B-4E37-8752-80DEF12AAC8A","PointId":"54","MonitoringPointName":"急水港（浮标）","IsVideoOrNot":"0","Address":"","Des":"","Region":"青浦","SiteType":"浮标站","Area":"商榻片","Valley":"太湖流域","heliu":"淀山湖","ControlType":"市控","RunStatus":"离线","EQI":"","CalEQIType":"河流","LastDataTime":"2018-05-11 13:11:00","X":"-52386.8657","Y":"-14119.5136"},{"MonitoringPointUid":"4FDA0790-01F8-4E6E-8A35-F1F14ECC46B7","PointId":"16","MonitoringPointName":"黄渡站","IsVideoOrNot":"0","Address":"","Des":"","Region":"青浦","SiteType":"固定站","Area":"嘉宝北片","Valley":"太湖流域","heliu":"","ControlType":"国控","RunStatus":"在线","EQI":"","CalEQIType":"河流","LastDataTime":"2018-05-11 13:11:00","X":"-25253.0992","Y":"2973.1252"},{"MonitoringPointUid":"77FF32F9-1BA0-4FD9-9E5B-EEE0F22A788B","PointId":"51","MonitoringPointName":"急水港","IsVideoOrNot":"0","Address":"","Des":"","Region":"青浦","SiteType":"固定站","Area":"商榻片","Valley":"太湖流域","heliu":"淀山湖","ControlType":"国控","RunStatus":"离线","EQI":"","CalEQIType":"河流","LastDataTime":"2018-05-11 13:11:00","X":"-52589.1558","Y":"-13163.2108"},{"MonitoringPointUid":"941BE32C-1AB1-429A-B7BC-3DBFB8AA5C17","PointId":"58","MonitoringPointName":"四号航标（浮标）","IsVideoOrNot":"0","Address":"","Des":"","Region":"青浦","SiteType":"浮标站","Area":"","Valley":"太湖流域","heliu":"淀山湖","ControlType":"市控","RunStatus":"离线","EQI":"","CalEQIType":"河流","LastDataTime":"2018-05-11 13:11:00","X":"-48294.8934","Y":"-14046.4111"},{"MonitoringPointUid":"8501A66E-131C-48D6-8600-5D937283E7B5","PointId":"57","MonitoringPointName":"湖心北区（浮标）","IsVideoOrNot":"0","Address":"","Des":"","Region":"青浦","SiteType":"浮标站","Area":"","Valley":"太湖流域","heliu":"淀山湖","ControlType":"市控","RunStatus":"离线","EQI":"","CalEQIType":"河流","LastDataTime":"2018-05-11 13:11:00","X":"-47964.0958","Y":"-10906.4881"},{"MonitoringPointUid":"3D920C16-738F-4B4D-B2EE-59E0E18930D8","PointId":"45","MonitoringPointName":"南港大桥","IsVideoOrNot":"0","Address":"","Des":"","Region":"青浦","SiteType":"岸边站","Area":"青松片","Valley":"","heliu":"淀浦河","ControlType":"区控","RunStatus":"在线","EQI":"","CalEQIType":"河流","LastDataTime":"2018-05-11 13:11:00","X":"-38931.6271","Y":"-13062.3000"},{"MonitoringPointUid":"1BA59FB5-1D86-403F-9DA8-FB711B8CD2D9","PointId":"95","MonitoringPointName":"明珠湖（浮标）","IsVideoOrNot":"0","Address":"","Des":"","Region":"崇明","SiteType":"浮标站","Area":"崇明岛片区","Valley":"长江流域","heliu":"明珠湖","ControlType":"市控","RunStatus":"离线","EQI":"","CalEQIType":"河流","LastDataTime":"2018-05-11 13:11:00","X":"-20314.1296","Y":"55839.0374"},{"MonitoringPointUid":"754A18C1-8E7D-4D0E-B4F8-AC33218783D5","PointId":"28","MonitoringPointName":"七效港西桥","IsVideoOrNot":"0","Address":"","Des":"","Region":"崇明","SiteType":"岸边站","Area":"崇明岛片区","Valley":"长江流域","heliu":"","ControlType":"国控","RunStatus":"离线","EQI":"","CalEQIType":"河流","LastDataTime":"2018-05-11 13:11:00","X":"31011.3098","Y":"38417.5929"},{"MonitoringPointUid":"EEEB7277-378E-499D-9033-AB6D987CEBF5","PointId":"64","MonitoringPointName":"上澳塘泵站(浮标)","IsVideoOrNot":"0","Address":"","Des":"","Region":"徐汇","SiteType":"浮标站","Area":"","Valley":"太湖流域","heliu":"东上澳塘","ControlType":"市控","RunStatus":"离线","EQI":"","CalEQIType":"河流","LastDataTime":"2018-05-11 13:11:00","X":"-50170935.2400","Y":"-3458337.0300"},{"MonitoringPointUid":"409C5BE0-DD18-4C26-AF54-EF01F245CE2A","PointId":"22","MonitoringPointName":"苏州河梦清园（浮标）","IsVideoOrNot":"0","Address":"","Des":"","Region":"普陀","SiteType":"浮标站","Area":"蕴南片","Valley":"太湖流域","heliu":"","ControlType":"市控","RunStatus":"离线","EQI":"","CalEQIType":"河流","LastDataTime":"2018-05-11 13:11:00","X":"-2852.5222","Y":"1982.4906"},{"MonitoringPointUid":"B4F91019-012C-457B-AAC5-A3A4B909F7C3","PointId":"25","MonitoringPointName":"太浦河原水厂","IsVideoOrNot":"0","Address":"","Des":"","Region":"青浦","SiteType":"固定站","Area":"太南片","Valley":"太湖流域","heliu":"","ControlType":"国控","RunStatus":"在线","EQI":"","CalEQIType":"河流","LastDataTime":"2018-05-11 13:11:00","X":"-40275.1631","Y":"-23039.1819"},{"MonitoringPointUid":"38B1FD9C-DBF0-45BA-817F-292A1E9DEA62","PointId":"29","MonitoringPointName":"北湖（浮标）","IsVideoOrNot":"0","Address":"","Des":"","Region":"崇明","SiteType":"浮标站","Area":"崇明岛片区","Valley":"长江流域","heliu":"","ControlType":"市控","RunStatus":"离线","EQI":"","CalEQIType":"河流","LastDataTime":"2018-05-11 13:11:00","X":"12691.7657","Y":"51053.5906"},{"MonitoringPointUid":"B644ADBF-691B-466F-9E39-DC8C8A6E4135","PointId":"292","MonitoringPointName":"北新泾(浮标)","IsVideoOrNot":"0","Address":"","Des":"","Region":"长宁","SiteType":"岸边站","Area":"","Valley":"","heliu":"","ControlType":"区控","RunStatus":"离线","EQI":"","CalEQIType":"河流","LastDataTime":"2018-05-11 13:11:00","X":"0.0000","Y":"0.0000"},{"MonitoringPointUid":"F10B33FA-2667-471F-88B3-CF18F05227C8","PointId":"52","MonitoringPointName":"游泳场（浮标）","IsVideoOrNot":"0","Address":"","Des":"","Region":"青浦","SiteType":"浮标站","Area":"","Valley":"太湖流域","heliu":"淀山湖","ControlType":"市控","RunStatus":"离线","EQI":"","CalEQIType":"河流","LastDataTime":"2018-05-11 13:11:00","X":"-45614.4925","Y":"-14366.2484"},{"MonitoringPointUid":"E3C8A6F6-15C1-4AF0-906D-274EDFE50C1C","PointId":"137","MonitoringPointName":"蕴川路桥","IsVideoOrNot":"0","Address":"","Des":"","Region":"宝山","SiteType":"岸边站","Area":"嘉宝北片","Valley":"太湖流域","heliu":"练祁河","ControlType":"国控","RunStatus":"离线","EQI":"","CalEQIType":"河流","LastDataTime":"2018-05-11 13:11:00","X":"-5776.3484","Y":"22563.0486"},{"MonitoringPointUid":"F51C6C0A-E091-419B-9D46-739BB233DD3A","PointId":"56","MonitoringPointName":"湖心东区（浮标）","IsVideoOrNot":"0","Address":"","Des":"","Region":"青浦","SiteType":"浮标站","Area":"","Valley":"太湖流域","heliu":"淀山湖","ControlType":"市控","RunStatus":"离线","EQI":"","CalEQIType":"河流","LastDataTime":"2018-05-11 13:11:00","X":"-44134.8529","Y":"-11846.9943"},{"MonitoringPointUid":"2B064F74-2EC1-4C77-BFD5-DA4C982E835B","PointId":"59","MonitoringPointName":"白石矶（浮标）","IsVideoOrNot":"0","Address":"","Des":"","Region":"青浦","SiteType":"浮标站","Area":"","Valley":"太湖流域","heliu":"淀山湖","ControlType":"市控","RunStatus":"离线","EQI":"","CalEQIType":"河流","LastDataTime":"2018-05-11 13:11:00","X":"-54072.6545","Y":"-16944.5008"},{"MonitoringPointUid":"AC8B76D2-9AF5-45F8-B83E-D2B47E461885","PointId":"17","MonitoringPointName":"赵屯站","IsVideoOrNot":"0","Address":"","Des":"","Region":"青浦","SiteType":"固定站","Area":"青松片","Valley":"太湖流域","heliu":"","ControlType":"市控","RunStatus":"在线","EQI":"","CalEQIType":"河流","LastDataTime":"2018-05-11 13:11:00","X":"-37794.7867","Y":"4020.4835"}]
     */

    private String Result;
    private String message;
    private List<PointDataBean> PointData;

    public String getResult() {
        return Result;
    }

    public void setResult(String Result) {
        this.Result = Result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PointDataBean> getPointData() {
        return PointData;
    }

    public void setPointData(List<PointDataBean> PointData) {
        this.PointData = PointData;
    }

    @Table(name = "PointInfo")
    public static class PointDataBean implements Idname {
        /**
         * MonitoringPointUid : CCF33A69-ED62-4BA8-9CFB-F0E3540E0E90
         * PointId : 106
         * MonitoringPointName : 蒲汇塘泵站（浮标）
         * IsVideoOrNot : 0
         * Address :
         * Des :
         * Region : 徐汇
         * SiteType : 浮标站
         * Area :
         * Valley : 太湖流域
         * heliu : 蒲汇塘
         * ControlType : 市控
         * RunStatus : 离线
         * EQI :
         * CalEQIType : 河流
         * LastDataTime : 2018-05-11 13:11:00
         * X :
         * Y :
         */

        @Column(name = "id", isId = true)
        private int id;
        @Column(name = "MonitoringPointUid")
        private String MonitoringPointUid="";
        @Column(name = "PointId")
        @SerializedName("PointId")
        private String idkey="";
        @Column(name = "MonitoringPointName")
        @SerializedName("MonitoringPointName")
        private String namevalue="";
        @Column(name = "IsVideoOrNot")
        private String IsVideoOrNot="";
        @Column(name = "Address")
        private String Address="";
        @Column(name = "Des")
        private String Des="";
        @Column(name = "Region")
        private String Region="";
        @Column(name = "SiteType")
        private String SiteType="";
        @Column(name = "Area")
        private String Area="";
        @Column(name = "Valley")
        private String Valley="";
        @Column(name = "heliu")
        private String heliu="";
        @Column(name = "ControlType")
        private String ControlType="";
        @Column(name = "RunStatus")
        private String RunStatus="";
        @Column(name = "EQI")
        private String EQI="";
        @Column(name = "CalEQIType")
        private String CalEQIType="";
        @Column(name = "LastDataTime")
        private String LastDataTime="";
        @Column(name = "X")
        private String X="";
        @Column(name = "Y")
        private String Y="";


        public String getMonitoringPointUid() {
            return MonitoringPointUid;
        }

        public void setMonitoringPointUid(String MonitoringPointUid) {
            this.MonitoringPointUid = MonitoringPointUid;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @NotNull
        @Override
        public String getIdkey() {
            return idkey;
        }

        @Override
        public void setIdkey(String idkey) {
            this.idkey = idkey;
        }

        @NotNull
        @Override
        public String getNamevalue() {
            return namevalue;
        }

        @Override
        public void setNamevalue(String namevalue) {
            this.namevalue = namevalue;
        }

        public String getIsVideoOrNot() {
            return IsVideoOrNot;
        }

        public void setIsVideoOrNot(String IsVideoOrNot) {
            this.IsVideoOrNot = IsVideoOrNot;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getDes() {
            return Des;
        }

        public void setDes(String Des) {
            this.Des = Des;
        }

        public String getRegion() {
            return Region;
        }

        public void setRegion(String Region) {
            this.Region = Region;
        }

        public String getSiteType() {
            return SiteType;
        }

        public void setSiteType(String SiteType) {
            this.SiteType = SiteType;
        }

        public String getArea() {
            return Area;
        }

        public void setArea(String Area) {
            this.Area = Area;
        }

        public String getValley() {
            return Valley;
        }

        public void setValley(String Valley) {
            this.Valley = Valley;
        }

        public String getHeliu() {
            return heliu;
        }

        public void setHeliu(String heliu) {
            this.heliu = heliu;
        }

        public String getControlType() {
            return ControlType;
        }

        public void setControlType(String ControlType) {
            this.ControlType = ControlType;
        }

        public String getRunStatus() {
            return RunStatus;
        }

        public void setRunStatus(String RunStatus) {
            this.RunStatus = RunStatus;
        }

        public String getEQI() {
            return EQI;
        }

        public void setEQI(String EQI) {
            this.EQI = EQI;
        }

        public String getCalEQIType() {
            return CalEQIType;
        }

        public void setCalEQIType(String CalEQIType) {
            this.CalEQIType = CalEQIType;
        }

        public String getLastDataTime() {
            return LastDataTime;
        }

        public void setLastDataTime(String LastDataTime) {
            this.LastDataTime = LastDataTime;
        }

        public String getX() {
            return X;
        }

        public void setX(String X) {
            this.X = X;
        }

        public String getY() {
            return Y;
        }

        public void setY(String Y) {
            this.Y = Y;
        }
    }
}
