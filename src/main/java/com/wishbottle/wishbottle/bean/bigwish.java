package com.wishbottle.wishbottle.bean;

public class bigwish {
    private boolean hasGood;//点赞过为true,未点赞为false
    private boolean hasCollection;//收藏过为true,未收藏过为false
    private  Wish wish;//wish对象


    private boolean isShow=false;//下拉评论显示（默认不显示)

    public bigwish(boolean hasGood, boolean hasCollection, Wish wish) {
        this.hasGood = hasGood;
        this.hasCollection = hasCollection;
        this.wish = wish;
    }

    public bigwish() {
    }

    public boolean isHasGood() {
        return hasGood;
    }

    public void setHasGood(boolean hasGood) {
        this.hasGood = hasGood;
    }

    public boolean isHasCollection() {
        return hasCollection;
    }

    public void setHasCollection(boolean hasCollection) {
        this.hasCollection = hasCollection;
    }

    public Wish getWish() {
        return wish;
    }

    public void setWish(Wish wish) {
        this.wish = wish;
    }
    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

}
