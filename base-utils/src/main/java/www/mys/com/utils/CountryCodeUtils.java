package www.mys.com.utils;

import java.util.ArrayList;

public class CountryCodeUtils {

    public static double getTimeZoneByCountryCode(int countryCode) {
        for (CountryCode cc : countryCodeList) {
            if (cc.getCountryCodeInt() == countryCode) {
                return cc.getTimeZone();
            }
        }
        return 0;
    }

    public static double getTimeZoneByCountryCode(String countryCode) {
        for (CountryCode cc : countryCodeList) {
            if (cc.getCountryCode().equals(countryCode)) {
                return cc.getTimeZone();
            }
        }
        return 0;
    }

    public static String getCountryName(String countryCode) {
        for (CountryCode cc : countryCodeList) {
            if (cc.getCountryCode().equals(countryCode)) {
                return cc.getEnName();
            }
        }
        return null;
    }

    public static int getCountryCode(String countryCode) {
        for (CountryCode cc : countryCodeList) {
            if (cc.getCountryCode().equals(countryCode)) {
                return cc.getCountryCodeInt();
            }
        }
        return 0;
    }

    public static String getCountryName(int countryCode) {
        for (CountryCode cc : countryCodeList) {
            if (cc.getCountryCodeInt() == countryCode) {
                return cc.getEnName();
            }
        }
        return null;
    }

    public static String getCountryCode(int countryCode) {
        for (CountryCode cc : countryCodeList) {
            if (cc.getCountryCodeInt() == countryCode) {
                return cc.getCountryCode();
            }
        }
        return null;
    }

    public static class CountryCode {
        private String enName;
        private String zhName;
        private String countryCode;
        private int countryCodeInt;
        private double timeZone;

        public CountryCode() {
        }

        public CountryCode(String enName, String zhName, String countryCode, int countryCodeInt, double timeZone) {
            this.enName = enName;
            this.zhName = zhName;
            this.countryCode = countryCode;
            this.countryCodeInt = countryCodeInt;
            this.timeZone = timeZone;
        }

        public String getEnName() {
            return enName;
        }

        public void setEnName(String enName) {
            this.enName = enName;
        }

        public String getZhName() {
            return zhName;
        }

        public void setZhName(String zhName) {
            this.zhName = zhName;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public int getCountryCodeInt() {
            return countryCodeInt;
        }

        public void setCountryCodeInt(int countryCodeInt) {
            this.countryCodeInt = countryCodeInt;
        }

        public double getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(double timeZone) {
            this.timeZone = timeZone;
        }

        @Override
        public String toString() {
            return "CountryCode{" +
                    "enName='" + enName + '\'' +
                    ", zhName='" + zhName + '\'' +
                    ", countryCode='" + countryCode + '\'' +
                    ", countryCodeInt=" + countryCodeInt +
                    ", timeZone=" + timeZone +
                    '}';
        }
    }


