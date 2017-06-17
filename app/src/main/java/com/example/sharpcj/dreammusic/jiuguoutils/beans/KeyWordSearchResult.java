package com.example.sharpcj.dreammusic.jiuguoutils.beans;

import java.util.List;

/**
 * Created by joy on 2016/7/18.
 */
public class KeyWordSearchResult {

    /**
     * song : [{"bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","yyr_artist":"1","songname":"平凡之路","artistname":"新声带音乐工作室","control":"0000000000","songid":"74012204","has_mv":"0","encrypted_songid":""},{"bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","yyr_artist":"0","songname":"平凡之路","artistname":"莫艳琳","control":"0000000000","songid":"128337565","has_mv":"0","encrypted_songid":"04077a6469d0956210153L"},{"bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","yyr_artist":"1","songname":"平凡之路","artistname":"杜瑞璞Aka.Du","control":"0000000000","songid":"74010680","has_mv":"0","encrypted_songid":""},{"bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","yyr_artist":"1","songname":"平凡之路ft.月漓（原味版）","artistname":"萧萧","control":"0000000000","songid":"73975202","has_mv":"0","encrypted_songid":""},{"bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","yyr_artist":"1","songname":"平凡之路","artistname":"朵瑞秘","control":"0000000000","songid":"73959926","has_mv":"0","encrypted_songid":""},{"bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","yyr_artist":"1","songname":"平凡之路电吉他吉他独奏","artistname":"东门口","control":"0000000000","songid":"74142179","has_mv":"0","encrypted_songid":""},{"bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","yyr_artist":"1","songname":"平凡之路 －电影［后会无期］主题曲","artistname":"杨凌雁","control":"0000000000","songid":"73930312","has_mv":"0","encrypted_songid":""},{"bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","yyr_artist":"1","songname":"平凡之路","artistname":"Rock空调君","control":"0000000000","songid":"73985164","has_mv":"0","encrypted_songid":""},{"bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","yyr_artist":"1","songname":"平凡之路","artistname":"MC雪殇","control":"0000000000","songid":"74032690","has_mv":"0","encrypted_songid":""},{"bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","yyr_artist":"1","songname":"平凡之路REMIX (satan)","artistname":"MR\u2018Satan","control":"0000000000","songid":"74065144","has_mv":"0","encrypted_songid":""}]
     * error_code : 22000
     * order : song
     */

    private int error_code;
    private String order;
    /**
     * bitrate_fee : {"0":"0|0","1":"0|0"}
     * yyr_artist : 1
     * songname : 平凡之路
     * artistname : 新声带音乐工作室
     * control : 0000000000
     * songid : 74012204
     * has_mv : 0
     * encrypted_songid :
     */

    private List<SongBean> song;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public List<SongBean> getSong() {
        return song;
    }

    public void setSong(List<SongBean> song) {
        this.song = song;
    }

    public static class SongBean {
        private String bitrate_fee;
        private String yyr_artist;
        private String songname;
        private String artistname;
        private String control;
        private String songid;
        private String has_mv;
        private String encrypted_songid;

        public String getBitrate_fee() {
            return bitrate_fee;
        }

        public void setBitrate_fee(String bitrate_fee) {
            this.bitrate_fee = bitrate_fee;
        }

        public String getYyr_artist() {
            return yyr_artist;
        }

        public void setYyr_artist(String yyr_artist) {
            this.yyr_artist = yyr_artist;
        }

        public String getSongname() {
            return songname;
        }

        public void setSongname(String songname) {
            this.songname = songname;
        }

        public String getArtistname() {
            return artistname;
        }

        public void setArtistname(String artistname) {
            this.artistname = artistname;
        }

        public String getControl() {
            return control;
        }

        public void setControl(String control) {
            this.control = control;
        }

        public String getSongid() {
            return songid;
        }

        public void setSongid(String songid) {
            this.songid = songid;
        }

        public String getHas_mv() {
            return has_mv;
        }

        public void setHas_mv(String has_mv) {
            this.has_mv = has_mv;
        }

        public String getEncrypted_songid() {
            return encrypted_songid;
        }

        public void setEncrypted_songid(String encrypted_songid) {
            this.encrypted_songid = encrypted_songid;
        }
    }
}
