// posts.js
var Api = require('../../utils/api.js');
var util = require('../../utils/group.js');
var WxParse = require('../../wxParse/wxParse.js');
var datadetail = require('../../data/data_detail.js');
var appInstance = getApp();

WxParse.emojisInit('[]', "/wxParse/emojis/", {
  "s": "02.gif",
  "O": "14.gif",
  "|": "02.gif",
  "$": "06.gif",
  "X": "07.gif",
  "'(": "09.gif",
  "-|": "10.gif",
  "@": "11.gif",
  "P": "12.gif",
  "D": "13.gif",
  ")": "00.gif",
  "(": "15.gif",
  "Q": "18.gif",
  "T": "19.gif",
  ";P": "20.gif",
  ";-D": "21.gif",
  "!": "26.gif",
  "L": "27.gif",
  "?": "32.gif",
  "U": "16.gif",
  "K": "25.gif",
  "C-": "29.gif",
  ";X": "35.gif",
  "H": "36.gif",
  ";bye": "39.gif",
  ";cool": "04.gif",
  "-b": "40.gif",
  "-8": "41.gif",
  ";PT": "42.gif",
  "hx": "44.gif",
  ";K": "47.gif",
  "E": "49.gif",
  "-(": "50.gif",
  ";hx": "51.gif",
  "-v": "53.gif",
  ";xx": "54.gif",
})


