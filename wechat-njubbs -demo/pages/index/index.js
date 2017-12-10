//index.js
var Api = require('../../utils/api.js');
//获取应用实例
var app = getApp()
Page({
  data: {
    islogin: false,
    bindName: '',
    userInfo: {},
    bbsuserInfo: {}
  },

  onLoad: function () {
    console.log('onLoad')
    var that = this;

    ///缓存中得到WeChat信息
    var wechatid = wx.getStorageSync('user');
    if (wechatid.openid){
      var wechartUserInfo = wx.getStorageSync('wechartUserInfo');
      if (wechartUserInfo == '')
        return;
      that.setData({
        userInfo: wechartUserInfo
      })
    }

    ///服务器查询是否绑定小百合账户
    // that.setData({
    //   bbsuserInfo: bbsUserInfo,
    //   bindName: res,
    //   islogin: true
    // })
  

  },

///解绑账号
  logoutClick: function (e) {
    var that = this;
    var wechatid = wx.getStorageSync('user');
    var ApiUrl = Api.deleteAccount;
    Api.userLoginPost(ApiUrl, wechatid, (err, res) => {
      if (res == 'succes') {
        that.setData({
          bindName: 0,
          bbsuserInfo: '',
          islogin: false
        })
        wx.removeStorageSync('bbsUserInfo')
        wx.showToast({
          title: '解绑成功',
          icon: 'succes',
          duration: 1000,
          mask: true
        })
      }
    })
  },

  //跳转登录界面
  bindViewTap: function () {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },

  //跳转个人主页
  detailInfoClick: function (e) {
    var that = this;
    if (that.data.bbsuserInfo != null) {

      wx.navigateTo({
        url: '/pages/personalInfo/personalInfo?userId=' + that.data.bbsuserInfo.id,
      })
    }

  },

//跳转关于界面
  aboutClick: function(){
    wx.navigateTo({
      url: '/pages/about/about',
    })
  },

  ///微信授权登录
  userInfoHandler: function (e) {
    var that = this;
    console.log(e);
    if (e.detail.userInfo != null) {
      that.setData({
        userInfo: e.detail.userInfo
      })
      wx.setStorageSync('wechartUserInfo', e.detail.userInfo);//存储个人微信信息  
      //that.onLoad();

      wx.login({
        success: function (resCode) {
          console.log(resCode);
          var code = resCode.code;
          wx.request({
            method: 'POST',
            header: { 'Content-Type': 'application/json' },
            url: '',
            //向服务器传递code,encryptedData,iv得到openid
            data: {
              "code": code,
              "encryptedData": e.detail.encryptedData,
              "iv": e.detail.iv
            },
            success(res) {
              console.log(res);
              var obj = {};
              obj.openid = res.data.authInfo.openId;
              wx.setStorageSync('user', obj);//存储openid  
              //查询是否绑定小百合账号
              //........
               },
            fail(e) {
              console.error(e)
              callback(e)
            }
          })
        }
      });
    }

  },

  //跳转发帖界面
  newPostClick: function (e) {
    wx.navigateTo({
      url: '/pages/newpost/newpost',
    })
  }
})
