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
        add(new CountryCode("Angola", "������", "AO", 244, -7));
        add(new CountryCode("Afghanistan", "������", "AF", 93, 0));
        add(new CountryCode("Albania", "����������", "AL", 355, -7));
        add(new CountryCode("Algeria", "����������", "DZ", 213, -8));
        add(new CountryCode("Andorra", "���������͹�", "AD", 376, -8));
        add(new CountryCode("Anguilla", "��������", "AI", 1264, -12));
        add(new CountryCode("Antigua and Barbuda", "����ϺͰͲ���", "AG", 1268, -12));
        add(new CountryCode("Argentina", "����͢", "AR", 54, -11));
        add(new CountryCode("Armenia", "��������", "AM", 374, -6));
        add(new CountryCode("Ascension", "��ɭ��", "AC", 247, -8));
        add(new CountryCode("Australia", "�Ĵ�����", "AU", 61, +2));
        add(new CountryCode("Austria", "�µ���", "AT", 43, -7));
        add(new CountryCode("Azerbaijan", "�����ݽ�", "AZ", 994, -5));
        add(new CountryCode("Bahamas", "�͹���", "BS", 1242, -13));
        add(new CountryCode("Bahrain", "����", "BH", 973, -5));
        add(new CountryCode("Bangladesh", "�ϼ�����", "BD", 880, -2));
        add(new CountryCode("Barbados", "�ͰͶ�˹", "BB", 1246, -12));
        add(new CountryCode("Belarus", "�׶���˹", "BY", 375, -6));
        add(new CountryCode("Belgium", "����ʱ", "BE", 32, -7));
        add(new CountryCode("Belize", "������", "BZ", 501, -14));
        add(new CountryCode("Benin", "����", "BJ", 229, -7));
        add(new CountryCode("Bermuda Is.", "��Ľ��Ⱥ��", "BM", 1441, -12));
        add(new CountryCode("Bolivia", "����ά��", "BO", 591, -12));
        add(new CountryCode("Botswana", "��������", "BW", 267, -6));
        add(new CountryCode("Brazil", "����", "BR", 55, -11));
        add(new CountryCode("Brunei", "����", "BN", 673, 0));
        add(new CountryCode("Bulgaria", "��������", "BG", 359, -6));
        add(new CountryCode("Burkina-faso", "�����ɷ���", "BF", 226, -8));
        add(new CountryCode("Myanmar", "���", "MM", 95, -1.3));
        add(new CountryCode("Burundi", "��¡��", "BI", 257, -6));
        add(new CountryCode("Cameroon", "����¡", "CM", 237, -7));
        add(new CountryCode("Canada", "���ô�", "CA", 1, -13));
        add(new CountryCode("Cayman Is.", "����Ⱥ��", "KY", 1345, -13));
        add(new CountryCode("Central African Republic", "�зǹ��͹�", "CF", 236, -7));
        add(new CountryCode("Chad", "է��", "TD", 235, -7));
        add(new CountryCode("Chile", "����", "CL", 56, -13));
        add(new CountryCode("China", "�й�", "CN", 86, 0));
        add(new CountryCode("Colombia", "���ױ���", "CO", 57, 0));
        add(new CountryCode("Congo", "�չ�", "CG", 242, -7));
        add(new CountryCode("Cook Is.", "���Ⱥ��", "CK", 682, -18.3));
        add(new CountryCode("Costa Rica", "��˹�����", "CR", 506, -14));
        add(new CountryCode("Cuba", "�Ű�", "CU", 53, -13));
        add(new CountryCode("Cyprus", "����·˹", "CY", 357, -6));
        add(new CountryCode("Czech Republic", "�ݿ�", "CZ", 420, -7));
        add(new CountryCode("Denmark", "����", "DK", 45, -7));
        add(new CountryCode("Djibouti", "������", "DJ", 253, -5));
        add(new CountryCode("Dominica Rep.", "������ӹ��͹�", "DO", 1890, -13));
        add(new CountryCode("Ecuador", "��϶��", "EC", 593, -13));
        add(new CountryCode("Egypt", "����", "EG", 20, -6));
        add(new CountryCode("EI Salvador", "�����߶�", "SV", 503, -14));
        add(new CountryCode("Estonia", "��ɳ����", "EE", 372, -5));
        add(new CountryCode("Ethiopia", "���������", "ET", 251, -5));
        add(new CountryCode("Fiji", "쳼�", "FJ", 679, +4));
        add(new CountryCode("Finland", "����", "FI", 358, -6));
        add(new CountryCode("France", "����", "FR", 33, -8));
        add(new CountryCode("French Guiana", "����������", "GF", 594, -12));
        add(new CountryCode("Gabon", "����", "GA", 241, -7));
        add(new CountryCode("Gambia", "�Ա���", "GM", 220, -8));
        add(new CountryCode("Georgia", "��³����", "GE", 995, 0));
        add(new CountryCode("Germany", "�¹�", "DE", 49, -7));
        add(new CountryCode("Ghana", "����", "GH", 233, -8));
        add(new CountryCode("Gibraltar", "ֱ������", "GI", 350, -8));
        add(new CountryCode("Greece", "ϣ��", "GR", 30, -6));
        add(new CountryCode("Grenada", "�����ɴ�", "GD", 1809, -14));
        add(new CountryCode("Guam", "�ص�", "GU", 1671, +2));
        add(new CountryCode("Guatemala", "Σ������", "GT", 502, -14));
        add(new CountryCode("Guinea", "������", "GN", 224, -8));
        add(new CountryCode("Guyana", "������", "GY", 592, -11));
        add(new CountryCode("Haiti", "����", "HT", 509, -13));
        add(new CountryCode("Honduras", "�鶼��˹", "HN", 504, -14));
        add(new CountryCode("Hong kong", "���", "HK", 852, 0));
        add(new CountryCode("Hungary", "������", "HU", 36, -7));
        add(new CountryCode("Iceland", "����", "IS", 354, -9));
        add(new CountryCode("India", "ӡ��", "IN", 91, -2.3));
        add(new CountryCode("Indonesia", "ӡ��������", "ID", 62, -0.3));
        add(new CountryCode("Iran", "����", "IR", 98, -4.3));
        add(new CountryCode("Iraq", "������", "IQ", 964, -5));
        add(new CountryCode("Ireland", "������", "IE", 353, -4.3));
        add(new CountryCode("Israel", "��ɫ��", "IL", 972, -6));
        add(new CountryCode("Italy", "�����", "IT", 39, -7));
        add(new CountryCode("Ivory Coast", "���ص���", "CI", 225, -6));
        add(new CountryCode("Jamaica", "�����", "JM", 1876, -12));
        add(new CountryCode("Japan", "�ձ�", "JP", 81, +1));
        add(new CountryCode("Jordan", "Լ��", "JO", 962, -6));
        add(new CountryCode("Kampuchea (Cambodia )", "����կ", "KH", 855, -1));
        add(new CountryCode("Kazakstan", "������˹̹", "KZ", 327, -5));
        add(new CountryCode("Kenya", "������", "KE", 254, -5));
        add(new CountryCode("Korea", "����", "KR", 82, +1));
        add(new CountryCode("Kuwait", "������", "KW", 965, -5));
        add(new CountryCode("Kyrgyzstan", "������˹̹", "KG", 331, -5));
        add(new CountryCode("Laos", "����", "LA", 856, -1));
        add(new CountryCode("Latvia", "����ά��", "LV", 371, -5));
        add(new CountryCode("Lebanon", "�����", "LB", 961, -6));
        add(new CountryCode("Lesotho", "������", "LS", 266, -6));
        add(new CountryCode("Liberia", "��������", "LR", 231, -8));
        add(new CountryCode("Libya", "������", "LY", 218, -6));
        add(new CountryCode("Liechtenstein", "��֧��ʿ��", "LI", 423, -7));
        add(new CountryCode("Lithuania", "������", "LT", 370, -5));
        add(new CountryCode("Luxembourg", "¬ɭ��", "LU", 352, -7));
        add(new CountryCode("Macau", "����", "MO", 853, 0));
        add(new CountryCode("Madagascar", "����˹��", "MG", 261, -5));
        add(new CountryCode("Malawi", "����ά", "MW", 265, -6));
        add(new CountryCode("Malaysia", "��������", "MY", 60, -0.5));
        add(new CountryCode("Maldives", "�������", "MV", 960, -7));
        add(new CountryCode("Mali", "����", "ML", 223, -8));
        add(new CountryCode("Malta", "�����", "MT", 356, -7));
        add(new CountryCode("Mariana Is", "��������Ⱥ��", "MP", 1670, +1));
        add(new CountryCode("Martinique", "�������", "MQ", 596, -12));
        add(new CountryCode("Mauritius", "ë����˹", "MU", 230, -4));
        add(new CountryCode("Mexico", "ī����", "MX", 52, -15));
        add(new CountryCode("Moldova Republic of", "Ħ������", "MD", 373, -5));
        add(new CountryCode("Monaco", "Ħ�ɸ�", "MC", 377, -7));
        add(new CountryCode("Mongolia", "�ɹ�", "MN", 976, 0));
        add(new CountryCode("Montserrat Is", "���������ص�", "MS", 1664, -12));
        add(new CountryCode("Morocco", "Ħ���", "MA", 212, -6));
        add(new CountryCode("Mozambique", "Īɣ�ȿ�", "MZ", 258, -6));
        add(new CountryCode("Namibia", "���ױ���", "NA", 264, -7));
        add(new CountryCode("Nauru", "�³", "NR", 674, +4));
        add(new CountryCode("Nepal", "�Ჴ��", "NP", 977, -2.3));
        add(new CountryCode("Netheriands Antilles", "����������˹", "AN", 599, -12));
        add(new CountryCode("Netherlands", "����", "NL", 31, -7));
        add(new CountryCode("New Zealand", "������", "NZ", 64, +4));
        add(new CountryCode("Nicaragua", "�������", "NI", 505, -14));
        add(new CountryCode("Niger", "���ն�", "NE", 227, -8));
        add(new CountryCode("Nigeria", "��������", "NG", 234, -7));
        add(new CountryCode("North Korea", "����", "KP", 850, +1));
        add(new CountryCode("Norway", "Ų��", "NO", 47, -7));
        add(new CountryCode("Oman", "����", "OM", 968, -4));
        add(new CountryCode("Pakistan", "�ͻ�˹̹", "PK", 92, -2.3));
        add(new CountryCode("Panama", "������", "PA", 507, -13));
        add(new CountryCode("Papua New Cuinea", "�Ͳ����¼�����", "PG", 675, +2));
        add(new CountryCode("Paraguay", "������", "PY", 595, -12));
        add(new CountryCode("Peru", "��³", "PE", 51, -13));
        add(new CountryCode("Philippines", "���ɱ�", "PH", 63, 0));
        add(new CountryCode("Poland", "����", "PL", 48, -7));
        add(new CountryCode("French Polynesia", "��������������", "PF", 689, +3));
        add(new CountryCode("Portugal", "������", "PT", 351, -8));
        add(new CountryCode("Puerto Rico", "�������", "PR", 1787, -12));
        add(new CountryCode("Qatar", "������", "QA", 974, -5));
        add(new CountryCode("Reunion", "������", "RE", 262, -4));
        add(new CountryCode("Romania", "��������", "RO", 40, -6));
        add(new CountryCode("Russia", "����˹", "RU", 7, -5));
        add(new CountryCode("Saint Lueia", "ʥ¬����", "LC", 1758, -12));
        add(new CountryCode("Saint Vincent", "ʥ��ɭ�ص�", "VC", 1784, -12));
        add(new CountryCode("Samoa Eastern", "����Ħ��(��)", "AS", 684, -19));
        add(new CountryCode("Samoa Western", "����Ħ��", "WS", 685, -19));
        add(new CountryCode("San Marino", "ʥ����ŵ", "SM", 378, -7));
        add(new CountryCode("Sao Tome and Principe", "ʥ��������������", "ST", 239, -8));
        add(new CountryCode("Saudi Arabia", "ɳ�ذ�����", "SA", 966, -5));
        add(new CountryCode("Senegal", "���ڼӶ�", "SN", 221, -8));
        add(new CountryCode("Seychelles", "�����", "SC", 248, -4));
        add(new CountryCode("Sierra Leone", "��������", "SL", 232, -8));
        add(new CountryCode("Singapore", "�¼���", "SG", 65, +0.3));
        add(new CountryCode("Slovakia", "˹�工��", "SK", 421, -7));
        add(new CountryCode("Slovenia", "˹��������", "SI", 386, -7));
        add(new CountryCode("Solomon Is", "������Ⱥ��", "SB", 677, +3));
        add(new CountryCode("Somali", "������", "SO", 252, -5));
        add(new CountryCode("South Africa", "�Ϸ�", "ZA", 27, -6));
        add(new CountryCode("Spain", "������", "ES", 34, -8));
        add(new CountryCode("Sri Lanka", "˹������", "LK", 94, 0));
        add(new CountryCode("St.Lucia", "ʥ¬����", "LC", 1758, -12));
        add(new CountryCode("St.Vincent", "ʥ��ɭ��", "VC", 1784, -12));
        add(new CountryCode("Sudan", "�յ�", "SD", 249, -6));
        add(new CountryCode("Suriname", "������", "SR", 597, -11.3));
        add(new CountryCode("Swaziland", "˹��ʿ��", "SZ", 268, -6));
        add(new CountryCode("Sweden", "���", "SE", 46, -7));
        add(new CountryCode("Switzerland", "��ʿ", "CH", 41, -7));
        add(new CountryCode("Syria", "������", "SY", 963, -6));
        add(new CountryCode("Taiwan", "̨��ʡ", "TW", 886, 0));
        add(new CountryCode("Tajikstan", "������˹̹", "TJ", 992, -5));
        add(new CountryCode("Tanzania", "̹ɣ����", "TZ", 255, -5));
        add(new CountryCode("Thailand", "̩��", "TH", 66, -1));
        add(new CountryCode("Togo", "���", "TG", 228, -8));
        add(new CountryCode("Tonga", "����", "TO", 676, +4));
        add(new CountryCode("Trinidad and Tobago", "�������Ͷ�͸�", "TT", 1809, -12));
        add(new CountryCode("Tunisia", "ͻ��˹", "TN", 216, -7));
        add(new CountryCode("Turkey", "������", "TR", 90, -6));
        add(new CountryCode("Turkmenistan", "������˹̹", "TM", 993, -5));
        add(new CountryCode("Uganda", "�ڸɴ�", "UG", 256, -5));
        add(new CountryCode("Ukraine", "�ڿ���", "UA", 380, -5));
        add(new CountryCode("United Arab Emirates", "����������������", "AE", 971, -4));
        add(new CountryCode("United Kingdom", "Ӣ��", "GB", 44, -8));
        add(new CountryCode("United States of America", "����", "US", 1, -13));
        add(new CountryCode("Uruguay", "������", "UY", 598, -10.3));
        add(new CountryCode("Uzbekistan", "���ȱ��˹̹", "UZ", 233, -5));
        add(new CountryCode("Venezuela", "ί������", "VE", 58, -12.3));
        add(new CountryCode("Vietnam", "Խ��", "VN", 84, -1));
        add(new CountryCode("Yemen", "Ҳ��", "YE", 967, -5));
        add(new CountryCode("Yugoslavia", "��˹����", "YU", 381, -7));
        add(new CountryCode("Zimbabwe", "��Ͳ�Τ", "ZW", 263, -6));
        add(new CountryCode("Zaire", "������", "ZR", 243, -7));
        add(new CountryCode("Zambia", "�ޱ���", "ZM", 260, -6));
    }};
}