    public static final ArrayList<CountryCode> countryCodeList = new ArrayList<CountryCode>() {{
        add(new CountryCode("Angola", "安哥拉", "AO", 244, -7));
        add(new CountryCode("Afghanistan", "阿富汗", "AF", 93, 0));
        add(new CountryCode("Albania", "阿尔巴尼亚", "AL", 355, -7));
        add(new CountryCode("Algeria", "阿尔及利亚", "DZ", 213, -8));
        add(new CountryCode("Andorra", "安道尔共和国", "AD", 376, -8));
        add(new CountryCode("Anguilla", "安圭拉岛", "AI", 1264, -12));
        add(new CountryCode("Antigua and Barbuda", "安提瓜和巴布达", "AG", 1268, -12));
        add(new CountryCode("Argentina", "阿根廷", "AR", 54, -11));
        add(new CountryCode("Armenia", "亚美尼亚", "AM", 374, -6));
        add(new CountryCode("Ascension", "阿森松", "AC", 247, -8));
        add(new CountryCode("Australia", "澳大利亚", "AU", 61, +2));
        add(new CountryCode("Austria", "奥地利", "AT", 43, -7));
        add(new CountryCode("Azerbaijan", "阿塞拜疆", "AZ", 994, -5));
        add(new CountryCode("Bahamas", "巴哈马", "BS", 1242, -13));
        add(new CountryCode("Bahrain", "巴林", "BH", 973, -5));
        add(new CountryCode("Bangladesh", "孟加拉国", "BD", 880, -2));
        add(new CountryCode("Barbados", "巴巴多斯", "BB", 1246, -12));
        add(new CountryCode("Belarus", "白俄罗斯", "BY", 375, -6));
        add(new CountryCode("Belgium", "比利时", "BE", 32, -7));
        add(new CountryCode("Belize", "伯利兹", "BZ", 501, -14));
        add(new CountryCode("Benin", "贝宁", "BJ", 229, -7));
        add(new CountryCode("Bermuda Is.", "百慕大群岛", "BM", 1441, -12));
        add(new CountryCode("Bolivia", "玻利维亚", "BO", 591, -12));
        add(new CountryCode("Botswana", "博茨瓦纳", "BW", 267, -6));
        add(new CountryCode("Brazil", "巴西", "BR", 55, -11));
        add(new CountryCode("Brunei", "文莱", "BN", 673, 0));
        add(new CountryCode("Bulgaria", "保加利亚", "BG", 359, -6));
        add(new CountryCode("Burkina-faso", "布基纳法索", "BF", 226, -8));
        add(new CountryCode("Myanmar", "缅甸", "MM", 95, -1.3));
        add(new CountryCode("Burundi", "布隆迪", "BI", 257, -6));
        add(new CountryCode("Cameroon", "喀麦隆", "CM", 237, -7));
        add(new CountryCode("Canada", "加拿大", "CA", 1, -13));
        add(new CountryCode("Cayman Is.", "开曼群岛", "KY", 1345, -13));
        add(new CountryCode("Central African Republic", "中非共和国", "CF", 236, -7));
        add(new CountryCode("Chad", "乍得", "TD", 235, -7));
        add(new CountryCode("Chile", "智利", "CL", 56, -13));
        add(new CountryCode("China", "中国", "CN", 86, 0));
        add(new CountryCode("Colombia", "哥伦比亚", "CO", 57, 0));
        add(new CountryCode("Congo", "刚果", "CG", 242, -7));
        add(new CountryCode("Cook Is.", "库克群岛", "CK", 682, -18.3));
        add(new CountryCode("Costa Rica", "哥斯达黎加", "CR", 506, -14));
        add(new CountryCode("Cuba", "古巴", "CU", 53, -13));
        add(new CountryCode("Cyprus", "塞浦路斯", "CY", 357, -6));
        add(new CountryCode("Czech Republic", "捷克", "CZ", 420, -7));
        add(new CountryCode("Denmark", "丹麦", "DK", 45, -7));
        add(new CountryCode("Djibouti", "吉布提", "DJ", 253, -5));
        add(new CountryCode("Dominica Rep.", "多米尼加共和国", "DO", 1890, -13));
        add(new CountryCode("Ecuador", "厄瓜多尔", "EC", 593, -13));
        add(new CountryCode("Egypt", "埃及", "EG", 20, -6));
        add(new CountryCode("EI Salvador", "萨尔瓦多", "SV", 503, -14));
        add(new CountryCode("Estonia", "爱沙尼亚", "EE", 372, -5));
        add(new CountryCode("Ethiopia", "埃塞俄比亚", "ET", 251, -5));
        add(new CountryCode("Fiji", "斐济", "FJ", 679, +4));
        add(new CountryCode("Finland", "芬兰", "FI", 358, -6));
        add(new CountryCode("France", "法国", "FR", 33, -8));
        add(new CountryCode("French Guiana", "法属圭亚那", "GF", 594, -12));
        add(new CountryCode("Gabon", "加蓬", "GA", 241, -7));
        add(new CountryCode("Gambia", "冈比亚", "GM", 220, -8));
        add(new CountryCode("Georgia", "格鲁吉亚", "GE", 995, 0));
        add(new CountryCode("Germany", "德国", "DE", 49, -7));
        add(new CountryCode("Ghana", "加纳", "GH", 233, -8));
        add(new CountryCode("Gibraltar", "直布罗陀", "GI", 350, -8));
        add(new CountryCode("Greece", "希腊", "GR", 30, -6));
        add(new CountryCode("Grenada", "格林纳达", "GD", 1809, -14));
        add(new CountryCode("Guam", "关岛", "GU", 1671, +2));
        add(new CountryCode("Guatemala", "危地马拉", "GT", 502, -14));
        add(new CountryCode("Guinea", "几内亚", "GN", 224, -8));
        add(new CountryCode("Guyana", "圭亚那", "GY", 592, -11));
        add(new CountryCode("Haiti", "海地", "HT", 509, -13));
        add(new CountryCode("Honduras", "洪都拉斯", "HN", 504, -14));
        add(new CountryCode("Hong kong", "香港", "HK", 852, 0));
        add(new CountryCode("Hungary", "匈牙利", "HU", 36, -7));
        add(new CountryCode("Iceland", "冰岛", "IS", 354, -9));
        add(new CountryCode("India", "印度", "IN", 91, -2.3));
        add(new CountryCode("Indonesia", "印度尼西亚", "ID", 62, -0.3));
        add(new CountryCode("Iran", "伊朗", "IR", 98, -4.3));
        add(new CountryCode("Iraq", "伊拉克", "IQ", 964, -5));
        add(new CountryCode("Ireland", "爱尔兰", "IE", 353, -4.3));
        add(new CountryCode("Israel", "以色列", "IL", 972, -6));
        add(new CountryCode("Italy", "意大利", "IT", 39, -7));
        add(new CountryCode("Ivory Coast", "科特迪瓦", "CI", 225, -6));
        add(new CountryCode("Jamaica", "牙买加", "JM", 1876, -12));
        add(new CountryCode("Japan", "日本", "JP", 81, +1));
        add(new CountryCode("Jordan", "约旦", "JO", 962, -6));
        add(new CountryCode("Kampuchea (Cambodia )", "柬埔寨", "KH", 855, -1));
        add(new CountryCode("Kazakstan", "哈萨克斯坦", "KZ", 327, -5));
        add(new CountryCode("Kenya", "肯尼亚", "KE", 254, -5));
        add(new CountryCode("Korea", "韩国", "KR", 82, +1));
        add(new CountryCode("Kuwait", "科威特", "KW", 965, -5));
        add(new CountryCode("Kyrgyzstan", "吉尔吉斯坦", "KG", 331, -5));
        add(new CountryCode("Laos", "老挝", "LA", 856, -1));
        add(new CountryCode("Latvia", "拉脱维亚", "LV", 371, -5));
        add(new CountryCode("Lebanon", "黎巴嫩", "LB", 961, -6));
        add(new CountryCode("Lesotho", "莱索托", "LS", 266, -6));
        add(new CountryCode("Liberia", "利比里亚", "LR", 231, -8));
        add(new CountryCode("Libya", "利比亚", "LY", 218, -6));
        add(new CountryCode("Liechtenstein", "列支敦士登", "LI", 423, -7));
        add(new CountryCode("Lithuania", "立陶宛", "LT", 370, -5));
        add(new CountryCode("Luxembourg", "卢森堡", "LU", 352, -7));
        add(new CountryCode("Macau", "澳门", "MO", 853, 0));
        add(new CountryCode("Madagascar", "马达加斯加", "MG", 261, -5));
        add(new CountryCode("Malawi", "马拉维", "MW", 265, -6));
        add(new CountryCode("Malaysia", "马来西亚", "MY", 60, -0.5));
        add(new CountryCode("Maldives", "马尔代夫", "MV", 960, -7));
        add(new CountryCode("Mali", "马里", "ML", 223, -8));
        add(new CountryCode("Malta", "马耳他", "MT", 356, -7));
        add(new CountryCode("Mariana Is", "马里亚那群岛", "MP", 1670, +1));
        add(new CountryCode("Martinique", "马提尼克", "MQ", 596, -12));
        add(new CountryCode("Mauritius", "毛里求斯", "MU", 230, -4));
        add(new CountryCode("Mexico", "墨西哥", "MX", 52, -15));
        add(new CountryCode("Moldova Republic of", "摩尔多瓦", "MD", 373, -5));
        add(new CountryCode("Monaco", "摩纳哥", "MC", 377, -7));
        add(new CountryCode("Mongolia", "蒙古", "MN", 976, 0));
        add(new CountryCode("Montserrat Is", "蒙特塞拉特岛", "MS", 1664, -12));
        add(new CountryCode("Morocco", "摩洛哥", "MA", 212, -6));
        add(new CountryCode("Mozambique", "莫桑比克", "MZ", 258, -6));
        add(new CountryCode("Namibia", "纳米比亚", "NA", 264, -7));
        add(new CountryCode("Nauru", "瑙鲁", "NR", 674, +4));
        add(new CountryCode("Nepal", "尼泊尔", "NP", 977, -2.3));
        add(new CountryCode("Netheriands Antilles", "荷属安的列斯", "AN", 599, -12));
        add(new CountryCode("Netherlands", "荷兰", "NL", 31, -7));
        add(new CountryCode("New Zealand", "新西兰", "NZ", 64, +4));
        add(new CountryCode("Nicaragua", "尼加拉瓜", "NI", 505, -14));
        add(new CountryCode("Niger", "尼日尔", "NE", 227, -8));
        add(new CountryCode("Nigeria", "尼日利亚", "NG", 234, -7));
        add(new CountryCode("North Korea", "朝鲜", "KP", 850, +1));
        add(new CountryCode("Norway", "挪威", "NO", 47, -7));
        add(new CountryCode("Oman", "阿曼", "OM", 968, -4));
        add(new CountryCode("Pakistan", "巴基斯坦", "PK", 92, -2.3));
        add(new CountryCode("Panama", "巴拿马", "PA", 507, -13));
        add(new CountryCode("Papua New Cuinea", "巴布亚新几内亚", "PG", 675, +2));
        add(new CountryCode("Paraguay", "巴拉圭", "PY", 595, -12));
        add(new CountryCode("Peru", "秘鲁", "PE", 51, -13));
        add(new CountryCode("Philippines", "菲律宾", "PH", 63, 0));
        add(new CountryCode("Poland", "波兰", "PL", 48, -7));
        add(new CountryCode("French Polynesia", "法属玻利尼西亚", "PF", 689, +3));
        add(new CountryCode("Portugal", "葡萄牙", "PT", 351, -8));
        add(new CountryCode("Puerto Rico", "波多黎各", "PR", 1787, -12));
        add(new CountryCode("Qatar", "卡塔尔", "QA", 974, -5));
        add(new CountryCode("Reunion", "留尼旺", "RE", 262, -4));
        add(new CountryCode("Romania", "罗马尼亚", "RO", 40, -6));
        add(new CountryCode("Russia", "俄罗斯", "RU", 7, -5));
        add(new CountryCode("Saint Lueia", "圣卢西亚", "LC", 1758, -12));
        add(new CountryCode("Saint Vincent", "圣文森特岛", "VC", 1784, -12));
        add(new CountryCode("Samoa Eastern", "东萨摩亚(美)", "AS", 684, -19));
        add(new CountryCode("Samoa Western", "西萨摩亚", "WS", 685, -19));
        add(new CountryCode("San Marino", "圣马力诺", "SM", 378, -7));
        add(new CountryCode("Sao Tome and Principe", "圣多美和普林西比", "ST", 239, -8));
        add(new CountryCode("Saudi Arabia", "沙特阿拉伯", "SA", 966, -5));
        add(new CountryCode("Senegal", "塞内加尔", "SN", 221, -8));
        add(new CountryCode("Seychelles", "塞舌尔", "SC", 248, -4));
        add(new CountryCode("Sierra Leone", "塞拉利昂", "SL", 232, -8));
        add(new CountryCode("Singapore", "新加坡", "SG", 65, +0.3));
        add(new CountryCode("Slovakia", "斯洛伐克", "SK", 421, -7));
        add(new CountryCode("Slovenia", "斯洛文尼亚", "SI", 386, -7));
        add(new CountryCode("Solomon Is", "所罗门群岛", "SB", 677, +3));
        add(new CountryCode("Somali", "索马里", "SO", 252, -5));
        add(new CountryCode("South Africa", "南非", "ZA", 27, -6));
        add(new CountryCode("Spain", "西班牙", "ES", 34, -8));
        add(new CountryCode("Sri Lanka", "斯里兰卡", "LK", 94, 0));
        add(new CountryCode("St.Lucia", "圣卢西亚", "LC", 1758, -12));
        add(new CountryCode("St.Vincent", "圣文森特", "VC", 1784, -12));
        add(new CountryCode("Sudan", "苏丹", "SD", 249, -6));
        add(new CountryCode("Suriname", "苏里南", "SR", 597, -11.3));
        add(new CountryCode("Swaziland", "斯威士兰", "SZ", 268, -6));
        add(new CountryCode("Sweden", "瑞典", "SE", 46, -7));
        add(new CountryCode("Switzerland", "瑞士", "CH", 41, -7));
        add(new CountryCode("Syria", "叙利亚", "SY", 963, -6));
        add(new CountryCode("Taiwan", "台湾省", "TW", 886, 0));
        add(new CountryCode("Tajikstan", "塔吉克斯坦", "TJ", 992, -5));
        add(new CountryCode("Tanzania", "坦桑尼亚", "TZ", 255, -5));
        add(new CountryCode("Thailand", "泰国", "TH", 66, -1));
        add(new CountryCode("Togo", "多哥", "TG", 228, -8));
        add(new CountryCode("Tonga", "汤加", "TO", 676, +4));
        add(new CountryCode("Trinidad and Tobago", "特立尼达和多巴哥", "TT", 1809, -12));
        add(new CountryCode("Tunisia", "突尼斯", "TN", 216, -7));
        add(new CountryCode("Turkey", "土耳其", "TR", 90, -6));
        add(new CountryCode("Turkmenistan", "土库曼斯坦", "TM", 993, -5));
        add(new CountryCode("Uganda", "乌干达", "UG", 256, -5));
        add(new CountryCode("Ukraine", "乌克兰", "UA", 380, -5));
        add(new CountryCode("United Arab Emirates", "阿拉伯联合酋长国", "AE", 971, -4));
        add(new CountryCode("United Kingdom", "英国", "GB", 44, -8));
        add(new CountryCode("United States of America", "美国", "US", 1, -13));
        add(new CountryCode("Uruguay", "乌拉圭", "UY", 598, -10.3));
        add(new CountryCode("Uzbekistan", "乌兹别克斯坦", "UZ", 233, -5));
        add(new CountryCode("Venezuela", "委内瑞拉", "VE", 58, -12.3));
        add(new CountryCode("Vietnam", "越南", "VN", 84, -1));
        add(new CountryCode("Yemen", "也门", "YE", 967, -5));
        add(new CountryCode("Yugoslavia", "南斯拉夫", "YU", 381, -7));
        add(new CountryCode("Zimbabwe", "津巴布韦", "ZW", 263, -6));
        add(new CountryCode("Zaire", "扎伊尔", "ZR", 243, -7));
        add(new CountryCode("Zambia", "赞比亚", "ZM", 260, -6));
    }};
}
