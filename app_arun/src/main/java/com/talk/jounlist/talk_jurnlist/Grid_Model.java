package com.talk.jounlist.talk_jurnlist;

public class Grid_Model
{
    public String name;
    public String desc;
    public String Longdesc;

    public String getLongdesc() {
        return Longdesc;
    }

    public void setLongdesc(String longdesc) {
        Longdesc = longdesc;
    }

    public int image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