Page({
  data: {
    detail: [],
    replyArr: [],
    allReply: [],
    hidden: true,
    topicID: '',
    zoneId: '',
    searchLoading: false, //"上拉加载"的变量，默认false，隐藏  
    searchLoadingComplete: false,  //“没有数据”的变量，默认false，隐藏  
    content: [], //剩余加载内容
    totalReplyLength: 0,
    comment: '',
    replyTemArray: [],
    input_value: '',
    images: []
  },

  onLoad: function (e) {
    var that = this;
    // var appInstance = getApp();
    var href = '';
    that.setData({
      zoneId: e.board
    });
    appInstance.href = href;
    appInstance.boardName = e.board;
    that.data.topicID = href;
    wx.showLoading({
      title: '加载中',
    })
    that.fetchData(href);
  },

//上拉加载更多
  onReachBottom: function () {
    var that = this;
    if (that.data.searchLoading && !that.data.searchLoadingComplete) {
      that.setData({
        floorstatus: true,
        searchLoading: true,  //把"上拉加载"的变量设为true，显示  
        searchLoadingComplete: false //把“没有数据”设为false，隐藏  
      })

      that.lower();
      console.log('上拉刷新', new Date());
    }

  },

  /**
 * 页面相关事件处理函数--监听用户拉动作
 */
  onPullDownRefresh: function () {
    var that = this;
    var href = that.data.topicID;
    that.clearData();
    that.data.totalReplyLength = 0;
    that.fetchData(href);
    console.log('上拉刷新', new Date());
    wx.stopPullDownRefresh();
  },

//大于60条回复，清空数据
  clearData() {
    var that = this;
    that.setData({
      replyArr: [],
      detail: [],
      replyTemArray: [],
      allReply: [],
      content: [],
      searchLoading: false, //"上拉加载"的变量，默认false，隐藏  
      searchLoadingComplete: false,  //“没有数据”的变量，默认false，隐藏  
    })
  },

  // 滑动底部加载
  lower: function () {
    console.log('滑动底部加载', new Date());
    var that = this;
    var contentLength = that.data.content.length;
    var totalReplyLength = that.data.totalReplyLength;
    if (that.data.detail.length > 1 && (that.data.detail.length - 1) % 30 == 0) {
      var url = appInstance.href + "&start=" + totalReplyLength;
      if (totalReplyLength % 60 == 0) {
        wx.showLoading({
          title: '下一页',
        })
        that.clearData();
      }else{
        wx.showLoading({
          title: '加载中',
        })
      }
      //翻页
      that.fetchData(url);
    } else if (contentLength >= 0) {
      var content;
      if (contentLength > 9) {
        content = that.data.content.slice(0, 10);
        that.data.content = that.data.content.slice(10, contentLength);
      } else {
        content = that.data.content.slice(0, contentLength);
        that.data.content = [];
        that.setData({
          searchLoadingComplete: true, //把“没有数据”设为true，显示  
          searchLoading: false  //把"上拉加载"的变量设为false，隐藏 
        })
      }
      var detailLength = that.data.detail.length;
      that.setData({
        detail: that.data.detail.concat(content),
      })
      var repliesArray = that.data.detail;
      that.wxParseData(repliesArray, detailLength);
    }
  },

  //富文本解析HTML数据
  wxParseData(repliesArray, detailLength){
    var that = this;
    var l = repliesArray.length;

    for (var i = detailLength; i < l; i++) {
      if (repliesArray[i].thisContext) {
        var c = repliesArray[i].thisContext;
        var r = new RegExp('\n', "g");
        c = c.replace(r, '<br/>');
        if (c.length > 0) {
          that.data.replyArr.push(c);
        }
      }
    }
    var replyArrLength = that.data.replyArr.length;
    console.log('replies:' + replyArrLength);
    if (replyArrLength > 0) {
      for (let i = detailLength; i < replyArrLength; i++) {
        WxParse.wxParse('reply' + i, 'html', that.data.replyArr[i], that);
        if (i === replyArrLength - 1) {
          WxParse.wxParseTemArray("replyTemArray", 'reply', replyArrLength, that)
        }
      }
    }
  },

  // 获取数据
  fetchData: function (href) {

    var that = this;
    var ApiUrl = Api.context;
    console.log(href)
    var data = {

    };
   // Api.fetchPost(ApiUrl, data, (err, res) => {
    var res = datadetail.data;//本地数据
      console.log(res);
      var detailLength = that.data.detail.length;
      if (res.context.length == 31) {
        that.setData({
          searchLoading: true   //把"上拉加载"的变量设为false，显示  
        })
        that.data.content = res.context.slice(11, 31);
        res.context = res.context.slice(0, 11);
      } else {

        if (res.context.length > 11) {
          that.data.content = res.context.slice(11, res.context.length);
          res.context = res.context.slice(0, 11);
          that.setData({
            searchLoading: true   //把"上拉加载"的变量设为false，显示  
          })
        } else {
          that.data.content = [];
          that.setData({
            searchLoadingComplete: true, //把“没有数据”设为true，显示  
            searchLoading: false  //把"上拉加载"的变量设为false，隐藏 
          })
        }
      }
      if (detailLength != 0) {
        var appendContext = res.context.shift();
      }
      that.setData({
        detail: that.data.detail.concat(res.context),
      })

      console.log(that.data.detail);
      if (detailLength == 0) {
        var article = that.data.detail[0].thisContext;

        var r1 = new RegExp('\n', "g");
        article = article.replace(r1, '<br/>');

        WxParse.wxParse('article', 'html', article, that, 15);//Padding为15
        console.log(article);
      }


      var repliesArray = that.data.detail;

      that.wxParseData(repliesArray, detailLength);

      that.data.totalReplyLength += 30;

      wx.hideLoading()
    //})



  },

  onShareAppMessage: function (res) {
    var that = this;
    console.log(appInstance.href)
    if (res.from == 'button') {
      // 来自页面内转发按钮
      console.log(res.target)
    }
    return {
      title: '',
      path: '/pages/detail/detail?' + appInstance.href,
      success: function (res) {
        // 转发成功
      },
      fail: function (res) {
        // 转发失败
      }
    }
  },

  //评论
  newComment: function (e) {
    wx.navigateTo({
      url: '../comment/comment?detail=' + this.data.detail[0].thisTitle
    })
  },

  authornameClick: function (e) {
    var userid = e.currentTarget.id;
    wx.navigateTo({
      url: '/pages/personalInfo/personalInfo?userId=' + userid,
    })
  },

  // 点赞
  zanClick: function (e) {
    console.log(e);
    var that = this;
    var wechartUserInfo = wx.getStorageSync('wechartUserInfo');
    if (wechartUserInfo == '') {
      wx.getSetting({
        success(res) {
          if (!res.authSetting['scope.userInfo']) {
            wx.openSetting({
              success: (res) => {

                wx.getUserInfo({
                  success: function (res1) {
                    var userInfo = res1.userInfo
                    var app = getApp()
                    app.globalData.userInfo = res1.userInfo
                    wx.setStorageSync('wechartUserInfo', res1.userInfo);//存储个人微信信息  
                  }
                })

              }
            })
          }
        }
      })

      return;
    }
    var weChatOpenid = wx.getStorageSync('user').openid;
    var id = e.currentTarget.id;
    var index = e.currentTarget.dataset.floorid;
    var ApiUrl = Api.zan;
    if (!weChatOpenid) return;

    var data = {
      //提交数据
    };

    Api.zanPost(ApiUrl, data, (err, res) => {

      var detail = that.data.detail;
      var replies = detail[index];
      if (res.Upvote == 1) {
        replies.thisFloorUpvoteNumber = parseInt(replies.thisFloorUpvoteNumber) + 1;
        replies.thisFloorUpvoteIsMe = 1;
        // if (res.action === "up") {
        that.setData({ detail: detail });

      } else if (res.Upvote == 0) {
        replies.thisFloorUpvoteNumber = parseInt(replies.thisFloorUpvoteNumber) - 1;
        replies.thisFloorUpvoteIsMe = 0;
        that.setData({ detail: detail });
      }
    })

  },

  //浏览谈论区
  zoneClick: function () {
    var pages = getCurrentPages();
    if (pages.length <= 2) {
      wx.navigateTo({
        url: '/pages/zone/zone?board=' + this.data.zoneId
      });
    } else {
      wx.navigateBack({

      })
    }
  },

  //评论内容
  commentInput: function (e) {
    this.data.comment = e.detail.value;
  },

  //提交评论
  submit: function () {
    var that = this;
    var webchatID = wx.getStorageSync('user').openid;
    if (typeof (webchatID) == "undefined") {
      //跳转登录界面
      wx.navigateTo({
        url: '/pages/login/login',
      })
      return;
    }


    var url = Api.Host + "board/" + that.data.zoneId + "/reply";
    var data = {
    };
    Api.submitComment(url, data, (err, res) => {
      if (res.result == 'success') {
        wx.showToast({
          title: '发表成功',
          icon: 'succes',
          duration: 1000,
          mask: true
        })
        that.setData({
          input_value: '',
          detail: [],
          replyArr: [],
          allReply: [],
        })

        that.fetchData(that.data.topicID);
      } else {
        wx.showToast({
          title: '发表失败',
          icon: 'loading',
          duration: 1000,
          mask: true
        })
      }
    }
    )
  },


  /* 函数描述：作为上传文件时递归上传的函数体体；
      * 参数描述： 
      * filePaths是文件路径数组
      * successUp是成功上传的个数
      * failUp是上传失败的个数
      * i是文件路径数组的指标
      * length是文件路径数组的长度
      */
  uploadDIY(filePaths, urlLength, failUp, i, webchatID, boardName, content) {
    var that = this;
    //wx.showToast(0, "图片上传中...", 20000000, 1);
    var isuploaderror = false;
    wx.uploadFile({
      url: '',//上传图片url
      filePath: filePaths[i],
      name: 'file',
      formData: {
      },//上传的body
      success: (res) => {
        console.log(res);
        content = content + '\n' + res.data;
        //successUp++;
      },
      fail: (res) => {
        failUp++;
        isuploaderror = true;
      },
      complete: () => {
        i++;
        if (i == filePaths.length) {
          // wx.hideToast();
          // var txt = '总共' + successUp + '张上传成功,' + failUp + '张上传失败！';
          // wx.showToast(0, txt, 2000, 1);

          var title = that.data.title;
          //var content = that.data.content;
          var zoneName = boardName;
          var url = Api.Host + "board/" + zoneName;
          var data = {

          };
          console.log(data);
          Api.submitPost(url, data, (err, res) => {
            wx.hideNavigationBarLoading() //完成停止加载
            setTimeout(function () {
              wx.hideLoading()
            }, 200)
            //更新数据
            if (res.result == "success") {
              wx.showToast({
                title: '发送成功',
                icon: 'succes',
                duration: 1000,
                mask: true
              })
              var pages = getCurrentPages();
              if (pages.length == 2) {
                wx.redirectTo({
                  url: '../zone/zone?board=' + zoneName
                })

              } else if (pages.length == 3) {
                pages[1].onPullDownRefresh();
                wx.navigateBack({})
              }
            } else {
              wx.showToast({
                title: '发送失败',
                icon: 'loading',
                duration: 1000,
                mask: true
              })
            }


          })

        } else {  //递归调用uploadDIY函数
          if (isuploaderror) {
            wx.showToast(0, '图片上传失败，请重新选择上传', 2000, 1);
          } else {
            that.uploadDIY(filePaths, urlLength, failUp, i, webchatID, boardName, content);
          }
        }
      }
    });
  },

//选择图片
  imgClick: function (e) {
    var that = this;
    wx.chooseImage({
      count: 9, // 默认9
      sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        // var tempFilePaths = ;
        var objArr = that.data.images
        for (var i = 0; i < res.tempFilePaths.length; i++) {
          objArr.push(res.tempFilePaths[i]);
        };
        // var tempFilePaths = res.tempFilePaths

        that.setData({ images: objArr });
      }
    })
  },
  /**
      * 查看大图
      * @param {String} cur 当前展示图片
      * @param {Array}  imageList 展示的图片列表
      */
  previewImage: function (e) {
    wx.previewImage({
      current: e.currentTarget.dataset.url, // 当前显示图片的http链接
      urls: e.currentTarget.dataset.urls // 需要预览的图片http链接列表
    })
  },

  /**
 * 删除选中图片
 * @param {Number} idx 要删除的图片索引
 */
  deleteImage: function deleteImage(idx) {
    var objArr = this.data.images;
    objArr.splice(idx, 1);
    this.setData({ images: objArr });
  },

})
