var Api = require('../../utils/api.js');
var group = require('../../utils/group.js');
var dataTab0 = require('../../data/data_top10.js');
var dataTab1 = require('../../data/data_topall.js');
var app = getApp()

var navList = [
  { id: "top10", title: "十大" },
  { id: "topall", title: "精华" },
  { id: "more", title: "发现" },
];

Page({
  data: {
    activeIndex: '0',
    navList: navList,
    title: '话题列表',
    postsList: [],
    topallList: [],
    zoneList: [],
    allzoneList: [],
    hidden: true,
    page: 0,
    tab: 'top10',
    loading: false,
    visibleList: [true, true, true, true, true, true, true, true, true, true, true],
    hotList: [{ id: "Pictures", content: "贴图版" }, { id: "Girls", content: "女生天地" }, { id: "WarAndPeace", content: "百年好合" }, { id: "FleaMarket", content: "跳蚤市场" }, { id: "WorldFootball", content: "世界足球" }, { id: "Basketball", content: "篮球" }, { id: "AutoSpeed", content: "车迷世界" }, { id: "Stock", content: "股市风云" }, { id: "AcademicReport", content: "学术讲座" }, { id: "V_Suggestions", content: "校长信箱" }, { id: "NJUExpress", content: "南大校园生活" }, { id: "JobExpress", content: "就业特快" }, { id: "JobAndWork", content: "创业与求职" }],
  },
  onShow: function () {
    // 生命周期函数--监听页面显示
    if (this.data.activeIndex == '2') return;
    // wx.showNavigationBarLoading() //在标题栏中显示加载

    setTimeout(() => {
      this.getData();
      // wx.hideNavigationBarLoading() //完成停止加载
      // wx.stopPullDownRefresh() //停止下拉刷新
    }, 200)

  },
  onLoad: function () {
    var that = this
    var wechatid = wx.getStorageSync('user');
    if (!wechatid.openid) {
      //调用应用实例的方法获取全局数据
      app.getUserInfo(function (userInfo) {
        //更新数据
        that.setData({
          userInfo: userInfo
        })
      })
      wechatid = wx.getStorageSync('user');
    }

  },
  onReady: function () {
    var sysInfo = wx.getSystemInfoSync();
    console.log(sysInfo);
    this.setData({ "pageHeight": sysInfo.windowHeight - 40 });//设置swiper高度
  },

  //讨论区搜索
  wxSearchInput: function (e) {
    var that = this
    var searchWord = e.detail.value;
    var tempObj = [];
    var visibleList = that.data.visibleList;
    var zoneList = that.data.allzoneList;
    for (var i = 0; i < zoneList.length; i++) {
      var zoneInfo = [];
      var tempArr = {};
      tempArr.initial = zoneList[i].initial;
      visibleList[i] = true;
      for (var j = 0; j < zoneList[i].zoneInfo.length; j++) {
        var index = zoneList[i].zoneInfo[j].id.indexOf(searchWord);
        //模糊搜索，字符匹配
        if (zoneList[i].zoneInfo[j].id.toLowerCase().indexOf(searchWord.toLowerCase()) > -1 || zoneList[i].zoneInfo[j].zone.toLowerCase().indexOf(searchWord.toLowerCase()) > -1) {
          zoneInfo.push(zoneList[i].zoneInfo[j]);
          visibleList[i] = false;
        }
      }
      tempArr.zoneInfo = zoneInfo;
      tempObj.push(tempArr);
    }
    that.setData({
      zoneList: tempObj,
      visibleList: visibleList
    })
  },
  
//点击弹出谈论区列表
  wxSerchFocus: function (e) {
    var that = this
    that.setData({
      hidden: false
    })
    // WxSearch.wxSearchFocus(e, that);
  },

  ////点击取消隐藏谈论区列表
  wxSearchFn: function (e) {
    var that = this
    that.setData({
      hidden: true
    })
  },

  //跳转讨论区
  bindBoard: function (e) {
    var zoneId = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: '../zone/zone?board=' + zoneId
    })
  },

  ///浏览帖子详情
  listClick: function (e) {
    wx.navigateTo({
      url: '/pages/detail/detail?' + e.currentTarget.dataset.item.href,
    })
    var appInstance = getApp();
    appInstance.href = e.currentTarget.dataset.item.href;
  },

  //浏览热门讨论区
  excuteHotSearch: function (e) {
    var sub = e.currentTarget.dataset.sub;
    var content = this.data.hotList[sub].id;
    wx.navigateTo({
      url: '../zone/zone?board=' + content
    })
  },

  // 点击tab获取对应分类的数据
  onTapTag: function (e) {
    var that = this;
    var tab = e.currentTarget.id;
    var index = e.currentTarget.dataset.index;
    that.setData({
      activeIndex: index,
      tab: tab,
      page: index
    });
    if (index == 2) {
      if (that.data.allzoneList.length === 0) {
        var zoneList = group.zoneList();

        that.setData({
          zoneList: zoneList,
          allzoneList: zoneList
        })
      }
    }
    else {
      if (that.data.postsList.length == 0 || that.data.topallList.length == 0)
        that.getData();
    }
  },

  //顶部swiper滑动tab切换
  bindChange: function (e) {
    var that = this;
    // var tab = e.currentTarget.id;
    var index = e.detail.current;
    var tab = that.data.navList[index].id

    if (this.data.activeIndex != index) {
      that.setData({
        activeIndex: index,
        tab: tab,
        page: index
      });
      if (that.data.postsList.length == 0 || that.data.topallList.length == 0)
        that.getData();

      if (that.data.allzoneList.length === 0) {
        var zoneList = group.zoneList();

        that.setData({
          zoneList: zoneList,
          allzoneList: zoneList
        })
      }
    }
  },

  //获取文章列表数据
  getData: function () {
    var that = this;
    var tab = that.data.tab;
    var page = that.data.page;

//获取本地数据
    if (tab == 'top10') {
      console.log(dataTab0.data);
      that.setData({
        postsList: dataTab0.data.top10,
      })
    }
    else {
      that.setData({
        topallList: dataTab1.data.topall,
      })
    }
    
    // // get请求方法
    // Api.fetchGet(ApiUrl, (err, res) => {
    //   //更新数据
    //   if (!err) {
    //     if (tab == 'top10') {
    //       that.setData({
    //         postsList: res.top10,
    //       })
    //     }
    //     else {
    //       that.setData({
    //         // topallList: that.data.topallList.concat(res.topAll)
    //         topallList: res.topAll,
    //       })
    //     }
    //   } else {

    //     wx.showLoading({
    //       title: '加载中',
    //     })
    //   }
      // setTimeout(function () {
      //   that.setData({ loading: false });
      // }, 300);
    // }

    // )
    // console.log(postsList);
  },

  //分享
  onShareAppMessage: function (res) {
    var that = this;
    // var appInstance = getApp();
    // console.log(appInstance.href)
    if (res.from === 'button') {
      // 来自页面内转发按钮
      console.log(res.target)
    }
    return {
      title: '南大小百合',
      path: '/pages/topics/topics',
      success: function (res) {
        // 转发成功
      },
      fail: function (res) {
        // 转发失败
      }
    }
  },


  //下拉搜索
  visibleClick: function (e) {
    var id = e.currentTarget.id;
    var visibleList = this.data.visibleList;
    visibleList[id] = !visibleList[id];
    this.setData({
      visibleList: visibleList
    })
  },

///提交formid 用于推送模板消息
  formSubmit: function (e) {
    var ApiUrl = Api.formid;
    var formid = e.detail.formId;
    console.log(formid)
    var data = {
      "formid": formid,
      "openid": wx.getStorageSync('user').openid
    };
    if (app.globalData.formid)
      return;
    Api.submitFormId(ApiUrl, data, (err, res) => {
      if (res == 'Success'){
        app.globalData.formid = formid;
        console.log(res)
      }
    })
  },
})
