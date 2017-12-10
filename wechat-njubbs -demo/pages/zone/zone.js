// pages/zone/zone.js
var Api = require('../../utils/api.js');
var dataZone = require('../../data/data_zone.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    href: '',
    DockList: [],
    listClickFlag: false,
    index: 0,
    islogin: false,
    hidden: true
  },

  onShow: function () {
    // 生命周期函数--监听页面显示
    var that = this;
              that.setData({
                DockList: dataZone.data.DockList
          })
    // if (that.data.listClickFlag){
    //   var DockList = that.data.DockList;
    //   var ApiUrl = Api.section + that.data.href + "/";
    //   if (DockList[that.data.index].number > DockList[0].number - 19){
    //     ApiUrl += "/latest";
    //     Api.fetchGet(ApiUrl, (err, res) => {
    //       //更新数据
    //       that.setData({
    //         DockList: res.DockList
    //       })
    //     }

    //     )
    //   }else{
    //     ApiUrl += DockList[that.data.index].number - 2;
    //     Api.fetchGet(ApiUrl, (err, res) => {
    //       //更新数据
    //       var length = res.DockList.length - 1;
    //       for (var i = 0; i < res.DockList.length; i++) {
    //         if (DockList[that.data.index].number == res.DockList[i].number){
    //           DockList[that.data.index].upvoteNumber = res.DockList[i].upvoteNumber; 
    //           that.setData({
    //             DockList: DockList
    //           })
    //           break;
    //         }
    //       }
          

    //     }

        // )
      // }
      



      that.data.listClickFlag = false;
    // }

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    // if (wx.getStorageSync('bbsUserInfo') == ''){
    //   that.setData({
    //     islogin: false
    //   })
    // }else{
    //   that.setData({
    //     islogin: true
    //   })
    // }
    // that.data.href = options.board;
    // that.getData(options.board + "/latest")
    // wx.setNavigationBarTitle({
    //   title: options.board
    // })
  },
  onPullDownRefresh: function () {
    //   该方法绑定了页面滑动到顶部的事件，然后做上拉刷新
    wx.showNavigationBarLoading() //在标题栏中显示加载

    setTimeout(() => {
      this.getData(this.data.href + "/latest")
      wx.hideNavigationBarLoading() //完成停止加载
      wx.stopPullDownRefresh() //停止下拉刷新
    }, 200)

    console.log('下拉刷新', new Date());

  },
  onReachBottom: function () {
    this.lower();
    console.log('上拉刷新', new Date());
  },
  // 滑动底部加载
  lower: function () {
    console.log('滑动底部加载', new Date());
    var that = this;
    var startnumber = that.data.DockList[that.data.DockList.length - 1].number - 22;
    that.getData(that.data.href + "/" + startnumber)
  },

  detailClick: function (e) {
    wx.navigateTo({
      url: '/pages/detail/detail?' + e.currentTarget.dataset.item.url,
    })
    var appInstance = getApp();
    appInstance.href = e.currentTarget.dataset.item.url;
    this.data.listClickFlag = true;
    this.data.index = e.currentTarget.dataset.index;
  },

  //发帖
  newpost: function (e) {
    var content = this.data.href;
    wx.navigateTo({
      url: '../newpost/newpost?board=' + content
    })
  },

  //获取文章列表数据
  getData: function (tab) {
    var that = this;
    var ApiUrl = Api.section + tab;
    console.log(ApiUrl);
   // that.setData({ hidden: false });

    // Api.fetchGet(ApiUrl, (err, res) => {
    //   //更新数据
    //   console.log(res);
    //   if (tab == that.data.href + "/latest"){
    //     that.setData({
    //       DockList: res.DockList
    //     })
    //   }else{
    //     that.setData({
    //       DockList: that.data.DockList.concat(res.DockList)
    //     })
    //   }

    //   setTimeout(function () {
    //     that.setData({ hidden: true });
    //   }, 300);
    // }

    // )

  },
})