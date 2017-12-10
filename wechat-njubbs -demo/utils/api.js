// 域名地址
var HOST = '';
//讨论区
var section = HOST + '';
//帖子详情
var context = HOST + '';
//绑定小百合账号
var bindAccount = HOST + '';
//解绑小百合账号
var deleteAccount = HOST + '';
//查询绑定状态
var checkState = HOST + '';
//查询小百合个人信息
var bbsUserInfo = HOST + '';
// 为评论点赞
var zan = HOST + '';
///上传formid
var formid = HOST + '';

// get请求方法
function fetchGet(url, callback) {
  // return callback(null, top250)
  wx.request({
    url: url,
    header: { 'Content-Type': 'application/json' },
    success(res) {
      console.log(res);
      callback(null, res.data)
    },
    fail(e) {
      console.error(e)
      callback(e)
    }
  })
}

// post请求方法 帖子详情
function fetchPost(url, data, callback) {
  wx.request({
    method: 'POST',
    header: { 'Content-Type': 'application/json' },
    url: url,

    data: {
      "url": data.url,
      "webchatID": data.webchatID
    },
    success(res) {
      console.log(res);
      callback(null, res.data)
    },
    fail(e) {
      console.error(e)
      callback(e)
    }
  })
}

function fetchUserPost(url, data, callback) {
  wx.request({
    method: 'POST',
    header: { 'Content-Type': 'application/json' },
    url:  url,

    data: {
      "userName": data.userName,
      "passwd": data.passwd,
      "webchatID": data.webchatID,
    },
    success(res) {
      console.log(res);
      callback(null, res.data)
    },
    fail(e) {
      console.error(e)
      callback(e)
    }
  })
}
///绑定，解绑，查询是否绑定
function userLoginPost(url, data, callback) {
  wx.request({
    method: 'POST',
    header: { 'Content-Type': 'application/json' },
    url: url,

    data: {
      "webchatID": data.openid,
    },
    success(res) {
      console.log(res);
      callback(null, res.data)
    },
    fail(e) {
      console.error(e)
      callback(e)
    }
  })
}
///点赞和取消点赞
function zanPost(url, data, callback) {
  wx.request({
    method: 'POST',
    header: { 'Content-Type': 'application/json' },
    url: url,

    data: {
      "topicID": data.topicID,
      "floorID": data.floorID,
      "webchatID": data.webchatID,
    },
    success(res) {
      console.log(res);
      callback(null, res.data)
    },
    fail(e) {
      console.error(e)
      callback(e)
    }
  })
}

///发帖
function submitPost(url, data, callback) {
  wx.request({
    method: 'POST',
    header: { 'Content-Type': 'application/json' },
    url: url,

    data: {
      "title": data.title,
      "text": data.text,
      "webchatID": data.webchatID,
      "pictures": data.pictures
    },
    success(res) {
      console.log(res);
      callback(null, res.data)
    },
    fail(e) {
      console.error(e)
      callback(e)
    }
  })
}

///评论
function submitComment(url, data, callback) {
  wx.request({
    method: 'POST',
    header: { 'Content-Type': 'application/json' },
    url: url,

    data: {
      "title": data.title,
      "text": data.text,
      "webchatID": data.webchatID,
      "url": data.url,
      "pictures": data.pictures
    },
    success(res) {
      console.log(res);
      callback(null, res.data)
    },
    fail(e) {
      console.error(e)
      callback(e)
    }
  })
}

//存formid
function submitFormId(url, data, callback) {
  wx.request({
    method: 'POST',
    header: { 'Content-Type': 'application/json' },
    url: url,

    data: {
      "formid": data.formid,
      "openid": data.openid,
    },
    success(res) {
      console.log(res);
      callback(null, res.data)
    },
    fail(e) {
      console.error(e)
      callback(e)
    }
  })
}

module.exports = {
  // API
  section: section,
  context: context,
  Host: HOST,
  bindAccount: bindAccount,
  deleteAccount: deleteAccount,
  checkState: checkState,
  bbsUserInfo: bbsUserInfo,
  zan: zan,
  formid: formid,
  // METHOD
  fetchGet: fetchGet,
  fetchPost: fetchPost,
  fetchUserPost: fetchUserPost,
  userLoginPost: userLoginPost,
  zanPost: zanPost,
  submitPost: submitPost,
  submitComment: submitComment,
  submitFormId: submitFormId
}
