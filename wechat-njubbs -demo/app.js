//app.js
App({
  onLaunch: function () {
    //调用API从本地缓存中获取数据
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
  },
  getUserInfo: function (cb) {
    var that = this
    if (this.globalData.userInfo) {
      typeof cb == "function" && cb(this.globalData.userInfo)
    } else {
      //调用登录接口
      wx.login({
        success: function (res) {
          console.log(res);
          var code = res.code;
          wx.getUserInfo({
            success: function (res1) {
              that.globalData.userInfo = res1.userInfo
              wx.setStorageSync('wechartUserInfo', res1.userInfo);//存储个人微信信息  
              typeof cb == "function" && cb(that.globalData.userInfo)
              wx.request({
                method: 'POST',
                header: { 'Content-Type': 'application/json' },
                url: '',//服务器url
                //上传code，encryptedData，iv得到openid
                data: {
                  "code": code,
                  "encryptedData": res1.encryptedData,
                  "iv": res1.iv
                },
                success(res) {
                  console.log(res);
                  var obj = {};
                  obj.openid = res.data.authInfo.openId;
                  wx.setStorageSync('user', obj);//存储openid  
                  callback(null, res.data)
                },
                fail(e) {
                  console.error(e)
                  callback(e)
                }
              })
            },
            fail: function () {
            }
          })
        }
      });
      wx.checkSession({
        success: function () {
          //登录态未过期
        },
        fail: function () {
          //登录态过期
          wx.login()
        }
      });
    }
  },
  globalData: {
    userInfo: null,
    formid: '',
    appid: "",//小程序appid
    secret: "",//小程序secret
    host: ''//
  }
})