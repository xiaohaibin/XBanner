package com.stx.xhb.demo.entity;


import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

import java.util.List;

/**
 * @author: xiaohaibin.
 * @time: 2018/10/19
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
public class TuchongEntity {


    private String tos_name;
    private boolean more;
    private String result;
    private List<FeedListBean> feedList;

    public String getTos_name() {
        return tos_name;
    }

    public void setTos_name(String tos_name) {
        this.tos_name = tos_name;
    }

    public boolean isMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<FeedListBean> getFeedList() {
        return feedList;
    }

    public void setFeedList(List<FeedListBean> feedList) {
        this.feedList = feedList;
    }

    public static class FeedListBean {

        private String type;
        private EntryBean entry;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public EntryBean getEntry() {
            return entry;
        }

        public void setEntry(EntryBean entry) {
            this.entry = entry;
        }

        public static class EntryBean extends SimpleBannerInfo {

            private String vid;
            private String video_id;
            private String title;
            private String content;
            private String type;
            private AuthorBean author;
            private String cover;
            private String raw_cover;
            private int favorites;
            private int views;
            private String video_width;
            private String video_height;
            private String created;
            private String share_url;
            private String share_cover;
            private String duration;
            private boolean is_recommend;
            private String comments;
            private String passed_time;
            private boolean is_ultra;
            private boolean collected;
            private int collect_num;
            private String gif_cover;
            private List<String> category;
            private String url;
            private List<ImagesBean> images;

            @Override
            public String getXBannerUrl() {
                return "https://photo.tuchong.com/" + getImages().get(0).getUser_id() + "/f/" + getImages().get(0).getImg_id() + ".jpg";
            }

            @Override
            public String getXBannerTitle() {
                return getTitle();
            }

            public List<ImagesBean> getImages() {
                return images;
            }

            public String getUrl() {
                return url;
            }

            public String getVid() {
                return vid;
            }

            public void setVid(String vid) {
                this.vid = vid;
            }

            public String getVideo_id() {
                return video_id;
            }

            public void setVideo_id(String video_id) {
                this.video_id = video_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public AuthorBean getAuthor() {
                return author;
            }

            public void setAuthor(AuthorBean author) {
                this.author = author;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getRaw_cover() {
                return raw_cover;
            }

            public void setRaw_cover(String raw_cover) {
                this.raw_cover = raw_cover;
            }

            public int getFavorites() {
                return favorites;
            }

            public void setFavorites(int favorites) {
                this.favorites = favorites;
            }

            public int getViews() {
                return views;
            }

            public void setViews(int views) {
                this.views = views;
            }

            public String getVideo_width() {
                return video_width;
            }

            public void setVideo_width(String video_width) {
                this.video_width = video_width;
            }

            public String getVideo_height() {
                return video_height;
            }

            public void setVideo_height(String video_height) {
                this.video_height = video_height;
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }

            public String getShare_url() {
                return share_url;
            }

            public void setShare_url(String share_url) {
                this.share_url = share_url;
            }

            public String getShare_cover() {
                return share_cover;
            }

            public void setShare_cover(String share_cover) {
                this.share_cover = share_cover;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public boolean isIs_recommend() {
                return is_recommend;
            }

            public void setIs_recommend(boolean is_recommend) {
                this.is_recommend = is_recommend;
            }

            public String getComments() {
                return comments;
            }

            public void setComments(String comments) {
                this.comments = comments;
            }

            public String getPassed_time() {
                return passed_time;
            }

            public void setPassed_time(String passed_time) {
                this.passed_time = passed_time;
            }

            public boolean isIs_ultra() {
                return is_ultra;
            }

            public void setIs_ultra(boolean is_ultra) {
                this.is_ultra = is_ultra;
            }

            public boolean isCollected() {
                return collected;
            }

            public void setCollected(boolean collected) {
                this.collected = collected;
            }

            public int getCollect_num() {
                return collect_num;
            }

            public void setCollect_num(int collect_num) {
                this.collect_num = collect_num;
            }

            public String getGif_cover() {
                return gif_cover;
            }

            public void setGif_cover(String gif_cover) {
                this.gif_cover = gif_cover;
            }

            public List<String> getCategory() {
                return category;
            }

            public void setCategory(List<String> category) {
                this.category = category;
            }

            public static class AuthorBean {
                /**
                 * site_id : 1370250
                 * type : user
                 * name : 炙热的橙
                 * domain :
                 * description : 微博：炙热的橙
                 * followers : 7841
                 * url : https://tuchong.com/1370250/
                 * icon : https://s1.tuchong.com/sites/137/1370250/logo_small.jpg?6
                 * verified : false
                 * verified_type : 0
                 * verified_reason :
                 * verifications : 0
                 * verification_list : []
                 */

                private int site_id;
                private String type;
                private String name;
                private String domain;
                private String description;
                private int followers;
                private String url;
                private String icon;
                private boolean verified;
                private int verified_type;
                private String verified_reason;
                private int verifications;
                private List<?> verification_list;

                public int getSite_id() {
                    return site_id;
                }

                public void setSite_id(int site_id) {
                    this.site_id = site_id;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getDomain() {
                    return domain;
                }

                public void setDomain(String domain) {
                    this.domain = domain;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public int getFollowers() {
                    return followers;
                }

                public void setFollowers(int followers) {
                    this.followers = followers;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getIcon() {
                    return icon;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }

                public boolean isVerified() {
                    return verified;
                }

                public void setVerified(boolean verified) {
                    this.verified = verified;
                }

                public int getVerified_type() {
                    return verified_type;
                }

                public void setVerified_type(int verified_type) {
                    this.verified_type = verified_type;
                }

                public String getVerified_reason() {
                    return verified_reason;
                }

                public void setVerified_reason(String verified_reason) {
                    this.verified_reason = verified_reason;
                }

                public int getVerifications() {
                    return verifications;
                }

                public void setVerifications(int verifications) {
                    this.verifications = verifications;
                }

                public List<?> getVerification_list() {
                    return verification_list;
                }

                public void setVerification_list(List<?> verification_list) {
                    this.verification_list = verification_list;
                }
            }
        }
    }
}
