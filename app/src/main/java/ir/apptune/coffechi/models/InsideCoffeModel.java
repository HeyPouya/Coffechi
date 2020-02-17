package ir.apptune.coffechi.models;


import java.util.List;

public class InsideCoffeModel {

    /**
     * Type : 1
     * List : [{"Title":"دارچین","latitude":"-0.120850","Longitude":"51.508742","Telephone1":"02634695278","Telephone2":"02635987415","WorkTime":"ساعت 10 تا 24","Address":"آدرس تستی","Score":"80","IsBikedelivery":"false","IsMusic":"false","IsParking":"true","IsPose":"true","IsSelfService":"false","IsSmoke":"true","IsWifi":"true"}]
     * CafePhoto : [{"Name":"فضای بیرونی دارچین","PhoroUrl":"/Content/Website/CoffechiDBEntities/SubGroupPhotoGallery/PhoroUrl/2.-Cafe-Darchin-کافه-دارچین_3_21_2017_10_54_AM.jpg"},{"Name":"دکوراسیون دارچین","PhoroUrl":"/Content/Website/CoffechiDBEntities/SubGroupPhotoGallery/PhoroUrl/کافه-دارچین-69208-همگردی_3_21_2017_10_55_AM.jpg"},{"Name":"صبحانه دارچین","PhoroUrl":"/Content/Website/CoffechiDBEntities/SubGroupPhotoGallery/PhoroUrl/image-ea8e0ab1b3ee30e089a186e5f709e85bffe6c87c2f6179b7b30edadc95431a31-V_3_21_2017_10_55_AM.jpg"}]
     * Status : true
     */

    private int Type;
    private String Status;
    private java.util.List<ListBean> List;
    private java.util.List<CafePhotoBean> CafePhoto;

    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public List<ListBean> getList() {
        return List;
    }

    public void setList(List<ListBean> List) {
        this.List = List;
    }

    public List<CafePhotoBean> getCafePhoto() {
        return CafePhoto;
    }

    public void setCafePhoto(List<CafePhotoBean> CafePhoto) {
        this.CafePhoto = CafePhoto;
    }

    public static class ListBean {
        /**
         * Title : دارچین
         * latitude : -0.120850
         * Longitude : 51.508742
         * Telephone1 : 02634695278
         * Telephone2 : 02635987415
         * WorkTime : ساعت 10 تا 24
         * Address : آدرس تستی
         * Score : 80
         * IsBikedelivery : false
         * IsMusic : false
         * IsParking : true
         * IsPose : true
         * IsSelfService : false
         * IsSmoke : true
         * IsWifi : true
         */

        private String Title;
        private String latitude;
        private String Longitude;
        private String Telephone1;
        private String Telephone2;
        private String WorkTime;
        private String Address;
        private String Score;
        private String IsBikedelivery;
        private String IsMusic;
        private String IsParking;
        private String IsPose;
        private String IsSelfService;
        private String IsSmoke;
        private String IsWifi;

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return Longitude;
        }

        public void setLongitude(String Longitude) {
            this.Longitude = Longitude;
        }

        public String getTelephone1() {
            return Telephone1;
        }

        public void setTelephone1(String Telephone1) {
            this.Telephone1 = Telephone1;
        }

        public String getTelephone2() {
            return Telephone2;
        }

        public void setTelephone2(String Telephone2) {
            this.Telephone2 = Telephone2;
        }

        public String getWorkTime() {
            return WorkTime;
        }

        public void setWorkTime(String WorkTime) {
            this.WorkTime = WorkTime;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getScore() {
            return Score;
        }

        public void setScore(String Score) {
            this.Score = Score;
        }

        public String getIsBikedelivery() {
            return IsBikedelivery;
        }

        public void setIsBikedelivery(String IsBikedelivery) {
            this.IsBikedelivery = IsBikedelivery;
        }

        public String getIsMusic() {
            return IsMusic;
        }

        public void setIsMusic(String IsMusic) {
            this.IsMusic = IsMusic;
        }

        public String getIsParking() {
            return IsParking;
        }

        public void setIsParking(String IsParking) {
            this.IsParking = IsParking;
        }

        public String getIsPose() {
            return IsPose;
        }

        public void setIsPose(String IsPose) {
            this.IsPose = IsPose;
        }

        public String getIsSelfService() {
            return IsSelfService;
        }

        public void setIsSelfService(String IsSelfService) {
            this.IsSelfService = IsSelfService;
        }

        public String getIsSmoke() {
            return IsSmoke;
        }

        public void setIsSmoke(String IsSmoke) {
            this.IsSmoke = IsSmoke;
        }

        public String getIsWifi() {
            return IsWifi;
        }

        public void setIsWifi(String IsWifi) {
            this.IsWifi = IsWifi;
        }
    }

    public static class CafePhotoBean {
        /**
         * Name : فضای بیرونی دارچین
         * PhoroUrl : /Content/Website/CoffechiDBEntities/SubGroupPhotoGallery/PhoroUrl/2.-Cafe-Darchin-کافه-دارچین_3_21_2017_10_54_AM.jpg
         */

        private String Name;
        private String PhoroUrl;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getPhoroUrl() {
            return PhoroUrl;
        }

        public void setPhoroUrl(String PhoroUrl) {
            this.PhoroUrl = PhoroUrl;
        }
    }
}
