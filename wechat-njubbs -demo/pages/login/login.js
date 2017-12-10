var Api = require('../../utils/api.js');

Page({
  data: {
    loading: false,
    userName: "",
    password: "",
    error: ""
  },

  onLoad: function () {

  },

///账号输入
  bindKeyInput: function (e) {
    this.setData({
      userName: e.detail.value
    })
  },

//密码输入
  bindPassKeyInput: function (e) {
    this.setData({
      password: e.detail.value
    })
  },

  // 验证账号密码(登录)
  isLogin: function () {
    var that = this;
    //var accesstoken = that.data.accesstoken;
    var ApiUrl = Api.bindAccount;
    var wechat = wx.getStorageSync('user');

    var wechartUserInfo = wx.getStorageSync('wechartUserInfo');
    if (wechartUserInfo == '')
      return;

    var password = that.data.password;
    var CuserInfo = {
      "userName": that.data.userName,
      "passwd": password,
      "webchatID": wechat.openid
    };

    that.setData({ loading: true });
    //登录
    Api.fetchPost(ApiUrl, CuserInfo, (err, res) => {

      if (res == 'success') {
        wx.showToast({
          title: '登录成功',
          icon: 'succes',
          duration: 1000,
          mask: true
        })

        setTimeout(function () {
          prevPage.setData({ bindName: that.data.userName });
          wx.navigateBack();
        }, 1000);

      } else {
        wx.showToast({
          title: '账号或密码错误',
          icon: 'loading',
          duration: 1000,
          mask: true
        })
        that.setData({ error: res.error_msg });
        that.setData({ loading: false });
        setTimeout(function () {
          that.setData({ error: "" });
        }, 2000);
      }

    })


  }
})
